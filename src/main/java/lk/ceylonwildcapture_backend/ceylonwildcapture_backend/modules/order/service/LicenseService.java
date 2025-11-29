package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.common.enums.LicenseType;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.LicenseResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.LicenseSearchCriteria;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.LicenseVerificationResultDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.PhotoPurchaseCheckDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.License;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for License management operations.
 * Defines business logic for license generation, validation, ownership verification,
 * and license retrieval.
 */
public interface LicenseService {

    /**
     * Create a new license.
     *
     * @param license the license entity to create
     * @return the created license
     * @throws IllegalArgumentException if license data is invalid
     */
    License createLicense(License license);

    /**
     * Generate license for order item.
     *
     * @param orderItemId the order item ID
     * @return the generated license
     * @throws IllegalArgumentException if order item not found or license already exists
     */
    License generateLicenseForOrderItem(Long orderItemId);

    /**
     * Generate licenses for all items in order.
     *
     * @param orderId the order ID
     * @return list of generated licenses
     * @throws IllegalArgumentException if order not found
     */
    List<License> generateLicensesForOrder(Long orderId);

    /**
     * Update license.
     *
     * @param licenseId the license ID
     * @param license the updated license data
     * @return the updated license
     * @throws IllegalArgumentException if license not found
     */
    License updateLicense(Long licenseId, License license);

    /**
     * Get license by ID.
     *
     * @param licenseId the license ID
     * @return Optional containing the license if found
     */
    Optional<License> getLicenseById(Long licenseId);

    /**
     * Get license by license key.
     *
     * @param licenseKey the license key
     * @return Optional containing the license if found
     */
    Optional<License> getLicenseByKey(String licenseKey);

    /**
     * Get license by order item.
     *
     * @param orderItemId the order item ID
     * @return Optional containing the license if found
     */
    Optional<License> getLicenseByOrderItem(Long orderItemId);

    /**
     * Get all licenses by buyer.
     *
     * @param buyerId the buyer ID
     * @param pageable pagination information
     * @return page of licenses owned by the buyer
     */
    Page<License> getLicensesByBuyer(Long buyerId, Pageable pageable);

    /**
     * Get active licenses by buyer.
     *
     * @param buyerId the buyer ID
     * @param pageable pagination information
     * @return page of active licenses
     */
    Page<License> getActiveLicensesByBuyer(Long buyerId, Pageable pageable);

    /**
     * Get licenses for specific photo owned by buyer.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @return list of licenses
     */
    List<License> getLicensesByBuyerAndPhoto(Long buyerId, Long photoId);

    /**
     * Get active license for photo owned by buyer.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @return Optional containing the active license if found
     */
    Optional<License> getActiveLicenseByBuyerAndPhoto(Long buyerId, Long photoId);

    /**
     * Get licenses by license type.
     *
     * @param licenseType the license type
     * @param pageable pagination information
     * @return page of licenses
     */
    Page<License> getLicensesByLicenseType(LicenseType licenseType, Pageable pageable);

    /**
     * Get licenses by order.
     *
     * @param orderId the order ID
     * @return list of licenses for the order
     */
    List<License> getLicensesByOrder(Long orderId);

    /**
     * Get licenses for photo.
     *
     * @param photoId the photo ID
     * @param pageable pagination information
     * @return page of licenses
     */
    Page<License> getLicensesByPhoto(Long photoId, Pageable pageable);

    /**
     * Delete license.
     *
     * @param licenseId the license ID
     * @throws IllegalArgumentException if license not found or cannot be deleted
     */
    void deleteLicense(Long licenseId);

    /**
     * Activate license.
     *
     * @param licenseId the license ID
     * @return the activated license
     * @throws IllegalArgumentException if license not found
     */
    License activateLicense(Long licenseId);

    /**
     * Deactivate license.
     *
     * @param licenseId the license ID
     * @return the deactivated license
     * @throws IllegalArgumentException if license not found
     */
    License deactivateLicense(Long licenseId);

    /**
     * Validate license by key.
     *
     * @param licenseKey the license key
     * @return validation result with license details
     */
    Object validateLicense(String licenseKey);

    /**
     * Check if buyer has valid license for photo.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @return true if buyer has a valid (active and not expired) license
     */
    boolean hasValidLicense(Long buyerId, Long photoId);

