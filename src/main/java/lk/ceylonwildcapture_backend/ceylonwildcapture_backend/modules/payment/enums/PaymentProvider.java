package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums;

/**
 * Enum representing payment gateway providers.
 */
public enum PaymentProvider {
    /**
     * Stripe payment gateway.
     */
    STRIPE,

    /**
     * PayPal payment gateway.
     */
    PAYPAL,

    /**
     * Razorpay payment gateway.
     */
    RAZORPAY,

    /**
     * Local payment gateway.
     */
    LOCAL_GATEWAY,

    /**
     * Bank transfer.
     */
    BANK_TRANSFER,

    /**
     * Manual payment (admin processed).
     */
    MANUAL,

    /**
     * Test payment gateway for development.
     */
    TEST
}
