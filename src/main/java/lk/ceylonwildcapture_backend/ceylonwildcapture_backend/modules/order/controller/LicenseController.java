package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.controller;

import jakarta.validation.Valid;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.dto.*;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for license management operations.
 */
@RestController
@RequestMapping("/api/v1/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    /**
     * Get license by ID.
     *
     * @param licenseId license ID
     * @param userId authenticated user ID
     * @return license response
     */
    @GetMapping("/{licenseId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<LicenseResponseDto> getLicense(
            @PathVariable Long licenseId,
            @RequestAttribute("userId") Long userId) {
        LicenseResponseDto response = licenseService.getLicenseDto(licenseId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get license by license key.
     *
     * @param licenseKey license key
     * @param userId authenticated user ID
     * @return license response
     */
    @GetMapping("/key/{licenseKey}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER', 'ADMIN')")
    public ResponseEntity<LicenseResponseDto> getLicenseByKey(
            @PathVariable String licenseKey,
            @RequestAttribute("userId") Long userId) {
        LicenseResponseDto response = licenseService.getLicenseDtoByKey(licenseKey, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get current user's licenses.
     *
     * @param userId authenticated user ID
     * @param pageable pagination parameters
     * @return page of licenses
     */
    @GetMapping("/my-licenses")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER')")
    public ResponseEntity<Page<LicenseResponseDto>> getMyLicenses(
            @RequestAttribute("userId") Long userId,
            Pageable pageable) {
        Page<LicenseResponseDto> licenses = licenseService.getUserLicenses(userId, pageable);
        return ResponseEntity.ok(licenses);
    }

    /**
     * Get current user's active licenses.
     *
     * @param userId authenticated user ID
     * @param pageable pagination parameters
     * @return page of active licenses
     */
    @GetMapping("/my-licenses/active")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER')")
    public ResponseEntity<Page<LicenseResponseDto>> getMyActiveLicenses(
            @RequestAttribute("userId") Long userId,
            Pageable pageable) {
        Page<LicenseResponseDto> licenses = licenseService.getUserActiveLicenses(userId, pageable);
        return ResponseEntity.ok(licenses);
    }

    /**
     * Get licenses for a specific photo owned by current user.
     *
     * @param userId authenticated user ID
     * @param photoId photo ID
     * @return list of licenses
     */
    @GetMapping("/my-licenses/photo/{photoId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER')")
    public ResponseEntity<List<LicenseResponseDto>> getMyLicensesForPhoto(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long photoId) {
        List<LicenseResponseDto> licenses = licenseService.getUserLicensesForPhoto(userId, photoId);
        return ResponseEntity.ok(licenses);
    }

    /**
     * Verify license ownership for download.
     *
     * @param requestDto download authorization request
     * @param userId authenticated user ID
     * @return verification result
     */
    @PostMapping("/verify")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER')")
    public ResponseEntity<LicenseVerificationResultDto> verifyLicense(
            @Valid @RequestBody DownloadAuthorizationRequestDto requestDto,
            @RequestAttribute("userId") Long userId) {
        LicenseVerificationResultDto result = licenseService.verifyLicenseOwnership(
                requestDto.getLicenseKey(),
                requestDto.getPhotoId(),
                userId
        );
        return ResponseEntity.ok(result);
    }

    /**
     * Validate license for download.
     *
     * @param licenseKey license key
     * @param photoId photo ID
     * @param userId authenticated user ID
     * @return validation result
     */
    @GetMapping("/validate-download")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER')")
    public ResponseEntity<LicenseVerificationResultDto> validateForDownload(
            @RequestParam String licenseKey,
            @RequestParam Long photoId,
            @RequestAttribute("userId") Long userId) {
        LicenseVerificationResultDto result = licenseService.validateForDownload(licenseKey, photoId, userId);
        return ResponseEntity.ok(result);
    }

    /**
     * Check if user has purchased a photo.
     *
     * @param userId authenticated user ID
     * @param photoId photo ID
     * @return purchase check result
     */
    @GetMapping("/check-purchase/{photoId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER')")
    public ResponseEntity<PhotoPurchaseCheckDto> checkPhotoPurchase(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long photoId) {
        PhotoPurchaseCheckDto result = licenseService.checkPhotoPurchase(userId, photoId);
        return ResponseEntity.ok(result);
    }

    /**
     * Search licenses (admin only).
     *
     * @param criteria search criteria
     * @param pageable pagination parameters
     * @return page of licenses
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<LicenseResponseDto>> searchLicenses(
            @ModelAttribute LicenseSearchCriteria criteria,
            Pageable pageable) {
        Page<LicenseResponseDto> licenses = licenseService.searchLicenses(criteria, pageable);
        return ResponseEntity.ok(licenses);
    }

    /**
     * Get license count for current user.
     *
     * @param userId authenticated user ID
     * @return license count
     */
    @GetMapping("/my-licenses/count")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PHOTOGRAPHER')")
    public ResponseEntity<Long> getMyLicenseCount(@RequestAttribute("userId") Long userId) {
        long count = licenseService.countUserLicenses(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * Deactivate a license (admin only).
     *
     * @param licenseId license ID
     * @return updated license
     */
    @PostMapping("/{licenseId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LicenseResponseDto> deactivateLicense(@PathVariable Long licenseId) {
        LicenseResponseDto response = licenseService.deactivate(licenseId);
        return ResponseEntity.ok(response);
    }

    /**
     * Activate a license (admin only).
     *
     * @param licenseId license ID
     * @return updated license
     */
    @PostMapping("/{licenseId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LicenseResponseDto> activateLicense(@PathVariable Long licenseId) {
        LicenseResponseDto response = licenseService.activate(licenseId);
        return ResponseEntity.ok(response);
    }
}
