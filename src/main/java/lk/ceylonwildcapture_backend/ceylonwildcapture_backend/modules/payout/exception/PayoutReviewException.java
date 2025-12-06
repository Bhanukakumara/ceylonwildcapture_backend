package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.exception;

/**
 * Exception thrown when a payout review operation fails.
 */
public class PayoutReviewException extends RuntimeException {
    public PayoutReviewException(String message) {
        super(message);
    }

    public PayoutReviewException(String message, Throwable cause) {
        super(message, cause);
    }
}
