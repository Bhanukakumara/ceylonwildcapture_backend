package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.AddItemToOrderRequestDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderItemResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.OrderResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for OrderItem management operations.
 * Defines business logic for order item creation, pricing calculations,
 * and sales analytics.
 */
public interface OrderItemService {

    /**
     * Create a new order item.
     *
     * @param orderItem the order item entity to create
     * @return the created order item
     * @throws IllegalArgumentException if order item data is invalid
     */
    OrderItem createOrderItem(OrderItem orderItem);

    /**
     * Create order item for photo purchase.
     *
     * @param orderId the order ID
     * @param photoId the photo ID
     * @param licenseType the license type
     * @return the created order item
     * @throws IllegalArgumentException if order or photo not found
     */
    OrderItem createOrderItem(Long orderId, Long photoId, LicenseType licenseType);

    /**
     * Update order item.
     *
     * @param orderItemId the order item ID
     * @param orderItem the updated order item data
     * @return the updated order item
     * @throws IllegalArgumentException if order item not found
     */
    OrderItem updateOrderItem(Long orderItemId, OrderItem orderItem);

    /**
     * Get order item by ID.
     *
     * @param orderItemId the order item ID
     * @return Optional containing the order item if found
     */
    Optional<OrderItem> getOrderItemById(Long orderItemId);

    /**
     * Get all order items by order.
     *
     * @param orderId the order ID
     * @return list of order items
     */
    List<OrderItem> getOrderItemsByOrder(Long orderId);

    /**
     * Get order items by order with pagination.
     *
     * @param orderId the order ID
     * @param pageable pagination information
     * @return page of order items
     */
    Page<OrderItem> getOrderItemsByOrder(Long orderId, Pageable pageable);

    /**
     * Get order items by photo.
     *
     * @param photoId the photo ID
     * @param pageable pagination information
     * @return page of order items
     */
    Page<OrderItem> getOrderItemsByPhoto(Long photoId, Pageable pageable);

    /**
     * Get order items by license type.
     *
     * @param licenseType the license type
     * @param pageable pagination information
     * @return page of order items
     */
    Page<OrderItem> getOrderItemsByLicenseType(LicenseType licenseType, Pageable pageable);

    /**
     * Get order items by photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of order items
     */
    Page<OrderItem> getOrderItemsByPhotographer(Long photographerId, Pageable pageable);

    /**
     * Delete order item.
     *
     * @param orderItemId the order item ID
     * @throws IllegalArgumentException if order item not found or cannot be deleted
     */
    void deleteOrderItem(Long orderItemId);

    /**
     * Calculate item price with license type.
     *
     * @param photoId the photo ID
     * @param licenseType the license type
     * @return the calculated price
     * @throws IllegalArgumentException if photo not found
     */
    BigDecimal calculateItemPrice(Long photoId, LicenseType licenseType);

    /**
     * Calculate photographer earnings for order item.
     *
     * @param finalPrice the final price after discounts
     * @param commissionRate the photographer's commission rate
     * @return the photographer earnings
     */
    BigDecimal calculatePhotographerEarnings(BigDecimal finalPrice, BigDecimal commissionRate);

    /**
     * Calculate platform commission for order item.
     *
     * @param finalPrice the final price after discounts
     * @param commissionRate the photographer's commission rate
     * @return the platform commission
     */
    BigDecimal calculatePlatformCommission(BigDecimal finalPrice, BigDecimal commissionRate);

    /**
     * Apply discount to order item.
     *
     * @param orderItemId the order item ID
     * @param discountAmount the discount amount
     * @return the updated order item
     * @throws IllegalArgumentException if order item not found
     */
    OrderItem applyDiscount(Long orderItemId, BigDecimal discountAmount);

    /**
     * Count order items by order.
     *
     * @param orderId the order ID
     * @return count of order items
     */
    long countOrderItemsByOrder(Long orderId);

    /**
     * Count sales for photo.
     *
     * @param photoId the photo ID
     * @return count of times photo has been purchased
     */
    long countSalesByPhoto(Long photoId);

    /**
     * Count order items by license type.
     *
     * @param licenseType the license type
     * @return count of order items
     */
    long countOrderItemsByLicenseType(LicenseType licenseType);

    /**
     * Calculate total sales for photo.
     *
     * @param photoId the photo ID
     * @return total sales amount
     */
    BigDecimal calculateTotalSalesByPhoto(Long photoId);

    /**
     * Calculate photographer earnings by photo.
     *
     * @param photoId the photo ID
     * @return total photographer earnings
     */
    BigDecimal calculatePhotographerEarningsByPhoto(Long photoId);

    /**
     * Calculate total earnings by photographer.
     *
     * @param photographerId the photographer ID
     * @return total photographer earnings
     */
    BigDecimal calculateTotalEarningsByPhotographer(Long photographerId);

    /**
     * Calculate total commission by photographer.
     *
     * @param photographerId the photographer ID
     * @return total platform commission
     */
    BigDecimal calculateTotalCommissionByPhotographer(Long photographerId);

    /**
     * Get best-selling photos.
     *
     * @param pageable pagination information
     * @return page of photo IDs with sales counts
     */
    Page<Object[]> getBestSellingPhotos(Pageable pageable);

    /**
     * Get most popular license types.
     *
     * @param pageable pagination information
     * @return page of license types with counts
     */
    Page<Object[]> getMostPopularLicenseTypes(Pageable pageable);

    /**
     * Check if buyer has purchased photo.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @return true if buyer has purchased the photo
     */
    boolean hasBuyerPurchasedPhoto(Long buyerId, Long photoId);

    /**
     * Get completed order items by photographer within date range.
     *
     * @param photographerId the photographer ID
     * @param startDate the start date
     * @param endDate the end date
     * @return list of order items
     */
    List<OrderItem> getCompletedItemsByPhotographerAndDateRange(
            Long photographerId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    /**
     * Calculate average price by license type.
     *
     * @param licenseType the license type
     * @return average price
     */
    BigDecimal calculateAveragePriceByLicenseType(LicenseType licenseType);

    /**
     * Get order items with discounts.
     *
     * @param pageable pagination information
     * @return page of order items with discounts applied
     */
    Page<OrderItem> getOrderItemsWithDiscount(Pageable pageable);

    /**
     * Count total sales for photographer.
     *
     * @param photographerId the photographer ID
     * @return count of sales
     */
    long countSalesByPhotographer(Long photographerId);

    OrderResponseDto addToOrder(@Valid AddItemToOrderRequestDto requestDto, Long userId);

    OrderResponseDto removeFromOrder(Long orderItemId, Long userId);

    OrderItemResponseDto getOrderItemDto(Long orderItemId, Long userId);

    List<OrderItemResponseDto> getItemsByOrder(Long orderId, Long userId);

    List<OrderItemResponseDto> getItemsByPhoto(Long photoId);

    int countItemsByOrder(Long orderId, Long userId);
}
