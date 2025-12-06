package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.filter;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.AuditType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Advanced search criteria for audit queries.
 * Supports filtering by multiple dimensions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditSearchCriteria {

    /**
     * Filter by audit type
     */
    private AuditType auditType;

    /**
     * Filter by user ID (actor)
     */
    private Long userId;

    /**
     * Filter by admin ID (for admin actions)
     */
    private Long adminId;

    /**
     * Filter by photo ID
     */
    private Long photoId;

    /**
     * Filter by order ID
     */
    private Long orderId;

    /**
     * Filter by license ID
     */
    private Long licenseId;

    /**
     * Filter by entity type
     */
    private EntityType entityType;

    /**
     * Filter by entity ID
     */
    private Long entityId;

    /**
     * Filter by action type
     */
    private String actionType;

    /**
     * Filter by action result (success/failure)
     */
    private ActionResult successFlag;

    /**
     * Filter by date range
     */
    private DateRange dateRange;

    /**
     * Filter by IP address
     */
    private String ipAddress;

    /**
     * Filter by country
     */
    private String country;

    /**
     * Check if criteria has any filters applied.
     *
     * @return true if at least one filter is set
     */
    public boolean hasFilters() {
        return auditType != null ||
               userId != null ||
               adminId != null ||
               photoId != null ||
               orderId != null ||
               licenseId != null ||
               entityType != null ||
               entityId != null ||
               actionType != null ||
               successFlag != null ||
               dateRange != null ||
               ipAddress != null ||
               country != null;
    }
}
