package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.exception;

/**
 * Exception thrown when a payment is not found.
 */
public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(Long paymentId) {
        super("Payment not found with ID: " + paymentId);
    }

    public PaymentNotFoundException(String field, String value) {
        super(String.format("Payment not found with %s: %s", field, value));
    }
}
