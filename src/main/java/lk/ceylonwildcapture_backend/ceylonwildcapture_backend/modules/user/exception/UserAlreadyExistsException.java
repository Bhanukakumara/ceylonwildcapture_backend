package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
