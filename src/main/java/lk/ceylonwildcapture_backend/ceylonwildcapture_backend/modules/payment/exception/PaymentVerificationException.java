package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.exception;

/**
 * Exception thrown when payment verification fails.
 */
public class PaymentVerificationException extends RuntimeException {

    public PaymentVerificationException(String message) {
        super(message);
    }

    public PaymentVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentVerificationException(String reason, String paymentId) {
        super(String.format("Payment verification failed for payment %s: %s", paymentId, reason));
    }
}
