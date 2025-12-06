package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payout.dto.EarningsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * Service interface for earnings operations.
 * Provides methods for calculating and retrieving earnings information.
 */
public interface EarningsService {

    /**
     * Calculate pending earnings for a photographer.
     *
     * @param photographerId the photographer ID
     * @return pending earnings amount
     */
    BigDecimal calculatePendingEarnings(Long photographerId);

    /**
     * Calculate total earnings for a photographer.
     *
     * @param photographerId the photographer ID
     * @return total earnings amount
     */
    BigDecimal calculateTotalEarnings(Long photographerId);

    /**
     * Calculate paid earnings for a photographer.
     *
     * @param photographerId the photographer ID
     * @return paid earnings amount
     */
    BigDecimal calculatePaidEarnings(Long photographerId);

    /**
     * Get earnings summary for a photographer.
     *
     * @param photographerId the photographer ID
     * @return earnings response DTO
     */
    EarningsResponseDto getEarningsSummary(Long photographerId);

    /**
     * Get earnings history for a photographer.
     *
     * @param photographerId the photographer ID
     * @param pageable pagination information
     * @return page of earnings response DTOs
     */
    Page<EarningsResponseDto> getEarningsHistory(Long photographerId, Pageable pageable);

    /**
     * Get earnings for date range.
     *
     * @param photographerId the photographer ID
     * @param startDate the start date
     * @param endDate the end date
     * @return earnings amount
     */
    BigDecimal getEarningsForDateRange(Long photographerId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Create earnings snapshot for a photographer.
     *
     * @param photographerId the photographer ID
     * @return earnings response DTO
     */
    EarningsResponseDto createEarningsSnapshot(Long photographerId);

    /**
     * Get most recent earnings snapshot for a photographer.
     *
     * @param photographerId the photographer ID
     * @return optional containing earnings response DTO
     */
    Optional<EarningsResponseDto> getMostRecentEarningsSnapshot(Long photographerId);

    /**
     * Calculate earnings breakdown for a photographer.
     *
     * @param photographerId the photographer ID
     * @return map containing earnings breakdown
     */
    Map<String, BigDecimal> getEarningsBreakdown(Long photographerId);

    /**
     * Get earnings statistics.
     *
     * @return map containing earnings statistics
     */
    Map<String, Object> getEarningsStatistics();

    /**
     * Update earnings for a photographer based on order completion.
     *
     * @param photographerId the photographer ID
     * @param orderItemId the order item ID
     */
    void updateEarningsFromOrderItem(Long photographerId, Long orderItemId);

    /**
     * Recalculate all earnings for a photographer.
     *
     * @param photographerId the photographer ID
     */
    void recalculateEarningsForPhotographer(Long photographerId);
}
