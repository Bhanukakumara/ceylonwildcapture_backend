package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Top Photographer DTO
 * Contains photographer information with photo count
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopPhotographerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImageUrl;
    private Long photoCount;
}
