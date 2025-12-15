package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.exception;

/**
 * Exception thrown when a payout record is not found.
 */
public class PayoutNotFoundException extends RuntimeException {
    public PayoutNotFoundException(String message) {
        super(message);
    }

    public PayoutNotFoundException(Long payoutId) {
        super("Payout not found with ID: " + payoutId);
    }

    public PayoutNotFoundException(String field, String value) {
        super(String.format("Payout not found with %s: %s", field, value));
    }
}
