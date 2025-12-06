package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.LoginAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LoginAuditRepository extends JpaRepository<LoginAudit, Long>, JpaSpecificationExecutor<LoginAudit> {
    Page<LoginAudit> findByUserId(Long userId, Pageable pageable);

    Page<LoginAudit> findByActionResult(ActionResult result, Pageable pageable);

    Page<LoginAudit> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
