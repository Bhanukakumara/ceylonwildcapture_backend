package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for shopping cart operations.
 * Defines business logic for cart management, item addition/removal,
 * and checkout preparation.
 */
public interface CartService {

    /**
     * Add photo to cart.
     *
     * @param userId the user ID
     * @param photoId the photo ID
     * @return the cart item (DTO placeholder)
     * @throws IllegalArgumentException if user or photo not found
     */
    Object addToCart(Long userId, Long photoId);

    /**
     * Remove item from cart.
     *
     * @param userId the user ID
     * @param cartItemId the cart item ID
     * @throws IllegalArgumentException if cart item not found
     */
    void removeFromCart(Long userId, Long cartItemId);


    /**
     * Get user's cart.
     *
     * @param userId the user ID
     * @return the cart with items (DTO placeholder)
     */
    Object getCart(Long userId);

    /**
     * Get cart items.
     *
     * @param userId the user ID
     * @return list of cart items (DTO placeholder)
     */
    List<Object> getCartItems(Long userId);

    /**
     * Get cart item count.
     *
     * @param userId the user ID
     * @return count of items in cart
     */
    int getCartItemCount(Long userId);

    /**
     * Calculate cart total.
     *
     * @param userId the user ID
     * @return the cart total amount
     */
    BigDecimal calculateCartTotal(Long userId);

    /**
     * Calculate cart total with coupon.
     *
     * @param userId the user ID
     * @param couponCode the coupon code
     * @return the cart total with discount applied
     */
    BigDecimal calculateCartTotalWithCoupon(Long userId, String couponCode);

    /**
     * Clear cart.
     *
     * @param userId the user ID
     * @throws IllegalArgumentException if user not found
     */
    void clearCart(Long userId);

    /**
     * Check if photo is in cart.
     *
     * @param userId the user ID
     * @param photoId the photo ID
     * @return true if photo is in cart
     */
    boolean isPhotoInCart(Long userId, Long photoId);

    /**
     * Validate cart before checkout.
     *
     * @param userId the user ID
     * @return validation result with any errors (DTO placeholder)
     */
    Object validateCart(Long userId);

    /**
     * Move cart to order.
     *
     * @param userId the user ID
     * @return the created order ID
     * @throws IllegalArgumentException if cart is empty or validation fails
     */
    Long checkoutCart(Long userId);

    /**
     * Apply coupon to cart.
     *
     * @param userId the user ID
     * @param couponCode the coupon code
     * @return the cart with coupon applied (DTO placeholder)
     * @throws IllegalArgumentException if coupon is invalid
     */
    Object applyCouponToCart(Long userId, String couponCode);

    /**
     * Remove coupon from cart.
     *
     * @param userId the user ID
     * @return the cart without coupon (DTO placeholder)
     */
    Object removeCouponFromCart(Long userId);

    /**
     * Get cart summary.
     *
     * @param userId the user ID
     * @return cart summary with totals and item count (DTO placeholder)
     */
    Object getCartSummary(Long userId);

    /**
     * Merge guest cart with user cart.
     *
     * @param userId the user ID
     * @param guestCartItems the guest cart items (DTO placeholder)
     * @return the merged cart (DTO placeholder)
     */
    Object mergeGuestCart(Long userId, List<Object> guestCartItems);

    /**
     * Check if cart is empty.
     *
     * @param userId the user ID
     * @return true if cart is empty
     */
    boolean isCartEmpty(Long userId);

    /**
     * Get cart expiration time.
     *
     * @param userId the user ID
     * @return the cart expiration timestamp
     */
    Object getCartExpiration(Long userId);

    /**
     * Refresh cart prices.
     *
     * @param userId the user ID
     * @return the cart with updated prices (DTO placeholder)
     */
    Object refreshCartPrices(Long userId);
}
