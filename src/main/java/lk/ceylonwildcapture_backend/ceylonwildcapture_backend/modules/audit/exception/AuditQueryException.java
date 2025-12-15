package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.exception;

public class AuditQueryException extends RuntimeException {
    public AuditQueryException(String message) {
        super(message);
    }

    public AuditQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
