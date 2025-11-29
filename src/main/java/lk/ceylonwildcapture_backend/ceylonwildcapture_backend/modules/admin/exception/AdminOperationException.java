package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.exception;

/**
 * Exception thrown when an admin operation fails.
 */
public class AdminOperationException extends RuntimeException {
    public AdminOperationException(String message) {
        super(message);
    }

    public AdminOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
