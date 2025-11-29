package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.exception;

/**
 * Exception thrown when attempting an invalid payment status transition.
 */
public class InvalidPaymentStatusException extends RuntimeException {

    public InvalidPaymentStatusException(String message) {
        super(message);
    }

    public InvalidPaymentStatusException(String currentStatus, String targetStatus) {
        super(String.format("Invalid status transition from %s to %s", currentStatus, targetStatus));
    }
}
