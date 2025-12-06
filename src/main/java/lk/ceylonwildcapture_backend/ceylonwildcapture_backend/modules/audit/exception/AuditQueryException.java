package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.exception;

/**
 * Exception thrown when an audit query operation fails.
 */
public class AuditQueryException extends RuntimeException {
    public AuditQueryException(String message) {
        super(message);
    }

    public AuditQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
