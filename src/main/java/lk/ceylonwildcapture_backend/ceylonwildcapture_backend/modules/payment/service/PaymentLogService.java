package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.TransactionDetailsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity.PaymentLog;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for payment log operations.
 */
public interface PaymentLogService {

    /**
     * Create payment log.
     *
     * @param paymentId payment ID
     * @param transactionType transaction type
     * @param requestPayload request payload
     * @param responsePayload response payload
     * @param status status
     * @return created payment log
     */
    PaymentLog createPaymentLog(Long paymentId, TransactionType transactionType,
                                 String requestPayload, String responsePayload, String status);

    /**
     * Log transaction.
     *
     * @param paymentId payment ID
     * @param transactionType transaction type
     * @param providerReference provider reference
     * @param httpStatusCode HTTP status code
     * @param status status
     * @param errorMessage error message
     * @return created payment log
     */
    PaymentLog logTransaction(Long paymentId, TransactionType transactionType, String providerReference,
                               Integer httpStatusCode, String status, String errorMessage);

    /**
     * Get payment logs for payment.
     *
     * @param paymentId payment ID
     * @param pageable pagination parameters
     * @return page of transaction details
     */
    Page<TransactionDetailsDto> getPaymentLogs(Long paymentId, Pageable pageable);

    /**
     * Get payment logs for payment (list).
     *
     * @param paymentId payment ID
     * @return list of transaction details
     */
    List<TransactionDetailsDto> getPaymentLogsList(Long paymentId);

    /**
     * Get logs by transaction type.
     *
     * @param transactionType transaction type
     * @param pageable pagination parameters
     * @return page of transaction details
     */
    Page<TransactionDetailsDto> getLogsByTransactionType(TransactionType transactionType, Pageable pageable);

    /**
     * Get failed transactions.
     *
     * @param pageable pagination parameters
     * @return page of failed transaction details
     */
    Page<TransactionDetailsDto> getFailedTransactions(Pageable pageable);

    /**
     * Delete old payment logs.
     *
     * @param daysToKeep days to keep logs
     * @return number of deleted logs
     */
    long deleteOldPaymentLogs(int daysToKeep);
}
