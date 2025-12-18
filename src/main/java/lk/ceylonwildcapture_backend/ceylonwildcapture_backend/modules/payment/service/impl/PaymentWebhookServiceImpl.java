package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.impl;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.WebhookEventDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity.WebhookEvent;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.WebhookEventType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.repository.WebhookEventRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentWebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentWebhookServiceImpl implements PaymentWebhookService {

    private final WebhookEventRepository webhookEventRepository;
    private final PaymentService paymentService;

    @Value("${stripe.webhook-secret}")
    private String stripeWebhookSecret;

    @Override
    @Transactional
    public WebhookEventDto handleGatewayWebhook(PaymentProvider provider, String payload, String signature,
            String ipAddress) {
        if (provider != PaymentProvider.STRIPE) {
            throw new UnsupportedOperationException("Only Stripe is supported for now");
        }

        try {
            Event event = Webhook.constructEvent(payload, signature, stripeWebhookSecret);

            WebhookEventType eventType = mapStripeEventType(event.getType());

            WebhookEvent webhookEvent = WebhookEvent.builder()
                    .eventId(event.getId())
                    .provider(provider)
                    .eventType(eventType)
                    .eventPayload(payload)
                    .signature(signature)
                    .ipAddress(ipAddress)
                    .processed(false)
                    .build();

            webhookEventRepository.save(webhookEvent);

            processStripeEvent(event, webhookEvent);

            return mapToDto(webhookEvent);

        } catch (SignatureVerificationException e) {
            log.error("Stripe signature verification failed: {}", e.getMessage());
            throw new RuntimeException("Invalid signature");
        } catch (Exception e) {
            log.error("Error handling Stripe webhook: {}", e.getMessage());
            throw new RuntimeException("Webhook handling failed");
        }
    }

    private void processStripeEvent(Event event, WebhookEvent webhookEvent) {
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataObjectDeserializer.getObject().orElse(null);

        if (stripeObject == null) {
            markEventAsFailed(webhookEvent.getId(), "Could not deserialize Stripe object");
            return;
        }

        try {
            switch (event.getType()) {
                case "checkout.session.completed":
                    handleCheckoutSessionCompleted((Session) stripeObject);
                    break;
                case "checkout.session.async_payment_succeeded":
                    handleCheckoutSessionCompleted((Session) stripeObject);
                    break;
                case "checkout.session.async_payment_failed":
                    Session session = (Session) stripeObject;
                    paymentService.markPaymentFailed(session.getId(), "Async payment failed");
                    break;
                default:
                    log.info("Unhandled Stripe event type: {}", event.getType());
            }
            markEventAsProcessed(webhookEvent.getId());
        } catch (Exception e) {
            markEventAsFailed(webhookEvent.getId(), e.getMessage());
        }
    }

    private void handleCheckoutSessionCompleted(Session session) {
        String sessionId = session.getId();
        String transactionId = session.getPaymentIntent();
        paymentService.markPaymentSuccess(sessionId, transactionId);
    }

    private WebhookEventType mapStripeEventType(String stripeType) {
        switch (stripeType) {
            case "checkout.session.completed":
            case "checkout.session.async_payment_succeeded":
                return WebhookEventType.PAYMENT_SUCCEEDED;
            case "checkout.session.async_payment_failed":
                return WebhookEventType.PAYMENT_FAILED;
            default:
                return WebhookEventType.UNKNOWN;
        }
    }

    @Override
    @Transactional
    public void processWebhookEvent(Long eventId) {
        webhookEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        // This is usually called from a background task or retry logic
    }

    @Override
    @Transactional
    public void processWebhookEventByEventId(String eventId) {
        webhookEventRepository.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    @Transactional
    public void retryWebhookProcessing(Long eventId) {
        WebhookEvent webhookEvent = webhookEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        webhookEvent.setRetryCount(webhookEvent.getRetryCount() + 1);
        webhookEventRepository.save(webhookEvent);
        // Retry logic here
    }

    @Override
    public List<WebhookEvent> getUnprocessedEvents(PaymentProvider provider) {
        return webhookEventRepository.findByProviderAndProcessedOrderByCreatedAtAsc(provider, false);
    }

    @Override
    public Page<WebhookEventDto> getWebhookEventsByPaymentId(String paymentId, Pageable pageable) {
        return webhookEventRepository.findByPaymentId(paymentId, pageable).map(this::mapToDto);
    }

    @Override
    public Page<WebhookEventDto> getWebhookEventsByProvider(PaymentProvider provider, Pageable pageable) {
        return webhookEventRepository.findByProvider(provider, pageable).map(this::mapToDto);
    }

    @Override
    public Page<WebhookEventDto> getFailedWebhookEvents(Pageable pageable) {
        return webhookEventRepository.findFailedWebhookEvents(pageable).map(this::mapToDto);
    }

    @Override
    @Transactional
    public void markEventAsProcessed(Long eventId) {
        WebhookEvent webhookEvent = webhookEventRepository.findById(eventId).orElse(null);
        if (webhookEvent != null) {
            webhookEvent.setProcessed(true);
            webhookEvent.setProcessedAt(LocalDateTime.now());
            webhookEventRepository.save(webhookEvent);
        }
    }

    @Override
    @Transactional
    public void markEventAsFailed(Long eventId, String errorMessage) {
        WebhookEvent webhookEvent = webhookEventRepository.findById(eventId).orElse(null);
        if (webhookEvent != null) {
            webhookEvent.setProcessingError(errorMessage);
            webhookEventRepository.save(webhookEvent);
        }
    }

    @Override
    @Transactional
    public long deleteOldProcessedEvents(int daysToKeep) {
        LocalDateTime olderThan = LocalDateTime.now().minusDays(daysToKeep);
        return webhookEventRepository.deleteByProcessedAndCreatedAtBefore(true, olderThan);
    }

    private WebhookEventDto mapToDto(WebhookEvent event) {
        return WebhookEventDto.builder()
                .id(event.getId())
                .eventId(event.getEventId())
                .provider(event.getProvider())
                .eventType(event.getEventType())
                .paymentId(event.getPaymentId())
                .processed(event.getProcessed())
                .processedAt(event.getProcessedAt())
                .processingError(event.getProcessingError())
                .retryCount(event.getRetryCount())
                .createdAt(event.getCreatedAt())
                .build();
    }
}
