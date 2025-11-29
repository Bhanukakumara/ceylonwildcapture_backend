package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.exception;

/**
 * Exception thrown when payout review operations fail.
 */
public class PayoutReviewException extends RuntimeException {
    public PayoutReviewException(String message) {
        super(message);
    }

    public PayoutReviewException(String message, Throwable cause) {
        super(message, cause);
    }
}
