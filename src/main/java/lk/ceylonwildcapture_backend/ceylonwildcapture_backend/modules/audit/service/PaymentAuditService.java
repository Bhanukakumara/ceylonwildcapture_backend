package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.PaymentAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.PaymentAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for payment audit operations.
 * Provides methods for recording and retrieving payment audit logs.
 */
public interface PaymentAuditService {

    /**
     * Record a payment audit event.
     *
     * @param paymentAudit the payment audit entity
     * @return the saved payment audit entity
     */
    PaymentAudit recordPayment(PaymentAudit paymentAudit);

    /**
     * Get payment audit by ID.
     *
     * @param auditId the audit ID
     * @return optional containing the payment audit DTO
     */
    Optional<PaymentAuditDto> getPaymentAuditById(Long auditId);

    /**
     * Get all payment audits for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of payment audit DTOs
     */
    Page<PaymentAuditDto> getPaymentsByUser(Long userId, Pageable pageable);

    /**
     * Get successful payment audits for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of payment audit DTOs
     */
    Page<PaymentAuditDto> getSuccessfulPaymentsByUser(Long userId, Pageable pageable);

    /**
     * Get failed payment audits for a user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of payment audit DTOs
     */
    Page<PaymentAuditDto> getFailedPaymentsByUser(Long userId, Pageable pageable);

    /**
     * Get payment audits within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of payment audit DTOs
     */
    Page<PaymentAuditDto> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get payment audits by payment method.
     *
     * @param paymentMethod the payment method
     * @param pageable pagination information
     * @return page of payment audit DTOs
     */
    Page<PaymentAuditDto> getPaymentsByMethod(String paymentMethod, Pageable pageable);

    /**
     * Get payment audits by action result.
     *
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of payment audit DTOs
     */
    Page<PaymentAuditDto> getPaymentsByResult(ActionResult actionResult, Pageable pageable);

    /**
     * Get payment audits for a specific order.
     *
     * @param orderId the order ID
     * @param pageable pagination information
     * @return page of payment audit DTOs
     */
    Page<PaymentAuditDto> getPaymentsByOrder(Long orderId, Pageable pageable);

    /**
     * Count total payments for a user.
     *
     * @param userId the user ID
     * @return count of payments
     */
    long countPaymentsForUser(Long userId);

    /**
     * Get total payment amount for a user within date range.
     *
     * @param userId the user ID
     * @param startDate the start date
     * @param endDate the end date
     * @return total payment amount
     */
    BigDecimal getTotalPaymentAmountForUser(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get payment statistics for a user.
     *
     * @param userId the user ID
     * @return map containing payment statistics
     */
    java.util.Map<String, Object> getPaymentStatistics(Long userId);

    /**
     * Get payment statistics by method.
     *
     * @return map containing payment method statistics
     */
    java.util.Map<String, Object> getPaymentMethodStatistics();
}
