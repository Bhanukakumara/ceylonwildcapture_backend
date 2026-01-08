package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.OrderItem;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for OrderItem entity.
 * Provides database operations for order items, photo sales tracking,
 * and license type analytics.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * Find all order items by order.
     *
     * @param order the order entity
     * @return list of order items
     */
    List<OrderItem> findByOrder(Order order);

    /**
     * Find all order items by order ID.
     *
     * @param orderId the order ID
     * @return list of order items
     */
    List<OrderItem> findByOrderId(Long orderId);

    /**
     * Find order items by order with pagination.
     *
     * @param orderId the order ID
     * @param pageable pagination information
     * @return page of order items
     */
    Page<OrderItem> findByOrderId(Long orderId, Pageable pageable);

    /**
     * Find all order items by photo.
     *
     * @param photo the photo entity
     * @param pageable pagination information
     * @return page of order items
     */
    Page<OrderItem> findByPhoto(Photo photo, Pageable pageable);

    /**
     * Find all order items by photo ID.
     *
     * @param photoId the photo ID
     * @param pageable pagination information
     * @return page of order items
     */
    Page<OrderItem> findByPhotoId(Long photoId, Pageable pageable);





    /**
     * Find order item by order and photo.
     *
     * @param orderId the order ID
     * @param photoId the photo ID
     * @return Optional containing the order item if found
     */
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId AND oi.photo.id = :photoId")
    Optional<OrderItem> findByOrderIdAndPhotoId(@Param("orderId") Long orderId, @Param("photoId") Long photoId);

    /**
     * Count order items by order.
     *
     * @param orderId the order ID
     * @return count of order items
     */
    long countByOrderId(Long orderId);

    /**
     * Count order items by photo.
     *
     * @param photoId the photo ID
     * @return count of times photo has been purchased
     */
    long countByPhotoId(Long photoId);



    /**
     * Calculate total sales for a photo.
     *
     * @param photoId the photo ID
     * @return total sales amount
     */
    @Query("SELECT SUM(oi.finalPrice) FROM OrderItem oi WHERE oi.photo.id = :photoId")
    BigDecimal calculateTotalSalesByPhoto(@Param("photoId") Long photoId);

    /**
     * Calculate photographer earnings for a photo.
     *
     * @param photoId the photo ID
     * @return total photographer earnings
     */
    @Query("SELECT SUM(oi.photographerEarnings) FROM OrderItem oi WHERE oi.photo.id = :photoId")
    BigDecimal calculatePhotographerEarningsByPhoto(@Param("photoId") Long photoId);

    /**
     * Calculate photographer earnings by photographer ID.
     *
     * @param photographerId the photographer ID
     * @return total photographer earnings
     */
    @Query("SELECT SUM(oi.photographerEarnings) FROM OrderItem oi WHERE oi.photo.photographer.id = :photographerId")
    BigDecimal calculateTotalEarningsByPhotographer(@Param("photographerId") Long photographerId);

    /**
     * Calculate platform commission by photographer ID.
     *
     * @param photographerId the photographer ID
     * @return total platform commission
     */
    @Query("SELECT SUM(oi.platformCommission) FROM OrderItem oi WHERE oi.photo.photographer.id = :photographerId")
    BigDecimal calculateTotalCommissionByPhotographer(@Param("photographerId") Long photographerId);

    /**
     * Find order items by photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of order items
     */
    @Query("SELECT oi FROM OrderItem oi WHERE oi.photo.photographer.id = :photographerId")
    Page<OrderItem> findByPhotographerId(@Param("photographerId") Long photographerId, Pageable pageable);

    /**
     * Find best-selling photos.
     *
     * @param pageable pagination information
     * @return page of photo IDs with sales counts
     */
    @Query("SELECT oi.photo.id, COUNT(oi) as salesCount FROM OrderItem oi " +
           "GROUP BY oi.photo.id ORDER BY salesCount DESC")
    Page<Object[]> findBestSellingPhotos(Pageable pageable);



    /**
     * Check if buyer has purchased photo.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @return true if buyer has purchased the photo
     */
    @Query("SELECT COUNT(oi) > 0 FROM OrderItem oi WHERE oi.order.buyer.id = :buyerId " +
           "AND oi.photo.id = :photoId AND oi.order.status = 'COMPLETED'")
    boolean existsByBuyerIdAndPhotoId(@Param("buyerId") Long buyerId, @Param("photoId") Long photoId);

    /**
     * Find order items for completed orders by photographer within date range.
     *
     * @param photographerId the photographer ID
     * @param startDate the start date
     * @param endDate the end date
     * @return list of order items
     */
    @Query("SELECT oi FROM OrderItem oi WHERE oi.photo.photographer.id = :photographerId " +
           "AND oi.order.status = 'COMPLETED' AND oi.createdAt BETWEEN :startDate AND :endDate")
    List<OrderItem> findCompletedItemsByPhotographerAndDateRange(
            @Param("photographerId") Long photographerId,
            @Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate
    );



    /**
     * Find order items with discount applied.
     *
     * @param pageable pagination information
     * @return page of order items with discounts
     */
    @Query("SELECT oi FROM OrderItem oi WHERE oi.discount > 0 ORDER BY oi.discount DESC")
    Page<OrderItem> findItemsWithDiscount(Pageable pageable);

    /**
     * Count total sales for photographer.
     *
     * @param photographerId the photographer ID
     * @return count of sales
     */
    @Query("SELECT COUNT(oi) FROM OrderItem oi WHERE oi.photo.photographer.id = :photographerId " +
           "AND oi.order.status = 'COMPLETED'")
    long countSalesByPhotographer(@Param("photographerId") Long photographerId);
}
