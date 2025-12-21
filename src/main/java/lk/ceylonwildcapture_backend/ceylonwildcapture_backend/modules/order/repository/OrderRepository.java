package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.OrderStatus;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Order entity.
 * Provides database operations for order management including buyer orders,
 * order status tracking, and order analytics.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

       /**
        * Find order by order number.
        *
        * @param orderNumber the unique order number
        * @return Optional containing the order if found
        */
       Optional<Order> findByOrderNumber(String orderNumber);

       /**
        * Find order by ID with buyer loaded.
        *
        * @param id the order ID
        * @return Optional containing the order if found
        */
       @Query("SELECT o FROM Order o LEFT JOIN FETCH o.buyer WHERE o.id = :id")
       Optional<Order> findByIdWithBuyer(@Param("id") Long id);

       /**
        * Find order by ID with order items loaded.
        *
        * @param id the order ID
        * @return Optional containing the order if found
        */
       @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.id = :id")
       Optional<Order> findByIdWithOrderItems(@Param("id") Long id);

       /**
        * Find all orders by buyer.
        *
        * @param buyer    the buyer (User entity)
        * @param pageable pagination information
        * @return page of orders
        */
       Page<Order> findByBuyer(User buyer, Pageable pageable);

       /**
        * Find all orders by buyer ID.
        *
        * @param buyerId  the buyer ID
        * @param pageable pagination information
        * @return page of orders
        */
       Page<Order> findByBuyerId(Long buyerId, Pageable pageable);

       /**
        * Find orders by buyer and status.
        *
        * @param buyerId  the buyer ID
        * @param status   the order status
        * @param pageable pagination information
        * @return page of orders
        */
       Page<Order> findByBuyerIdAndStatus(Long buyerId, OrderStatus status, Pageable pageable);

       /**
        * Find orders by status.
        *
        * @param status   the order status
        * @param pageable pagination information
        * @return page of orders
        */
       Page<Order> findByStatus(OrderStatus status, Pageable pageable);

       /**
        * Find orders by payment method.
        *
        * @param paymentMethod the payment method
        * @param pageable      pagination information
        * @return page of orders
        */
       Page<Order> findByPaymentMethod(String paymentMethod, Pageable pageable);

       /**
        * Find orders by transaction ID.
        *
        * @param transactionId the transaction ID
        * @return Optional containing the order if found
        */
       Optional<Order> findByTransactionId(String transactionId);

       /**
        * Find orders by payment ID.
        *
        * @param paymentId the payment ID
        * @return Optional containing the order if found
        */
       Optional<Order> findByPaymentId(String paymentId);

       /**
        * Find orders created within date range.
        *
        * @param startDate the start date
        * @param endDate   the end date
        * @param pageable  pagination information
        * @return page of orders
        */
       Page<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

       /**
        * Find completed orders by buyer.
        *
        * @param buyerId  the buyer ID
        * @param status   the order status (COMPLETED)
        * @param pageable pagination information
        * @return page of completed orders
        */
       @Query("SELECT o FROM Order o WHERE o.buyer.id = :buyerId AND o.status = :status ORDER BY o.completedAt DESC")
       Page<Order> findCompletedOrdersByBuyer(@Param("buyerId") Long buyerId,
                     @Param("status") OrderStatus status,
                     Pageable pageable);

       /**
        * Find pending orders by buyer.
        *
        * @param buyerId the buyer ID
        * @param status  the order status (PENDING)
        * @return list of pending orders
        */
       List<Order> findByBuyerIdAndStatus(Long buyerId, OrderStatus status);

       /**
        * Find orders with total amount greater than threshold.
        *
        * @param minAmount the minimum total amount
        * @param pageable  pagination information
        * @return page of orders
        */
       @Query("SELECT o FROM Order o WHERE o.totalAmount >= :minAmount ORDER BY o.totalAmount DESC")
       Page<Order> findByTotalAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount, Pageable pageable);

       /**
        * Find orders by billing email.
        *
        * @param billingEmail the billing email
        * @param pageable     pagination information
        * @return page of orders
        */
       Page<Order> findByBillingEmailContainingIgnoreCase(String billingEmail, Pageable pageable);

       /**
        * Find refunded orders.
        *
        * @param status   the order status (REFUNDED)
        * @param pageable pagination information
        * @return page of refunded orders
        */
       @Query("SELECT o FROM Order o WHERE o.status = :status ORDER BY o.refundedAt DESC")
       Page<Order> findRefundedOrders(@Param("status") OrderStatus status, Pageable pageable);

       /**
        * Find failed orders.
        *
        * @param status   the order status (FAILED)
        * @param pageable pagination information
        * @return page of failed orders
        */
       Page<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status, Pageable pageable);

       /**
        * Count orders by buyer.
        *
        * @param buyerId the buyer ID
        * @return count of orders
        */
       long countByBuyerId(Long buyerId);

       /**
        * Count orders by status.
        *
        * @param status the order status
        * @return count of orders
        */
       long countByStatus(OrderStatus status);

       /**
        * Count completed orders by buyer.
        *
        * @param buyerId the buyer ID
        * @param status  the order status (COMPLETED)
        * @return count of completed orders
        */
       long countByBuyerIdAndStatus(Long buyerId, OrderStatus status);

       /**
        * Calculate total sales amount by status.
        *
        * @param status the order status (COMPLETED)
        * @return total sales amount
        */
       @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = :status")
       BigDecimal calculateTotalSales(@Param("status") OrderStatus status);

       /**
        * Calculate total sales amount by buyer.
        *
        * @param buyerId the buyer ID
        * @param status  the order status (COMPLETED)
        * @return total amount spent by buyer
        */
       @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.buyer.id = :buyerId AND o.status = :status")
       BigDecimal calculateTotalSpentByBuyer(@Param("buyerId") Long buyerId, @Param("status") OrderStatus status);

       /**
        * Find recent orders.
        *
        * @param pageable pagination information
        * @return page of recent orders
        */
       Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);

       /**
        * Find orders by buyer within date range.
        *
        * @param buyerId   the buyer ID
        * @param startDate the start date
        * @param endDate   the end date
        * @param pageable  pagination information
        * @return page of orders
        */
       @Query("SELECT o FROM Order o WHERE o.buyer.id = :buyerId AND o.createdAt BETWEEN :startDate AND :endDate")
       Page<Order> findOrdersByBuyerAndDateRange(@Param("buyerId") Long buyerId,
                     @Param("startDate") LocalDateTime startDate,
                     @Param("endDate") LocalDateTime endDate,
                     Pageable pageable);

       /**
        * Find orders by coupon code.
        *
        * @param couponCode the coupon code
        * @param pageable   pagination information
        * @return page of orders that used the coupon
        */
       Page<Order> findByCouponCode(String couponCode, Pageable pageable);

       /**
        * Check if buyer has purchased from photographer.
        *
        * @param buyerId        the buyer ID
        * @param photographerId the photographer ID
        * @param status         the order status (COMPLETED)
        * @return true if buyer has purchased from photographer
        */
       @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.orderItems oi " +
                     "WHERE o.buyer.id = :buyerId AND oi.photo.photographer.id = :photographerId AND o.status = :status")
       boolean existsByBuyerIdAndPhotographerId(@Param("buyerId") Long buyerId,
                     @Param("photographerId") Long photographerId,
                     @Param("status") OrderStatus status);

       /**
        * Find top buyers by order count.
        *
        * @param status   the order status (COMPLETED)
        * @param pageable pagination information
        * @return page of buyer IDs with order counts
        */
       @Query("SELECT o.buyer.id, COUNT(o) as orderCount FROM Order o " +
                     "WHERE o.status = :status GROUP BY o.buyer.id ORDER BY orderCount DESC")
       Page<Object[]> findTopBuyersByOrderCount(@Param("status") OrderStatus status, Pageable pageable);

       /**
        * Find top buyers by total spending.
        *
        * @param status   the order status (COMPLETED)
        * @param pageable pagination information
        * @return page of buyer IDs with total spending
        */
       @Query("SELECT o.buyer.id, SUM(o.totalAmount) as totalSpent FROM Order o " +
                     "WHERE o.status = :status GROUP BY o.buyer.id ORDER BY totalSpent DESC")
       Page<Object[]> findTopBuyersBySpending(@Param("status") OrderStatus status, Pageable pageable);

       /**
        * Check if order number exists.
        *
        * @param orderNumber the order number
        * @return true if order number exists
        */
       boolean existsByOrderNumber(String orderNumber);

       /**
        * Find orders containing specific photo.
        *
        * @param photoId  the photo ID
        * @param pageable pagination information
        * @return page of orders containing the photo
        */
       @Query("SELECT DISTINCT o FROM Order o JOIN o.orderItems oi WHERE oi.photo.id = :photoId")
       Page<Order> findOrdersByPhotoId(@Param("photoId") Long photoId, Pageable pageable);

       /**
        * Check if buyer has purchased a specific photo.
        *
        * @param buyerId the buyer ID
        * @param photoId the photo ID
        * @param status  the order status (COMPLETED)
        * @return true if buyer has purchased this photo
        */
       @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.orderItems oi " +
                     "WHERE o.buyer.id = :buyerId AND oi.photo.id = :photoId AND o.status = :status")
       boolean hasBuyerPurchasedPhoto(@Param("buyerId") Long buyerId,
                     @Param("photoId") Long photoId,
                     @Param("status") OrderStatus status);
}
