package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums;

/**
 * Enum representing webhook event types from payment gateways.
 */
public enum WebhookEventType {
    /**
     * Payment succeeded event.
     */
    PAYMENT_SUCCEEDED,

    /**
     * Payment failed event.
     */
    PAYMENT_FAILED,

    /**
     * Payment cancelled event.
     */
    PAYMENT_CANCELLED,

    /**
     * Payment refunded event.
     */
    PAYMENT_REFUNDED,

    /**
     * Payment disputed event.
     */
    PAYMENT_DISPUTED,

    /**
     * Charge updated event.
     */
    CHARGE_UPDATED,

    /**
     * Charge captured event.
     */
    CHARGE_CAPTURED,

    /**
     * Refund updated event.
     */
    REFUND_UPDATED,

    /**
     * Unknown or unhandled event.
     */
    UNKNOWN
}
