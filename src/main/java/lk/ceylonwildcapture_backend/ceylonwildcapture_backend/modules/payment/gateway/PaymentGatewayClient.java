package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.gateway;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.GatewayPayloadDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentIntentResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.RefundRequestDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;

/**
 * Interface for payment gateway client operations.
 */
public interface PaymentGatewayClient {

    /**
     * Create payment intent with gateway.
     *
     * @param payload gateway payload
     * @return payment intent response
     */
    PaymentIntentResponseDto createPaymentIntent(GatewayPayloadDto payload);

    /**
     * Capture payment.
     *
     * @param paymentId payment ID
     * @return payment response
     */
    PaymentIntentResponseDto capturePayment(String paymentId);

    /**
     * Cancel payment.
     *
     * @param paymentId payment ID
     * @return payment response
     */
    PaymentIntentResponseDto cancelPayment(String paymentId);

    /**
     * Process refund.
     *
     * @param refundRequest refund request
     * @return refund response
     */
    PaymentIntentResponseDto processRefund(RefundRequestDto refundRequest);

    /**
     * Retrieve payment details from gateway.
     *
     * @param paymentId payment ID
     * @return payment response
     */
    PaymentIntentResponseDto retrievePayment(String paymentId);

    /**
     * Verify payment with gateway.
     *
     * @param paymentId payment ID
     * @param signature signature
     * @return verification result
     */
    boolean verifyPayment(String paymentId, String signature);

    /**
     * Get supported provider.
     *
     * @return payment provider
     */
    PaymentProvider getProvider();

    /**
     * Check if gateway is available.
     *
     * @return true if available
     */
    boolean isAvailable();
}
