package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.exception;

/**
 * Exception thrown when a license is not found.
 */
public class LicenseNotFoundException extends RuntimeException {

    public LicenseNotFoundException(String message) {
        super(message);
    }

    public LicenseNotFoundException(Long licenseId) {
        super("License not found with ID: " + licenseId);
    }

    public LicenseNotFoundException(String field, String value) {
        super(String.format("License not found with %s: %s", field, value));
    }
}
