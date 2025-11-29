package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException(String field, String value) {
        super(String.format("%s already exists: %s", field, value));
    }
}
