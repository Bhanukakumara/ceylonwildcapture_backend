package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException() {
        super("Account is locked. Please contact support.");
    }
}
