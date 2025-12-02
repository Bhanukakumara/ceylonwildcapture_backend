package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.dto.tag;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for merging multiple tags into one.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagMergeDto {

    @NotEmpty(message = "Source tag IDs are required")
    private List<Long> sourceTagIds;

    @NotNull(message = "Target tag ID is required")
    private Long targetTagId;
}
