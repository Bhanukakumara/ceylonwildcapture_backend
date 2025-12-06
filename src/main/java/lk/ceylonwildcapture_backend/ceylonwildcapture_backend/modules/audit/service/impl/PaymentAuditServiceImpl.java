package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.dto.PaymentAuditDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.PaymentAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.mapper.AuditMapper;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository.PaymentAuditRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.service.PaymentAuditService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentAuditServiceImpl implements PaymentAuditService {

    private final PaymentAuditRepository repository;
    private final AuditMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void recordPaymentEvent(Long userId, Long paymentId, Long orderId, String action, ActionResult result,
            BigDecimal amount, String currency, String metadata, String errorMessage, String ipAddress) {
        User user = entityManager.getReference(User.class, userId);

        PaymentAudit audit = PaymentAudit.builder()
                .user(user)
                .paymentId(paymentId)
                .orderId(orderId)
                .action(action)
                .actionResult(result)
                .amount(amount)
                .currency(currency)
                .metadata(metadata)
                .errorMessage(errorMessage)
                .ipAddress(ipAddress)
                .build();

        repository.save(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentAuditDto> getPaymentAuditsByUser(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable).map(mapper::toPaymentAuditDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentAuditDto> getPaymentAuditsByOrder(Long orderId, Pageable pageable) {
        return repository.findByOrderId(orderId, pageable).map(mapper::toPaymentAuditDto);
    }
}
