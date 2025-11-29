package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.License;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for License entity.
 * Provides database operations for license management, validation,
 * and user license ownership tracking.
 */
@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

    /**
     * Find license by license key.
     *
     * @param licenseKey the unique license key
     * @return Optional containing the license if found
     */
    Optional<License> findByLicenseKey(String licenseKey);

    /**
     * Find license by order item.
     *
     * @param orderItem the order item entity
     * @return Optional containing the license if found
     */
    Optional<License> findByOrderItem(OrderItem orderItem);

    /**
     * Find license by order item ID.
     *
     * @param orderItemId the order item ID
     * @return Optional containing the license if found
     */
    Optional<License> findByOrderItemId(Long orderItemId);

    /**
     * Find all licenses by buyer (user).
     *
     * @param buyerId the buyer ID
     * @param pageable pagination information
     * @return page of licenses owned by the buyer
     */
    @Query("SELECT l FROM License l WHERE l.orderItem.order.buyer.id = :buyerId")
    Page<License> findByBuyerId(@Param("buyerId") Long buyerId, Pageable pageable);

    /**
     * Find all active licenses by buyer.
     *
     * @param buyerId the buyer ID
     * @param isActive the active status
     * @param pageable pagination information
     * @return page of active licenses
     */
    @Query("SELECT l FROM License l WHERE l.orderItem.order.buyer.id = :buyerId AND l.isActive = :isActive")
    Page<License> findByBuyerIdAndIsActive(@Param("buyerId") Long buyerId,
                                           @Param("isActive") Boolean isActive,
                                           Pageable pageable);

    /**
     * Find licenses for a specific photo owned by buyer.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @return list of licenses
     */
    @Query("SELECT l FROM License l WHERE l.orderItem.order.buyer.id = :buyerId " +
           "AND l.orderItem.photo.id = :photoId")
    List<License> findByBuyerIdAndPhotoId(@Param("buyerId") Long buyerId, @Param("photoId") Long photoId);

    /**
     * Find active license for a specific photo owned by buyer.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @param isActive the active status
     * @return Optional containing the active license if found
     */
    @Query("SELECT l FROM License l WHERE l.orderItem.order.buyer.id = :buyerId " +
           "AND l.orderItem.photo.id = :photoId AND l.isActive = :isActive")
    Optional<License> findActiveLicenseByBuyerAndPhoto(@Param("buyerId") Long buyerId,
                                                        @Param("photoId") Long photoId,
                                                        @Param("isActive") Boolean isActive);

    /**
     * Check if buyer has valid license for photo.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @param isActive the active status
     * @return true if buyer has a valid license
     */
    @Query("SELECT COUNT(l) > 0 FROM License l WHERE l.orderItem.order.buyer.id = :buyerId " +
           "AND l.orderItem.photo.id = :photoId AND l.isActive = :isActive " +
           "AND (l.expiresAt IS NULL OR l.expiresAt > CURRENT_TIMESTAMP)")
    boolean hasValidLicense(@Param("buyerId") Long buyerId,
                           @Param("photoId") Long photoId,
                           @Param("isActive") Boolean isActive);

    /**
     * Check if buyer has license with specific license type for photo.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @param licenseType the license type
     * @return true if buyer has the specific license type
     */
    @Query("SELECT COUNT(l) > 0 FROM License l WHERE l.orderItem.order.buyer.id = :buyerId " +
           "AND l.orderItem.photo.id = :photoId AND l.licenseType = :licenseType " +
           "AND l.isActive = true AND (l.expiresAt IS NULL OR l.expiresAt > CURRENT_TIMESTAMP)")
    boolean hasLicenseType(@Param("buyerId") Long buyerId,
                          @Param("photoId") Long photoId,
                          @Param("licenseType") LicenseType licenseType);

    /**
     * Find licenses by license type.
     *
     * @param licenseType the license type
     * @param pageable pagination information
     * @return page of licenses
     */
    Page<License> findByLicenseType(LicenseType licenseType, Pageable pageable);

    /**
     * Find licenses by active status.
     *
     * @param isActive the active status
     * @param pageable pagination information
     * @return page of licenses
     */
    Page<License> findByIsActive(Boolean isActive, Pageable pageable);

    /**
     * Find licenses expiring within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of expiring licenses
     */
    List<License> findByExpiresAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find expired licenses.
     *
     * @param currentDate the current date
     * @param isActive the active status
     * @return list of expired licenses
     */
    @Query("SELECT l FROM License l WHERE l.expiresAt < :currentDate AND l.isActive = :isActive")
    List<License> findExpiredLicenses(@Param("currentDate") LocalDateTime currentDate,
                                     @Param("isActive") Boolean isActive);

    /**
     * Find licenses by issued email.
     *
     * @param issuedEmail the issued email
     * @param pageable pagination information
     * @return page of licenses
     */
    Page<License> findByIssuedEmailContainingIgnoreCase(String issuedEmail, Pageable pageable);

    /**
     * Find licenses with download limit exceeded.
     *
     * @return list of licenses with exceeded download limits
     */
    @Query("SELECT l FROM License l WHERE l.downloadLimit IS NOT NULL " +
           "AND l.downloadCount >= l.downloadLimit")
    List<License> findLicensesWithExceededDownloads();

    /**
     * Find licenses by order.
     *
     * @param orderId the order ID
     * @return list of licenses for the order
     */
    @Query("SELECT l FROM License l WHERE l.orderItem.order.id = :orderId")
    List<License> findByOrderId(@Param("orderId") Long orderId);

    /**
     * Count licenses by buyer.
     *
     * @param buyerId the buyer ID
     * @return count of licenses
     */
    @Query("SELECT COUNT(l) FROM License l WHERE l.orderItem.order.buyer.id = :buyerId")
    long countByBuyerId(@Param("buyerId") Long buyerId);

    /**
     * Count active licenses by buyer.
     *
     * @param buyerId the buyer ID
     * @param isActive the active status
     * @return count of active licenses
     */
    @Query("SELECT COUNT(l) FROM License l WHERE l.orderItem.order.buyer.id = :buyerId AND l.isActive = :isActive")
    long countByBuyerIdAndIsActive(@Param("buyerId") Long buyerId, @Param("isActive") Boolean isActive);

    /**
     * Count licenses by license type.
     *
     * @param licenseType the license type
     * @return count of licenses
     */
    long countByLicenseType(LicenseType licenseType);

    /**
     * Check if license key exists.
     *
     * @param licenseKey the license key
     * @return true if license key exists
     */
    boolean existsByLicenseKey(String licenseKey);

    /**
     * Find recently created licenses.
     *
     * @param pageable pagination information
     * @return page of recent licenses
     */
    Page<License> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Find recently downloaded licenses.
     *
     * @param pageable pagination information
     * @return page of licenses ordered by last download
     */
    @Query("SELECT l FROM License l WHERE l.lastDownloadedAt IS NOT NULL ORDER BY l.lastDownloadedAt DESC")
    Page<License> findRecentlyDownloadedLicenses(Pageable pageable);

    /**
     * Find licenses for a specific photo.
     *
     * @param photoId the photo ID
     * @param pageable pagination information
     * @return page of licenses
     */
    @Query("SELECT l FROM License l WHERE l.orderItem.photo.id = :photoId")
    Page<License> findByPhotoId(@Param("photoId") Long photoId, Pageable pageable);

    /**
     * Find most downloaded licenses.
     *
     * @param pageable pagination information
     * @return page of licenses ordered by download count
     */
    Page<License> findAllByOrderByDownloadCountDesc(Pageable pageable);

    /**
     * Find licenses by buyer and license type.
     *
     * @param buyerId the buyer ID
     * @param licenseType the license type
     * @param pageable pagination information
     * @return page of licenses
     */
    @Query("SELECT l FROM License l WHERE l.orderItem.order.buyer.id = :buyerId AND l.licenseType = :licenseType")
    Page<License> findByBuyerIdAndLicenseType(@Param("buyerId") Long buyerId,
                                               @Param("licenseType") LicenseType licenseType,
                                               Pageable pageable);
}
