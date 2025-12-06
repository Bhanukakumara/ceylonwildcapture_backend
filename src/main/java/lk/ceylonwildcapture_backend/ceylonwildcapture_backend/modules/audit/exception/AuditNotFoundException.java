package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.exception;

/**
 * Exception thrown when an audit record is not found.
 */
public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(String message) {
        super(message);
    }

    public AuditNotFoundException(Long auditId) {
        super("Audit record not found with ID: " + auditId);
    }

    public AuditNotFoundException(String field, String value) {
        super(String.format("Audit record not found with %s: %s", field, value));
    }
}
