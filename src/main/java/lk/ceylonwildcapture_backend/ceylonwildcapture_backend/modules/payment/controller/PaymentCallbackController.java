package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.controller;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentFailureCallbackDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentSuccessCallbackDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentVerificationRequestDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentVerificationResultDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentCallbackHandler;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for payment callbacks.
 */
@RestController
@RequestMapping("/api/v1/payments/callback")
@RequiredArgsConstructor
public class PaymentCallbackController {

    private final PaymentCallbackHandler callbackHandler;
    private final PaymentValidationService validationService;

    /**
     * Handle payment success callback.
     *
     * @param callbackDto payment success callback
     * @return payment response
     */
    @PostMapping("/success")
    public ResponseEntity<PaymentResponseDto> handleSuccess(@Valid @RequestBody PaymentSuccessCallbackDto callbackDto) {
        PaymentResponseDto response = callbackHandler.handlePaymentSuccess(callbackDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Handle payment failure callback.
     *
     * @param callbackDto payment failure callback
     * @return payment response
     */
    @PostMapping("/failure")
    public ResponseEntity<PaymentResponseDto> handleFailure(@Valid @RequestBody PaymentFailureCallbackDto callbackDto) {
        PaymentResponseDto response = callbackHandler.handlePaymentFailure(callbackDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Handle payment cancellation callback.
     *
     * @param paymentId payment ID
     * @return payment response
     */
    @PostMapping("/cancel")
    public ResponseEntity<PaymentResponseDto> handleCancel(@RequestParam String paymentId) {
        PaymentResponseDto response = callbackHandler.handlePaymentCancellation(paymentId);
        return ResponseEntity.ok(response);
    }

    /**
     * Verify payment.
     *
     * @param requestDto verification request
     * @return verification result
     */
    @PostMapping("/verify")
    public ResponseEntity<PaymentVerificationResultDto> verifyPayment(
            @Valid @RequestBody PaymentVerificationRequestDto requestDto) {
        PaymentVerificationResultDto result = validationService.verifyPayment(requestDto);
        return ResponseEntity.ok(result);
    }

    /**
     * Get success redirect URL.
     *
     * @param paymentId payment ID
     * @return redirect URL
     */
    @GetMapping("/redirect/success")
    public ResponseEntity<String> getSuccessRedirectUrl(@RequestParam String paymentId) {
        String redirectUrl = callbackHandler.generateSuccessRedirectUrl(paymentId);
        return ResponseEntity.ok(redirectUrl);
    }

    /**
     * Get failure redirect URL.
     *
     * @param paymentId payment ID
     * @param errorMessage error message
     * @return redirect URL
     */
    @GetMapping("/redirect/failure")
    public ResponseEntity<String> getFailureRedirectUrl(
            @RequestParam String paymentId,
            @RequestParam(required = false) String errorMessage) {
        String redirectUrl = callbackHandler.generateFailureRedirectUrl(paymentId, errorMessage);
        return ResponseEntity.ok(redirectUrl);
    }

    /**
     * Get cancel redirect URL.
     *
     * @param paymentId payment ID
     * @return redirect URL
     */
    @GetMapping("/redirect/cancel")
    public ResponseEntity<String> getCancelRedirectUrl(@RequestParam String paymentId) {
        String redirectUrl = callbackHandler.generateCancelRedirectUrl(paymentId);
        return ResponseEntity.ok(redirectUrl);
    }
}
