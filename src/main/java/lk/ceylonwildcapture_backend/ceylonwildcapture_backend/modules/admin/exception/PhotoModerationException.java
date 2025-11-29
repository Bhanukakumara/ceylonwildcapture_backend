package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.exception;

/**
 * Exception thrown when photo moderation operations fail.
 */
public class PhotoModerationException extends RuntimeException {
    public PhotoModerationException(String message) {
        super(message);
    }

    public PhotoModerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
