package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for adding an item to an order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {

    @NotNull(message = "Photo ID is required")
    private Long photoId;

}
