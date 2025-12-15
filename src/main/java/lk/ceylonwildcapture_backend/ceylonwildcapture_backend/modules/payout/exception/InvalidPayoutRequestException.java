package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.exception;

/**
 * Exception thrown when a payout request is invalid.
 */
public class InvalidPayoutRequestException extends RuntimeException {
    public InvalidPayoutRequestException(String message) {
        super(message);
    }

    public InvalidPayoutRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
