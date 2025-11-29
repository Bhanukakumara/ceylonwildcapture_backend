package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.exception;

/**
 * Exception thrown when attempting to use an expired license.
 */
public class LicenseExpiredException extends RuntimeException {

    public LicenseExpiredException(String message) {
        super(message);
    }

    public static LicenseExpiredException forLicenseKey(String licenseKey) {
        return new LicenseExpiredException("License has expired: " + licenseKey);
    }
}
