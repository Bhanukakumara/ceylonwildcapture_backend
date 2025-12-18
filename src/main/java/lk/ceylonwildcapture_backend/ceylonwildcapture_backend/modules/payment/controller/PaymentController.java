package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.controller;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller for payment operations.
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Create payment intent.
     *
     * @param requestDto payment intent request
     * @param userId     authenticated user ID
     * @return payment intent response
     */
    @PostMapping("/intent")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<PaymentIntentResponseDto> createPaymentIntent(
            @Valid @RequestBody CreatePaymentIntentRequestDto requestDto,
            @RequestAttribute("userId") Long userId) {
        PaymentIntentResponseDto response = paymentService.createPaymentIntent(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get payment by ID.
     *
     * @param paymentId payment ID
     * @param userId    authenticated user ID
     * @return payment response
     */
    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<PaymentResponseDto> getPayment(
            @PathVariable Long paymentId,
            @RequestAttribute("userId") Long userId) {
        PaymentResponseDto response = paymentService.getPaymentById(paymentId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get payment by provider payment ID.
     *
     * @param providerPaymentId provider payment ID
     * @return payment response
     */
    @GetMapping("/provider/{providerPaymentId}")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<PaymentResponseDto> getPaymentByProviderPaymentId(@PathVariable String providerPaymentId) {
        PaymentResponseDto response = paymentService.getPaymentByProviderPaymentId(providerPaymentId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get payment by order ID.
     *
     * @param orderId order ID
     * @return payment response
     */
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<PaymentResponseDto> getPaymentByOrderId(@PathVariable Long orderId) {
        PaymentResponseDto response = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get current user's payments.
     *
     * @param userId   authenticated user ID
     * @param pageable pagination parameters
     * @return page of payment responses
     */
    @GetMapping("/my-payments")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER')")
    public ResponseEntity<Page<PaymentResponseDto>> getMyPayments(
            @RequestAttribute("userId") Long userId,
            Pageable pageable) {
        Page<PaymentResponseDto> payments = paymentService.getUserPayments(userId, pageable);
        return ResponseEntity.ok(payments);
    }

    /**
     * Get payments by status (admin only).
     *
     * @param status   payment status
     * @param pageable pagination parameters
     * @return page of payment responses
     */
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PaymentResponseDto>> getPaymentsByStatus(
            @PathVariable PaymentStatus status,
            Pageable pageable) {
        Page<PaymentResponseDto> payments = paymentService.getPaymentsByStatus(status, pageable);
        return ResponseEntity.ok(payments);
    }

    /**
     * Get payments by date range (admin only).
     *
     * @param startDate start date
     * @param endDate   end date
     * @param pageable  pagination parameters
     * @return page of payment responses
     */
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PaymentResponseDto>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        Page<PaymentResponseDto> payments = paymentService.getPaymentsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(payments);
    }

    /**
     * Process refund (admin only).
     *
     * @param refundRequest refund request
     * @return updated payment response
     */
    @PostMapping("/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponseDto> processRefund(@Valid @RequestBody RefundRequestDto refundRequest) {
        PaymentResponseDto response = paymentService.processRefund(refundRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancel payment.
     *
     * @param paymentId payment ID
     * @param userId    authenticated user ID
     * @return updated payment response
     */
    @PostMapping("/{paymentId}/cancel")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER')")
    public ResponseEntity<PaymentResponseDto> cancelPayment(
            @PathVariable Long paymentId,
            @RequestAttribute("userId") Long userId) {
        PaymentResponseDto response = paymentService.cancelPayment(paymentId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Search payments (admin only).
     *
     * @param searchTerm search term
     * @param pageable   pagination parameters
     * @return page of payment responses
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PaymentResponseDto>> searchPayments(
            @RequestParam String searchTerm,
            Pageable pageable) {
        Page<PaymentResponseDto> payments = paymentService.searchPayments(searchTerm, pageable);
        return ResponseEntity.ok(payments);
    }

    /**
     * Get payment count for current user.
     *
     * @param userId authenticated user ID
     * @return payment count
     */
    @GetMapping("/my-payments/count")
    @PreAuthorize("hasAnyRole('BUYER', 'PHOTOGRAPHER')")
    public ResponseEntity<Long> getMyPaymentCount(@RequestAttribute("userId") Long userId) {
        long count = paymentService.countUserPayments(userId);
        return ResponseEntity.ok(count);
    }
}
