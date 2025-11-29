package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.controller;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.AddItemToOrderRequestDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderItemResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for order item management operations.
 */
@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    /**
     * Add item to order.
     *
     * @param requestDto add item request
     * @param userId authenticated user ID
     * @return updated order
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER')")
    public ResponseEntity<OrderResponseDto> addItemToOrder(
            @Valid @RequestBody AddItemToOrderRequestDto requestDto,
            @RequestAttribute("userId") Long userId) {
        OrderResponseDto response = orderItemService.addToOrder(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Remove item from order.
     *
     * @param orderItemId order item ID
     * @param userId authenticated user ID
     * @return updated order
     */
    @DeleteMapping("/{orderItemId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER')")
    public ResponseEntity<OrderResponseDto> removeItemFromOrder(
            @PathVariable Long orderItemId,
            @RequestAttribute("userId") Long userId) {
        OrderResponseDto response = orderItemService.removeFromOrder(orderItemId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get order item by ID.
     *
     * @param orderItemId order item ID
     * @param userId authenticated user ID
     * @return order item response
     */
    @GetMapping("/{orderItemId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<OrderItemResponseDto> getOrderItem(
            @PathVariable Long orderItemId,
            @RequestAttribute("userId") Long userId) {
        OrderItemResponseDto response = orderItemService.getOrderItemDto(orderItemId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all items for an order.
     *
     * @param orderId order ID
     * @param userId authenticated user ID
     * @return list of order items
     */
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<List<OrderItemResponseDto>> getOrderItems(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long userId) {
        List<OrderItemResponseDto> items = orderItemService.getItemsByOrder(orderId, userId);
        return ResponseEntity.ok(items);
    }

    /**
     * Get items by photo.
     *
     * @param photoId photo ID
     * @return list of order items
     */
    @GetMapping("/photo/{photoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderItemResponseDto>> getItemsByPhoto(@PathVariable Long photoId) {
        List<OrderItemResponseDto> items = orderItemService.getItemsByPhoto(photoId);
        return ResponseEntity.ok(items);
    }

    /**
     * Get order item count for an order.
     *
     * @param orderId order ID
     * @param userId authenticated user ID
     * @return item count
     */
    @GetMapping("/order/{orderId}/count")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<Integer> getOrderItemCount(
            @PathVariable Long orderId,
            @RequestAttribute("userId") Long userId) {
        int count = orderItemService.countItemsByOrder(orderId, userId);
        return ResponseEntity.ok(count);
    }
}