    /**
     * Check if buyer has specific license type for photo.
     *
     * @param buyerId the buyer ID
     * @param photoId the photo ID
     * @param licenseType the license type
     * @return true if buyer has the specific license type
     */
    boolean hasLicenseType(Long buyerId, Long photoId, LicenseType licenseType);

    /**
     * Record license download.
     *
     * @param licenseId the license ID
     * @return the updated license
     * @throws IllegalArgumentException if license not found or download limit exceeded
     */
    License recordDownload(Long licenseId);

    /**
     * Check if license can be downloaded.
     *
     * @param licenseId the license ID
     * @return true if license can be downloaded (not expired, within download limit)
     */
    boolean canDownload(Long licenseId);

    /**
     * Set license expiration date.
     *
     * @param licenseId the license ID
     * @param expiresAt the expiration date
     * @return the updated license
     * @throws IllegalArgumentException if license not found
     */
    License setExpirationDate(Long licenseId, LocalDateTime expiresAt);

    /**
     * Set license download limit.
     *
     * @param licenseId the license ID
     * @param downloadLimit the download limit
     * @return the updated license
     * @throws IllegalArgumentException if license not found
     */
    License setDownloadLimit(Long licenseId, Integer downloadLimit);

    /**
     * Get expiring licenses within days.
     *
     * @param days the number of days to look ahead
     * @return list of expiring licenses
     */
    List<License> getExpiringLicenses(int days);

    /**
     * Get expired licenses.
     *
     * @return list of expired licenses
     */
    List<License> getExpiredLicenses();

    /**
     * Deactivate expired licenses.
     *
     * @return count of deactivated licenses
     */
    long deactivateExpiredLicenses();

    /**
     * Get licenses with exceeded download limits.
     *
     * @return list of licenses with exceeded downloads
     */
    List<License> getLicensesWithExceededDownloads();

    /**
     * Get recently downloaded licenses.
     *
     * @param pageable pagination information
     * @return page of recently downloaded licenses
     */
    Page<License> getRecentlyDownloadedLicenses(Pageable pageable);

    /**
     * Get most downloaded licenses.
     *
     * @param pageable pagination information
     * @return page of licenses ordered by download count
     */
    Page<License> getMostDownloadedLicenses(Pageable pageable);

    /**
     * Count licenses by buyer.
     *
     * @param buyerId the buyer ID
     * @return count of licenses
     */
    long countLicensesByBuyer(Long buyerId);

    /**
     * Count active licenses by buyer.
     *
     * @param buyerId the buyer ID
     * @return count of active licenses
     */
    long countActiveLicensesByBuyer(Long buyerId);

    /**
     * Count licenses by license type.
     *
     * @param licenseType the license type
     * @return count of licenses
     */
    long countLicensesByLicenseType(LicenseType licenseType);

    /**
     * Generate unique license key.
     *
     * @return the generated license key
     */
    String generateLicenseKey();

    /**
     * Get license terms and conditions.
     *
     * @param licenseType the license type
     * @return the license terms text
     */
    String getLicenseTerms(LicenseType licenseType);

    /**
     * Get buyer's licenses by license type.
     *
     * @param buyerId the buyer ID
     * @param licenseType the license type
     * @param pageable pagination information
     * @return page of licenses
     */
    Page<License> getLicensesByBuyerAndLicenseType(Long buyerId, LicenseType licenseType, Pageable pageable);

    LicenseResponseDto getLicenseDto(Long licenseId, Long userId);

    LicenseResponseDto getLicenseDtoByKey(String licenseKey, Long userId);

    Page<LicenseResponseDto> getUserLicenses(Long userId, Pageable pageable);

    Page<LicenseResponseDto> getUserActiveLicenses(Long userId, Pageable pageable);

    List<LicenseResponseDto> getUserLicensesForPhoto(Long userId, Long photoId);

    LicenseVerificationResultDto verifyLicenseOwnership(@NotBlank(message = "License key is required") String licenseKey, @NotNull(message = "Photo ID is required") Long photoId, Long userId);

    LicenseVerificationResultDto validateForDownload(String licenseKey, Long photoId, Long userId);

    PhotoPurchaseCheckDto checkPhotoPurchase(Long userId, Long photoId);

    Page<LicenseResponseDto> searchLicenses(LicenseSearchCriteria criteria, Pageable pageable);

    long countUserLicenses(Long userId);

    LicenseResponseDto deactivate(Long licenseId);

    LicenseResponseDto activate(Long licenseId);
}
