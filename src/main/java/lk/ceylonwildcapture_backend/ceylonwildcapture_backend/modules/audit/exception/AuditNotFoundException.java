package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.exception;

public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(String message) {
        super(message);
    }
}
