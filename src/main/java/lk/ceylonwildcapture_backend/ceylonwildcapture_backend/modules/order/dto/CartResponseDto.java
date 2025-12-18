package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {

    private List<CartItemResponseDto> items;
    private int itemCount;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
}
