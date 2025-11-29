package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.gateway;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.WebhookEventType;

import java.util.Map;

/**
 * Interface for processing webhook events from payment gateways.
 */
public interface WebhookProcessor {

    /**
     * Process webhook event.
     *
     * @param eventPayload event payload
     * @param signature webhook signature
     */
    void processWebhook(String eventPayload, String signature);

    /**
     * Parse webhook event.
     *
     * @param eventPayload event payload
     * @return parsed event data
     */
    Map<String, Object> parseWebhookEvent(String eventPayload);

    /**
     * Extract event type.
     *
     * @param eventPayload event payload
     * @return webhook event type
     */
    WebhookEventType extractEventType(String eventPayload);

    /**
     * Extract payment ID.
     *
     * @param eventPayload event payload
     * @return payment ID
     */
    String extractPaymentId(String eventPayload);

    /**
     * Verify webhook signature.
     *
     * @param payload event payload
     * @param signature signature header
     * @return true if valid
     */
    boolean verifyWebhookSignature(String payload, String signature);

    /**
     * Get supported provider.
     *
     * @return payment provider
     */
    PaymentProvider getProvider();
}
