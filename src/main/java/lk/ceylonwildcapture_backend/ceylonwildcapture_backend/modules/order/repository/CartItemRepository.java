package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Find all cart items for a user
     */
    List<CartItem> findByUserId(Long userId);

    /**
     * Find cart item by user, photo, and license type
     */
    Optional<CartItem> findByUserIdAndPhotoIdAndLicenseType(
            Long userId,
            Long photoId,
            LicenseType licenseType);

    /**
     * Check if cart item exists
     */
    boolean existsByUserIdAndPhotoIdAndLicenseType(
            Long userId,
            Long photoId,
            LicenseType licenseType);

    /**
     * Count cart items for a user
     */
    long countByUserId(Long userId);

    /**
     * Delete all cart items for a user
     */
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    /**
     * Delete cart item by user and cart item id
     */
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.id = :cartItemId AND c.user.id = :userId")
    void deleteByIdAndUserId(@Param("cartItemId") Long cartItemId, @Param("userId") Long userId);

    /**
     * Check if photo is in user's cart
     */
    boolean existsByUserIdAndPhotoId(Long userId, Long photoId);
}
