package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.exception;

/**
 * Exception thrown when download limit is exceeded for a license.
 */
public class DownloadLimitExceededException extends RuntimeException {

    public DownloadLimitExceededException(String message) {
        super(message);
    }

    public DownloadLimitExceededException(String licenseKey, int limit) {
        super(String.format("Download limit exceeded for license %s (limit: %d)", licenseKey, limit));
    }
}
