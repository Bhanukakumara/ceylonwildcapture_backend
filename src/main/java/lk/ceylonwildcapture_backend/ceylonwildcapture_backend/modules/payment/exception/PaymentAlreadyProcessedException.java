package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.exception;

/**
 * Exception thrown when attempting to process an already processed payment.
 */
public class PaymentAlreadyProcessedException extends RuntimeException {

    public PaymentAlreadyProcessedException(String message) {
        super(message);
    }

    public PaymentAlreadyProcessedException(String paymentId, String status) {
        super(String.format("Payment %s has already been processed with status: %s", paymentId, status));
    }
}
