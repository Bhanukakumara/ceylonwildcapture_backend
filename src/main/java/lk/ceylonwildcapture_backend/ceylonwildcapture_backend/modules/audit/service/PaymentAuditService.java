package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.PaymentAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface PaymentAuditService {
    void recordPaymentEvent(Long userId, Long paymentId, Long orderId, String action, ActionResult result,
            BigDecimal amount, String currency, String metadata, String errorMessage, String ipAddress);

    Page<PaymentAuditDto> getPaymentAuditsByUser(Long userId, Pageable pageable);

    Page<PaymentAuditDto> getPaymentAuditsByOrder(Long orderId, Pageable pageable);
}
