package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentFailureCallbackDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentSuccessCallbackDto;

/**
 * Service interface for handling payment callbacks.
 */
public interface PaymentCallbackHandler {

    /**
     * Handle payment success callback.
     *
     * @param callbackDto payment success callback
     * @return payment response
     */
    PaymentResponseDto handlePaymentSuccess(PaymentSuccessCallbackDto callbackDto);

    /**
     * Handle payment failure callback.
     *
     * @param callbackDto payment failure callback
     * @return payment response
     */
    PaymentResponseDto handlePaymentFailure(PaymentFailureCallbackDto callbackDto);

    /**
     * Handle payment cancellation.
     *
     * @param paymentId payment ID
     * @return payment response
     */
    PaymentResponseDto handlePaymentCancellation(String paymentId);

    /**
     * Generate success redirect URL.
     *
     * @param paymentId payment ID
     * @return success redirect URL
     */
    String generateSuccessRedirectUrl(String paymentId);

    /**
     * Generate failure redirect URL.
     *
     * @param paymentId payment ID
     * @param errorMessage error message
     * @return failure redirect URL
     */
    String generateFailureRedirectUrl(String paymentId, String errorMessage);

    /**
     * Generate cancel redirect URL.
     *
     * @param paymentId payment ID
     * @return cancel redirect URL
     */
    String generateCancelRedirectUrl(String paymentId);
}
