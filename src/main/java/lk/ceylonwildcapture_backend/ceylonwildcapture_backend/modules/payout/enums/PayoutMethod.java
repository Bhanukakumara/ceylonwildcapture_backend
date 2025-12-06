package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.enums;

/**
 * Enumeration of payout methods supported by the system.
 */
public enum PayoutMethod {
    /**
     * Direct bank transfer
     */
    BANK_TRANSFER,

    /**
     * PayPal payment
     */
    PAYPAL,

    /**
     * Manual payout (requires manual processing)
     */
    MANUAL,

    /**
     * Stripe Connect payout
     */
    STRIPE_CONNECT,

    /**
     * Cryptocurrency payment
     */
    CRYPTOCURRENCY
}
