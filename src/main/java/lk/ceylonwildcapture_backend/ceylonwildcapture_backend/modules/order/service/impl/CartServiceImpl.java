package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.CartItemResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.CartResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.CartItem;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository.CartItemRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.CartService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.entity.Photo;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.photo.repository.PhotoRepository;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    private static final BigDecimal TAX_RATE = BigDecimal.valueOf(0.10); // 10% tax

    @Override
    @Transactional
    public Object addToCart(Long userId, Long photoId) {
        // Validate user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Validate photo
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("Photo not found with ID: " + photoId));

        // Check if item already exists
        if (cartItemRepository.existsByUserIdAndPhotoId(userId, photoId)) {
            throw new IllegalArgumentException("This photo is already in your cart");
        }

        // Create cart item
        CartItem cartItem = CartItem.builder()
                .user(user)
                .photo(photo)
                .build();

        cartItem = cartItemRepository.save(cartItem);

        return mapToCartItemResponse(cartItem);
    }

    @Override
    @Transactional
    public void removeFromCart(Long userId, Long cartItemId) {
        cartItemRepository.deleteByIdAndUserId(cartItemId, userId);
    }


    @Override
    @Transactional(readOnly = true)
    public Object getCart(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        List<CartItemResponseDto> items = cartItems.stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList());

        BigDecimal subtotal = calculateCartTotal(userId);
        BigDecimal tax = subtotal.multiply(TAX_RATE);
        BigDecimal total = subtotal.add(tax);

        return CartResponseDto.builder()
                .items(items)
                .itemCount(items.size())
                .subtotal(subtotal)
                .tax(tax)
                .total(total)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object> getCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId).stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public int getCartItemCount(Long userId) {
        return (int) cartItemRepository.countByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateCartTotal(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateCartTotalWithCoupon(Long userId, String couponCode) {
        // TODO: Implement coupon logic when coupon system is ready
        return calculateCartTotal(userId);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isPhotoInCart(Long userId, Long photoId) {
        return cartItemRepository.existsByUserIdAndPhotoId(userId, photoId);
    }

    @Override
    public Object validateCart(Long userId) {
        // TODO: Implement validation logic (check photo availability, prices, etc.)
        return true;
    }

    @Override
    @Transactional
    public Long checkoutCart(Long userId) {
        // TODO: Implement checkout logic to create order from cart
        throw new UnsupportedOperationException("Checkout not implemented yet");
    }

    @Override
    public Object applyCouponToCart(Long userId, String couponCode) {
        // TODO: Implement coupon application
        throw new UnsupportedOperationException("Coupon system not implemented yet");
    }

    @Override
    public Object removeCouponFromCart(Long userId) {
        // TODO: Implement coupon removal
        throw new UnsupportedOperationException("Coupon system not implemented yet");
    }

    @Override
    @Transactional(readOnly = true)
    public Object getCartSummary(Long userId) {
        return getCart(userId);
    }

    @Override
    @Transactional
    public Object mergeGuestCart(Long userId, List<Object> guestCartItems) {
        // TODO: Implement guest cart merging
        throw new UnsupportedOperationException("Guest cart merging not implemented yet");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCartEmpty(Long userId) {
        return cartItemRepository.countByUserId(userId) == 0;
    }

    @Override
    public Object getCartExpiration(Long userId) {
        // TODO: Implement cart expiration logic
        return null;
    }

    @Override
    @Transactional
    public Object refreshCartPrices(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        cartItems.forEach(cartItem -> {
            cartItem.calculatePrice();
            cartItemRepository.save(cartItem);
        });
        return getCart(userId);
    }

    // Helper method to map CartItem to CartItemResponseDto
    private CartItemResponseDto mapToCartItemResponse(CartItem cartItem) {
        Photo photo = cartItem.getPhoto();
        User photographer = photo.getPhotographer();

        return CartItemResponseDto.builder()
                .id(cartItem.getId())
                .photoId(photo.getId())
                .photoTitle(photo.getTitle())
                .photoImageUrl(photo.getImageUrl())
                .photoThumbnailUrl(photo.getThumbnailUrl())
                .photographerName(photographer != null ? photographer.getUsername() : "Unknown")
                .photographerId(photographer != null ? photographer.getId() : null)
                .price(cartItem.getPrice())
                .addedAt(cartItem.getCreatedAt())
                .build();
    }
}
