package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for transaction details (payment log).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDetailsDto {

    private Long id;
    private Long paymentId;
    private TransactionType transactionType;
    private String providerReference;
    private Integer httpStatusCode;
    private String status;
    private String errorMessage;
    private String errorCode;
    private String ipAddress;
    private LocalDateTime createdAt;
}
