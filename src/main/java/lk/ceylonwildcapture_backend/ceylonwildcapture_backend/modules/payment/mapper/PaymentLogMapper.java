package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.TransactionDetailsDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity.PaymentLog;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper interface for converting PaymentLog entities to DTOs.
 */
public interface PaymentLogMapper {

    /**
     * Convert PaymentLog entity to TransactionDetailsDto.
     *
     * @param paymentLog payment log entity
     * @return transaction details DTO
     */
    TransactionDetailsDto toTransactionDetailsDto(PaymentLog paymentLog);

    /**
     * Convert list of PaymentLog entities to list of TransactionDetailsDto.
     *
     * @param paymentLogs list of payment log entities
     * @return list of transaction details DTOs
     */
    List<TransactionDetailsDto> toTransactionDetailsDtoList(List<PaymentLog> paymentLogs);

    /**
     * Convert Page of PaymentLog entities to Page of TransactionDetailsDto.
     *
     * @param paymentLogs page of payment log entities
     * @return page of transaction details DTOs
     */
    Page<TransactionDetailsDto> toTransactionDetailsDtoPage(Page<PaymentLog> paymentLogs);
}
