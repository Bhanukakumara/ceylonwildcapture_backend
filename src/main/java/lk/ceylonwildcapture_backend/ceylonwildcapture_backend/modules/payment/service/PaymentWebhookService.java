package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.WebhookEventDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity.WebhookEvent;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for payment webhook operations.
 */
public interface PaymentWebhookService {

    /**
     * Handle gateway webhook.
     *
     * @param provider payment provider
     * @param payload webhook payload
     * @param signature webhook signature
     * @param ipAddress IP address
     * @return webhook event DTO
     */
    WebhookEventDto handleGatewayWebhook(PaymentProvider provider, String payload, String signature, String ipAddress);

    /**
     * Process webhook event.
     *
     * @param eventId event ID
     */
    void processWebhookEvent(Long eventId);

    /**
     * Process webhook event by event ID.
     *
     * @param eventId event ID from provider
     */
    void processWebhookEventByEventId(String eventId);

    /**
     * Retry failed webhook processing.
     *
     * @param eventId event ID
     */
    void retryWebhookProcessing(Long eventId);

    /**
     * Get unprocessed webhook events.
     *
     * @param provider payment provider
     * @return list of unprocessed events
     */
    List<WebhookEvent> getUnprocessedEvents(PaymentProvider provider);

    /**
     * Get webhook events by payment ID.
     *
     * @param paymentId payment ID
     * @param pageable pagination parameters
     * @return page of webhook events
     */
    Page<WebhookEventDto> getWebhookEventsByPaymentId(String paymentId, Pageable pageable);

    /**
     * Get webhook events by provider.
     *
     * @param provider payment provider
     * @param pageable pagination parameters
     * @return page of webhook events
     */
    Page<WebhookEventDto> getWebhookEventsByProvider(PaymentProvider provider, Pageable pageable);

    /**
     * Get failed webhook events.
     *
     * @param pageable pagination parameters
     * @return page of failed webhook events
     */
    Page<WebhookEventDto> getFailedWebhookEvents(Pageable pageable);

    /**
     * Mark webhook event as processed.
     *
     * @param eventId event ID
     */
    void markEventAsProcessed(Long eventId);

    /**
     * Mark webhook event as failed.
     *
     * @param eventId event ID
     * @param errorMessage error message
     */
    void markEventAsFailed(Long eventId, String errorMessage);

    /**
     * Delete old processed events.
     *
     * @param daysToKeep days to keep processed events
     * @return number of deleted events
     */
    long deleteOldProcessedEvents(int daysToKeep);
}
