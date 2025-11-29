package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Override
    public Object addToCart(Long userId, Long photoId, LicenseType licenseType) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeFromCart(Long userId, Long cartItemId) {
        // TODO: Implement actual business logic
    }

    @Override
    public Object updateCartItemLicenseType(Long userId, Long cartItemId, LicenseType licenseType) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Object getCart(Long userId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Object> getCartItems(Long userId) {
        // TODO: Implement actual business logic
        return Collections.emptyList();
    }

    @Override
    public int getCartItemCount(Long userId) {
        // TODO: Implement actual business logic
        return 0;
    }

    @Override
    public BigDecimal calculateCartTotal(Long userId) {
        // TODO: Implement actual business logic
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateCartTotalWithCoupon(Long userId, String couponCode) {
        // TODO: Implement actual business logic
        return BigDecimal.ZERO;
    }

    @Override
    public void clearCart(Long userId) {
        // TODO: Implement actual business logic
    }

    @Override
    public boolean isPhotoInCart(Long userId, Long photoId) {
        // TODO: Implement actual business logic
        return false;
    }

    @Override
    public Object validateCart(Long userId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Long checkoutCart(Long userId) {
        // TODO: Implement actual business logic
        return null;
    }

    @Override
    public Object applyCouponToCart(Long userId, String couponCode) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Object removeCouponFromCart(Long userId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Object getCartSummary(Long userId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Object mergeGuestCart(Long userId, List<Object> guestCartItems) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isCartEmpty(Long userId) {
        // TODO: Implement actual business logic
        return false;
    }

    @Override
    public Object getCartExpiration(Long userId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Object refreshCartPrices(Long userId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
