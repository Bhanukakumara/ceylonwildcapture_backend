package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.PaymentAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.PaymentAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for payment audit operations.
 * Handles payment transaction tracking and financial audit logs.
 */
@RestController
@RequestMapping("/api/v1/audit/payments")
@RequiredArgsConstructor
@CrossOrigin
public class PaymentAuditController {

    private final PaymentAuditService paymentAuditService;

    // ------------------------------
    // Get Payment Audit by ID
    // ------------------------------
    @GetMapping("/{auditId}")
    public ResponseEntity<PaymentAuditDto> getPaymentAuditById(@PathVariable Long auditId) {
        return paymentAuditService.getPaymentAuditById(auditId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------------
    // Get Payments by User
    // ------------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PaymentAuditDto>> getPaymentsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(paymentAuditService.getPaymentsByUser(userId, pageable));
    }

    // ------------------------------
    // Get Successful Payments by User
    // ------------------------------
    @GetMapping("/user/{userId}/successful")
    public ResponseEntity<Page<PaymentAuditDto>> getSuccessfulPaymentsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(paymentAuditService.getSuccessfulPaymentsByUser(userId, pageable));
    }

    // ------------------------------
    // Get Failed Payments by User
    // ------------------------------
    @GetMapping("/user/{userId}/failed")
    public ResponseEntity<Page<PaymentAuditDto>> getFailedPaymentsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(paymentAuditService.getFailedPaymentsByUser(userId, pageable));
    }

    // ------------------------------
    // Get Payments by Date Range
    // ------------------------------
    @GetMapping("/date-range")
    public ResponseEntity<Page<PaymentAuditDto>> getPaymentsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(paymentAuditService.getPaymentsByDateRange(startDate, endDate, pageable));
    }

    // ------------------------------
    // Get Payments by Method
    // ------------------------------
    @GetMapping("/method/{paymentMethod}")
    public ResponseEntity<Page<PaymentAuditDto>> getPaymentsByMethod(
            @PathVariable String paymentMethod,
            Pageable pageable) {
        return ResponseEntity.ok(paymentAuditService.getPaymentsByMethod(paymentMethod, pageable));
    }

    // ------------------------------
    // Get Payments by Result
    // ------------------------------
    @GetMapping("/result/{result}")
    public ResponseEntity<Page<PaymentAuditDto>> getPaymentsByResult(
            @PathVariable ActionResult result,
            Pageable pageable) {
        return ResponseEntity.ok(paymentAuditService.getPaymentsByResult(result, pageable));
    }

    // ------------------------------
    // Get Payments by Order
    // ------------------------------
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Page<PaymentAuditDto>> getPaymentsByOrder(
            @PathVariable Long orderId,
            Pageable pageable) {
        return ResponseEntity.ok(paymentAuditService.getPaymentsByOrder(orderId, pageable));
    }

    // ------------------------------
    // Count Payments for User
    // ------------------------------
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Object>> countPaymentsForUser(@PathVariable Long userId) {
        long count = paymentAuditService.countPaymentsForUser(userId);
        return ResponseEntity.ok(Map.of(
                "userId", userId,
                "paymentCount", count
        ));
    }

    // ------------------------------
    // Get Total Payment Amount
    // ------------------------------
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<Map<String, Object>> getTotalPaymentAmount(
            @PathVariable Long userId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        var totalAmount = paymentAuditService.getTotalPaymentAmountForUser(userId, startDate, endDate);
        return ResponseEntity.ok(Map.of(
                "userId", userId,
                "totalAmount", totalAmount,
                "startDate", startDate,
                "endDate", endDate
        ));
    }

    // ------------------------------
    // Get Payment Statistics
    // ------------------------------
    @GetMapping("/user/{userId}/statistics")
    public ResponseEntity<Map<String, Object>> getPaymentStatistics(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentAuditService.getPaymentStatistics(userId));
    }

    // ------------------------------
    // Get Payment Method Statistics
    // ------------------------------
    @GetMapping("/statistics/methods")
    public ResponseEntity<Map<String, Object>> getPaymentMethodStatistics() {
        return ResponseEntity.ok(paymentAuditService.getPaymentMethodStatistics());
    }
}
