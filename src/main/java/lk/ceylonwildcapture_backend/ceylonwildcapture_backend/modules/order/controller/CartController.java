package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.controller;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.AddToCartRequestDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.CartService;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    /**
     * Get user's cart
     */
    @GetMapping
    public ResponseEntity<?> getCart(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    /**
     * Add item to cart
     */
    @PostMapping("/items")
    public ResponseEntity<?> addToCart(
            @Valid @RequestBody AddToCartRequestDto request,
            Authentication authentication) {
        try {
            Long userId = getUserIdFromAuth(authentication);
            Object cartItem = cartService.addToCart(userId, request.getPhotoId(), request.getLicenseType());
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Remove item from cart
     */
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long cartItemId,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        cartService.removeFromCart(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update cart item license type
     */
    @PutMapping("/items/{cartItemId}/license")
    public ResponseEntity<?> updateLicenseType(
            @PathVariable Long cartItemId,
            @RequestParam LicenseType licenseType,
            Authentication authentication) {
        try {
            Long userId = getUserIdFromAuth(authentication);
            Object updatedItem = cartService.updateCartItemLicenseType(userId, cartItemId, licenseType);
            return ResponseEntity.ok(updatedItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get cart item count
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> getCartItemCount(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        int count = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * Clear cart
     */
    @DeleteMapping
    public ResponseEntity<?> clearCart(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if photo is in cart
     */
    @GetMapping("/check/{photoId}")
    public ResponseEntity<Boolean> isPhotoInCart(
            @PathVariable Long photoId,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        boolean inCart = cartService.isPhotoInCart(userId, photoId);
        return ResponseEntity.ok(inCart);
    }

    /**
     * Get cart summary
     */
    @GetMapping("/summary")
    public ResponseEntity<?> getCartSummary(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(cartService.getCartSummary(userId));
    }

    /**
     * Refresh cart prices
     */
    @PostMapping("/refresh-prices")
    public ResponseEntity<?> refreshCartPrices(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(cartService.refreshCartPrices(userId));
    }

    // Helper method to extract user ID from authentication
    private Long getUserIdFromAuth(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated");
        }

        // Extract username from authentication (JWT stores username in subject)
        String username = authentication.getName();

        // Look up user by username to get the user ID
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username))
                .getId();
    }
}
