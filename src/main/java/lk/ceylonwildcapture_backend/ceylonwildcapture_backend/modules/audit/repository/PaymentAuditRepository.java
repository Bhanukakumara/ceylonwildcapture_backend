package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.PaymentAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PaymentAuditRepository
        extends JpaRepository<PaymentAudit, Long>, JpaSpecificationExecutor<PaymentAudit> {
    Page<PaymentAudit> findByUserId(Long userId, Pageable pageable);

    Page<PaymentAudit> findByOrderId(Long orderId, Pageable pageable);

    Page<PaymentAudit> findByPaymentId(Long paymentId, Pageable pageable);

    Page<PaymentAudit> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
