package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.TransactionDetailsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity.PaymentLog;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.TransactionType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PaymentLogServiceImpl implements PaymentLogService {

    @Override
    public PaymentLog createPaymentLog(Long paymentId, TransactionType transactionType, String requestPayload, String responsePayload, String status) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PaymentLog logTransaction(Long paymentId, TransactionType transactionType, String providerReference, Integer httpStatusCode, String status, String errorMessage) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Page<TransactionDetailsDto> getPaymentLogs(Long paymentId, Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public List<TransactionDetailsDto> getPaymentLogsList(Long paymentId) {
        // TODO: Implement actual business logic
        return Collections.emptyList();
    }

    @Override
    public Page<TransactionDetailsDto> getLogsByTransactionType(TransactionType transactionType, Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<TransactionDetailsDto> getFailedTransactions(Pageable pageable) {
        // TODO: Implement actual business logic
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public long deleteOldPaymentLogs(int daysToKeep) {
        // TODO: Implement actual business logic
        return 0;
    }
}
