package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.TransactionDetailsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.TransactionType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for payment log operations.
 */
@RestController
@RequestMapping("/api/v1/payments/logs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PaymentLogController {

    private final PaymentLogService paymentLogService;

    /**
     * Get payment logs for payment.
     *
     * @param paymentId payment ID
     * @param pageable pagination parameters
     * @return page of transaction details
     */
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<Page<TransactionDetailsDto>> getPaymentLogs(
            @PathVariable Long paymentId,
            Pageable pageable) {
        Page<TransactionDetailsDto> logs = paymentLogService.getPaymentLogs(paymentId, pageable);
        return ResponseEntity.ok(logs);
    }

    /**
     * Get payment logs list for payment.
     *
     * @param paymentId payment ID
     * @return list of transaction details
     */
    @GetMapping("/payment/{paymentId}/list")
    public ResponseEntity<List<TransactionDetailsDto>> getPaymentLogsList(@PathVariable Long paymentId) {
        List<TransactionDetailsDto> logs = paymentLogService.getPaymentLogsList(paymentId);
        return ResponseEntity.ok(logs);
    }

    /**
     * Get logs by transaction type.
     *
     * @param transactionType transaction type
     * @param pageable pagination parameters
     * @return page of transaction details
     */
    @GetMapping("/type/{transactionType}")
    public ResponseEntity<Page<TransactionDetailsDto>> getLogsByTransactionType(
            @PathVariable TransactionType transactionType,
            Pageable pageable) {
        Page<TransactionDetailsDto> logs = paymentLogService.getLogsByTransactionType(transactionType, pageable);
        return ResponseEntity.ok(logs);
    }

    /**
     * Get failed transactions.
     *
     * @param pageable pagination parameters
     * @return page of failed transaction details
     */
    @GetMapping("/failed")
    public ResponseEntity<Page<TransactionDetailsDto>> getFailedTransactions(Pageable pageable) {
        Page<TransactionDetailsDto> logs = paymentLogService.getFailedTransactions(pageable);
        return ResponseEntity.ok(logs);
    }

    /**
     * Delete old payment logs.
     *
     * @param daysToKeep days to keep logs
     * @return number of deleted logs
     */
    @DeleteMapping("/cleanup")
    public ResponseEntity<Long> deleteOldPaymentLogs(@RequestParam(defaultValue = "90") int daysToKeep) {
        long deletedCount = paymentLogService.deleteOldPaymentLogs(daysToKeep);
        return ResponseEntity.ok(deletedCount);
    }
}
