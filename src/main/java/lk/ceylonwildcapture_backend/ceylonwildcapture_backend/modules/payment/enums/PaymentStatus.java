package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums;

/**
 * Enum representing payment status.
 */
public enum PaymentStatus {
    /**
     * Payment has been initiated but not yet processed.
     */
    PENDING,

    /**
     * Payment is being processed by the gateway.
     */
    PROCESSING,

    /**
     * Payment completed successfully.
     */
    SUCCESS,

    /**
     * Payment failed.
     */
    FAILED,

    /**
     * Payment was cancelled by user or system.
     */
    CANCELLED,

    /**
     * Payment is on hold for verification.
     */
    ON_HOLD,

    /**
     * Payment has been refunded.
     */
    REFUNDED,

    /**
     * Partial refund processed.
     */
    PARTIALLY_REFUNDED,

    /**
     * Payment expired before completion.
     */
    EXPIRED
}
