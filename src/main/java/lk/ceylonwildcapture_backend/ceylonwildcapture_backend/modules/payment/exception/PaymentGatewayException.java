package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.exception;

/**
 * Exception thrown when a payment gateway operation fails.
 */
public class PaymentGatewayException extends RuntimeException {

    private final String gatewayErrorCode;
    private final String gatewayErrorMessage;

    public PaymentGatewayException(String message) {
        super(message);
        this.gatewayErrorCode = null;
        this.gatewayErrorMessage = null;
    }

    public PaymentGatewayException(String message, Throwable cause) {
        super(message, cause);
        this.gatewayErrorCode = null;
        this.gatewayErrorMessage = null;
    }

    public PaymentGatewayException(String message, String gatewayErrorCode, String gatewayErrorMessage) {
        super(message);
        this.gatewayErrorCode = gatewayErrorCode;
        this.gatewayErrorMessage = gatewayErrorMessage;
    }

    public String getGatewayErrorCode() {
        return gatewayErrorCode;
    }

    public String getGatewayErrorMessage() {
        return gatewayErrorMessage;
    }
}
