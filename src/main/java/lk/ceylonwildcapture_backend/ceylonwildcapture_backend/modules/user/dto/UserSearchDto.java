package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.UserRole;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for User Search operations.
 * Contains search criteria for filtering users.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDto {

    @Size(max = 100, message = "Search term must not exceed 100 characters")
    private String searchTerm;

    private UserRole role;

    private Boolean isActive;

    private Boolean emailVerified;

    private LocalDateTime createdAfter;

    private LocalDateTime createdBefore;

    private LocalDateTime lastLoginAfter;

    private Integer page;

    private Integer size;

    private String sortBy;

    private String sortDirection;
}
