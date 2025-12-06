package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.entity.UserActivityAudit;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for UserActivityAudit entity.
 * Provides database operations for user activity audit records.
 */
@Repository
public interface UserActivityAuditRepository extends JpaRepository<UserActivityAudit, Long> {

    /**
     * Find all user activity audits for a specific user.
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of user activity audits
     */
    Page<UserActivityAudit> findByUserId(Long userId, Pageable pageable);

    /**
     * Find user activity audits by action type.
     *
     * @param action the action type
     * @param pageable pagination information
     * @return page of user activity audits
     */
    Page<UserActivityAudit> findByAction(String action, Pageable pageable);

    /**
     * Find user activity audits by action result.
     *
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of user activity audits
     */
    Page<UserActivityAudit> findByActionResult(ActionResult actionResult, Pageable pageable);

    /**
     * Find user activity audits within a date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of user activity audits
     */
    Page<UserActivityAudit> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find user activity audits by user and action.
     *
     * @param userId the user ID
     * @param action the action type
     * @param pageable pagination information
     * @return page of user activity audits
     */
    Page<UserActivityAudit> findByUserIdAndAction(Long userId, String action, Pageable pageable);

    /**
     * Find user activity audits by user and action result.
     *
     * @param userId the user ID
     * @param actionResult the action result
     * @param pageable pagination information
     * @return page of user activity audits
     */
    Page<UserActivityAudit> findByUserIdAndActionResult(Long userId, ActionResult actionResult, Pageable pageable);

    /**
     * Find user activity audits by user and date range.
     *
     * @param userId the user ID
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of user activity audits
     */
    @Query("SELECT ua FROM UserActivityAudit ua WHERE ua.user.id = :userId " +
           "AND ua.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY ua.createdAt DESC")
    Page<UserActivityAudit> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * Find user activity audits by user, action, and date range.
     *
     * @param userId the user ID
     * @param action the action type
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of user activity audits
     */
    @Query("SELECT ua FROM UserActivityAudit ua WHERE ua.user.id = :userId " +
           "AND ua.action = :action " +
           "AND ua.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY ua.createdAt DESC")
    Page<UserActivityAudit> findByUserIdAndActionAndDateRange(
            @Param("userId") Long userId,
            @Param("action") String action,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    /**
     * Count user activity audits for a user.
     *
     * @param userId the user ID
     * @return count of user activity audits
     */
    long countByUserId(Long userId);

    /**
     * Count user activity audits by action.
     *
     * @param action the action type
     * @return count of user activity audits
     */
    long countByAction(String action);

    /**
     * Find user activity audits by user and action result and date range.
     *
     * @param userId the user ID
     * @param actionResult the action result
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return page of user activity audits
     */
    @Query("SELECT ua FROM UserActivityAudit ua WHERE ua.user.id = :userId " +
           "AND ua.actionResult = :actionResult " +
           "AND ua.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY ua.createdAt DESC")
    Page<UserActivityAudit> findByUserIdAndActionResultAndDateRange(
            @Param("userId") Long userId,
            @Param("actionResult") ActionResult actionResult,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
