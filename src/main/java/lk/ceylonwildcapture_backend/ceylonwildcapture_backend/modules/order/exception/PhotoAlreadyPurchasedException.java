package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.exception;

/**
 * Exception thrown when attempting to purchase a photo that has already been purchased.
 */
public class PhotoAlreadyPurchasedException extends RuntimeException {

    public PhotoAlreadyPurchasedException(String message) {
        super(message);
    }

    public PhotoAlreadyPurchasedException(Long photoId, Long buyerId) {
        super(String.format("Photo %d has already been purchased by user %d", photoId, buyerId));
    }
}
