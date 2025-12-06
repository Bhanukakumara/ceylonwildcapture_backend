package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.UserActivityAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserActivityAuditRepository
        extends JpaRepository<UserActivityAudit, Long>, JpaSpecificationExecutor<UserActivityAudit> {
    Page<UserActivityAudit> findByUserId(Long userId, Pageable pageable);

    Page<UserActivityAudit> findByAction(String action, Pageable pageable);

    Page<UserActivityAudit> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
