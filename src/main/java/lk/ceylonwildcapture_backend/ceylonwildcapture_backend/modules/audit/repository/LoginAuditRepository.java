package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.LoginAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
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
 * Repository interface for LoginAudit entity.
 * Provides database operations for login audit records.
 */
@Repository
public interface LoginAuditRepository extends JpaRepository<LoginAudit, Long> {

    /**
     * Find all login audits for a specific user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of login audits
     */
    Page<LoginAudit> findByUserId(Long userId, Pageable pageable);

    /**
     * Find login audits by action result.
     *
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of login audits
     */
    Page<LoginAudit> findByActionResult(ActionResult actionResult, Pageable pageable);

    /**
     * Find login audits for a user by action result.
     *
     * @param userId the user ID
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of login audits
     */
    Page<LoginAudit> findByUserIdAndActionResult(Long userId, ActionResult actionResult, Pageable pageable);

    /**
     * Find login audits within a date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of login audits
     */
    Page<LoginAudit> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find login audits by IP address.
     *
     * @param ipAddress the IP address
     * @param pageable pagination information
     * @return page of login audits
     */
    Page<LoginAudit> findByIpAddress(String ipAddress, Pageable pageable);

    /**
     * Find login audits by country.
     *
     * @param country the country
     * @param pageable pagination information
     * @return page of login audits
     */
    Page<LoginAudit> findByCountry(String country, Pageable pageable);

    /**
     * Count login attempts for a user.
     *
     * @param userId the user ID
     * @return count of login attempts
     */
    long countByUserId(Long userId);

    /**
     * Count failed login attempts for a user.
     *
     * @param userId the user ID
     * @param actionResult the action result
     * @return count of failed login attempts
     */
    long countByUserIdAndActionResult(Long userId, ActionResult actionResult);

    /**
     * Find recent failed login attempts for a user.
     *
     * @param userId the user ID
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of failed login audits
     */
    @Query("SELECT la FROM LoginAudit la WHERE la.user.id = :userId " +
           "AND la.actionResult = :actionResult ORDER BY la.createdAt DESC")
    Page<LoginAudit> findRecentFailedAttempts(
            @Param("userId") Long userId,
            @Param("actionResult") ActionResult actionResult,
            Pageable pageable);

    /**
     * Find most recent login audit for a user.
     *
     * @param userId the user ID
     * @return optional containing the most recent login audit
     */
    @Query(value = "SELECT * FROM login_audits WHERE user_id = :userId ORDER BY created_at DESC LIMIT 1",
           nativeQuery = true)
    Optional<LoginAudit> findMostRecentByUserId(@Param("userId") Long userId);

    /**
     * Count login attempts within date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return count of login attempts
     */
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find login audits for user within date range by result.
     *
     * @param userId the user ID
     * @param startDate the start date
     * @param endDate the end date
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of login audits
     */
    @Query("SELECT la FROM LoginAudit la WHERE la.user.id = :userId " +
           "AND la.createdAt BETWEEN :startDate AND :endDate " +
           "AND la.actionResult = :actionResult ORDER BY la.createdAt DESC")
    Page<LoginAudit> findByUserIdAndDateRangeAndResult(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("actionResult") ActionResult actionResult,
            Pageable pageable);
}
