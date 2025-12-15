package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for photo moderation actions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoModerationDto {

    @NotNull(message = "Photo ID is required")
    private Long photoId;

    @NotNull(message = "Action is required")
    private ModerationAction action;

    private String rejectionReason;

    private String moderatorNotes;

    private Boolean applyWatermark;

    private Boolean featured;

    private Boolean visible;

    public enum ModerationAction {
        APPROVE,
        REJECT,
        FLAG,
        SOFT_DELETE,
        RESTORE,
        FEATURE,
        UNFEATURE,
        APPLY_WATERMARK,
        REMOVE_WATERMARK
    }
}
