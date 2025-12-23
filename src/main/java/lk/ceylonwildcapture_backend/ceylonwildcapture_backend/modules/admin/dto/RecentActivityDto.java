package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for recent activity data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentActivityDto {
    private String type; // "photo", "order", "user", "payout"
    private String action;
    private String userName;
    private Long userId;
    private LocalDateTime timestamp;
    private String timeAgo; // e.g., "2 minutes ago"
}
