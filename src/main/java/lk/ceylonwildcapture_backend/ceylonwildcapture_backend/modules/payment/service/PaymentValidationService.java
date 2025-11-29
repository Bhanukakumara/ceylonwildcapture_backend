package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentVerificationRequestDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentVerificationResultDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;

/**
 * Service interface for payment validation operations.
 */
public interface PaymentValidationService {

    /**
     * Validate payment signature.
     *
     * @param provider payment provider
     * @param payload payload data
     * @param signature signature to validate
     * @return true if valid
     */
    boolean validatePaymentSignature(PaymentProvider provider, String payload, String signature);

    /**
     * Verify payment.
     *
     * @param requestDto verification request
     * @return verification result
     */
    PaymentVerificationResultDto verifyPayment(PaymentVerificationRequestDto requestDto);

    /**
     * Verify webhook signature.
     *
     * @param provider payment provider
     * @param payload webhook payload
     * @param signature signature header
     * @return true if valid
     */
    boolean verifyWebhookSignature(PaymentProvider provider, String payload, String signature);

    /**
     * Validate payment amount.
     *
     * @param paymentId payment ID
     * @param expectedAmount expected amount
     * @return true if amounts match
     */
    boolean validatePaymentAmount(String paymentId, java.math.BigDecimal expectedAmount);

    /**
     * Validate payment status.
     *
     * @param paymentId payment ID
     * @return true if payment is valid and successful
     */
    boolean validatePaymentStatus(String paymentId);

    /**
     * Check if payment is duplicate.
     *
     * @param providerPaymentId provider payment ID
     * @return true if duplicate
     */
    boolean isDuplicatePayment(String providerPaymentId);

    /**
     * Validate payment timeout.
     *
     * @param paymentId payment ID
     * @param timeoutMinutes timeout in minutes
     * @return true if not timed out
     */
    boolean validatePaymentTimeout(Long paymentId, int timeoutMinutes);
}
