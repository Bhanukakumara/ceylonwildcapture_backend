package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.WebhookEventDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity.WebhookEvent;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentWebhookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PaymentWebhookServiceImpl implements PaymentWebhookService {

    @Override
    public WebhookEventDto handleGatewayWebhook(PaymentProvider provider, String payload, String signature, String ipAddress) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void processWebhookEvent(Long eventId) {
        // TODO: Implement actual business logic
    }

    @Override
    public void processWebhookEventByEventId(String eventId) {
        // TODO: Implement actual business logic
    }

    @Override
    public void retryWebhookProcessing(Long eventId) {
        // TODO: Implement actual business logic
    }

    @Override
    public List<WebhookEvent> getUnprocessedEvents(PaymentProvider provider) {
        // TODO: Implement actual business logic
        return Collections.emptyList();
    }

    @Override
    public Page<WebhookEventDto> getWebhookEventsByPaymentId(String paymentId, Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<WebhookEventDto> getWebhookEventsByProvider(PaymentProvider provider, Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<WebhookEventDto> getFailedWebhookEvents(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public void markEventAsProcessed(Long eventId) {
        // TODO: Implement actual business logic
    }

    @Override
    public void markEventAsFailed(Long eventId, String errorMessage) {
        // TODO: Implement actual business logic
    }

    @Override
    public long deleteOldProcessedEvents(int daysToKeep) {
        // TODO: Implement actual business logic
        return 0;
    }
}
