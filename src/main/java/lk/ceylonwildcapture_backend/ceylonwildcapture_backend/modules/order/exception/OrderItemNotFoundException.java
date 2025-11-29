package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.exception;

/**
 * Exception thrown when an order item is not found.
 */
public class OrderItemNotFoundException extends RuntimeException {

    public OrderItemNotFoundException(String message) {
        super(message);
    }

    public OrderItemNotFoundException(Long orderItemId) {
        super("Order item not found with ID: " + orderItemId);
    }

    public OrderItemNotFoundException(String field, String value) {
        super(String.format("Order item not found with %s: %s", field, value));
    }
}
