package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.exception;

/**
 * Exception thrown when an invalid order operation is attempted.
 */
public class InvalidOrderOperationException extends RuntimeException {

    public InvalidOrderOperationException(String message) {
        super(message);
    }

    public InvalidOrderOperationException(String operation, String reason) {
        super(String.format("Invalid operation '%s': %s", operation, reason));
    }
}
