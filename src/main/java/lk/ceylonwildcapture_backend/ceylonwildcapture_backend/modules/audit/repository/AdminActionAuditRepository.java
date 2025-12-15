package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.AdminActionAudit;
// import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.AdminActionAudit.EntityType; // Assuming not needed if using String or Specification
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AdminActionAuditRepository
        extends JpaRepository<AdminActionAudit, Long>, JpaSpecificationExecutor<AdminActionAudit> {
    Page<AdminActionAudit> findByAdminId(Long adminId, Pageable pageable);

    Page<AdminActionAudit> findByEntityId(Long entityId, Pageable pageable);

    Page<AdminActionAudit> findByEntityIdAndEntityType(Long entityId,
            lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.EntityType entityType,
            Pageable pageable);

    // Page<AdminActionAudit> findByEntityType(EntityType entityType, Pageable
    // pageable); // Commented out to avoid compilation error if EntityType is
    // ambiguous
    Page<AdminActionAudit> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
