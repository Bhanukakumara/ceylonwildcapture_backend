package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.License;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.OrderItem;

import java.time.LocalDateTime;

/**
 * Service interface for license generation logic.
 * Defines business logic for generating unique license keys,
 * setting license parameters, and preparing license data.
 */
public interface LicenseGenerationService {

    /**
     * Generate license for order item.
     *
     * @param orderItem the order item
     * @return the generated license
     */
    License generateLicense(OrderItem orderItem);

    /**
     * Generate unique license key.
     *
     * @return the generated license key
     */
    String generateUniqueLicenseKey();

    /**
     * Generate license key with custom prefix.
     *
     * @param prefix the license key prefix
     * @return the generated license key
     */
    String generateLicenseKeyWithPrefix(String prefix);



    /**
     * Validate license key format.
     *
     * @param licenseKey the license key to validate
     * @return true if license key format is valid
     */
    boolean isValidLicenseKeyFormat(String licenseKey);

    /**
     * Check if license key is unique.
     *
     * @param licenseKey the license key to check
     * @return true if license key is unique
     */
    boolean isLicenseKeyUnique(String licenseKey);

    /**
     * Determine download limit for license.
     *
     * @return the download limit (null for unlimited)
     */
    Integer determineDownloadLimit();

    /**
     * Determine expiration date for license.
     *
     * @param issueDate the issue date
     * @return the expiration date (null for no expiration)
     */
    LocalDateTime determineExpirationDate(LocalDateTime issueDate);

    /**
     * Get default license terms.
     *
     * @return the license terms text
     */
    String getLicenseTerms();

    /**
     * Prepare license data for order item.
     *
     * @param orderItem the order item
     * @return the prepared license entity
     */
    License prepareLicenseData(OrderItem orderItem);

    /**
     * Generate license certificate.
     *
     * @param license the license entity
     * @return the license certificate content (PDF, HTML, etc.)
     */
    Object generateLicenseCertificate(License license);

    /**
     * Generate license QR code.
     *
     * @param licenseKey the license key
     * @return the QR code image data
     */
    byte[] generateLicenseQRCode(String licenseKey);

    /**
     * Encrypt license key.
     *
     * @param licenseKey the license key
     * @return the encrypted license key
     */
    String encryptLicenseKey(String licenseKey);

    /**
     * Decrypt license key.
     *
     * @param encryptedKey the encrypted license key
     * @return the decrypted license key
     */
    String decryptLicenseKey(String encryptedKey);

    /**
     * Generate license verification URL.
     *
     * @param licenseKey the license key
     * @return the verification URL
     */
    String generateVerificationUrl(String licenseKey);

    /**
     * Generate license metadata.
     *
     * @param orderItem the order item
     * @return the license metadata (DTO placeholder)
     */
    Object generateLicenseMetadata(OrderItem orderItem);

    /**
     * Validate license generation eligibility.
     *
     * @param orderItem the order item
     * @return true if license can be generated
     */
    boolean canGenerateLicense(OrderItem orderItem);

    /**
     * Get default license configuration.
     *
     * @return the license configuration (DTO placeholder)
     */
    Object getDefaultLicenseConfiguration();
}
