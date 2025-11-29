package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums;

/**
 * Enum representing transaction types in payment logs.
 */
public enum TransactionType {
    /**
     * Payment intent creation.
     */
    PAYMENT_INTENT_CREATED,

    /**
     * Payment authorization.
     */
    PAYMENT_AUTHORIZED,

    /**
     * Payment capture/completion.
     */
    PAYMENT_CAPTURED,

    /**
     * Payment confirmation.
     */
    PAYMENT_CONFIRMED,

    /**
     * Payment failure.
     */
    PAYMENT_FAILED,

    /**
     * Payment cancellation.
     */
    PAYMENT_CANCELLED,

    /**
     * Payment refund initiated.
     */
    REFUND_INITIATED,

    /**
     * Payment refund completed.
     */
    REFUND_COMPLETED,

    /**
     * Payment refund failed.
     */
    REFUND_FAILED,

    /**
     * Webhook received from gateway.
     */
    WEBHOOK_RECEIVED,

    /**
     * Payment verification.
     */
    PAYMENT_VERIFIED,

    /**
     * Payment status update.
     */
    STATUS_UPDATE,

    /**
     * Payment timeout.
     */
    PAYMENT_TIMEOUT
}
