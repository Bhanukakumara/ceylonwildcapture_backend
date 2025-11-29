package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
        super("Invalid or expired token");
    }
}
