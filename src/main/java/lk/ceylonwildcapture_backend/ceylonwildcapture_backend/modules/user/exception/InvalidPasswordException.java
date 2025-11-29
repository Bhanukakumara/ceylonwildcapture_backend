package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException() {
        super("Password does not meet the required criteria");
    }
}
