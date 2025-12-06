package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.util;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.ActionResult;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums.EntityType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Utility interface for simplified audit record dispatching from other modules.
 * Provides convenience methods for recording different types of audit events.
 */
public interface AuditRecorder {

    /**
     * Record a successful login attempt.
     *
     * @param userId the user ID
     * @param ipAddress the IP address
     * @param userAgent the user agent
     */
    void recordLoginSuccess(Long userId, String ipAddress, String userAgent);

    /**
     * Record a failed login attempt.
     *
     * @param userId the user ID (may be null if user not found)
     * @param username the username attempted
     * @param ipAddress the IP address
     * @param userAgent the user agent
     * @param errorMessage the error message
     */
    void recordLoginFailure(Long userId, String username, String ipAddress, String userAgent, String errorMessage);

    /**
     * Record a photo download event.
     *
     * @param userId the user ID
     * @param licenseId the license ID
     * @param photoId the photo ID
     * @param ipAddress the IP address
     * @param userAgent the user agent
     * @param successful whether download was successful
     * @param errorMessage error message if failed
     */
    void recordDownload(Long userId, Long licenseId, Long photoId, String ipAddress, String userAgent,
                       Boolean successful, String errorMessage);

    /**
     * Record a user profile update.
     *
     * @param userId the user ID
     * @param action the action description
     * @param oldValue the old value
     * @param newValue the new value
     * @param ipAddress the IP address
     * @param userAgent the user agent
     */
    void recordUserProfileUpdate(Long userId, String action, String oldValue, String newValue,
                                 String ipAddress, String userAgent);

    /**
     * Record a password reset event.
     *
     * @param userId the user ID
     * @param ipAddress the IP address
     * @param userAgent the user agent
     * @param successful whether reset was successful
     */
    void recordPasswordReset(Long userId, String ipAddress, String userAgent, Boolean successful);

    /**
     * Record an admin moderation action.
     *
     * @param adminId the admin user ID
     * @param entityType the entity type being moderated
     * @param entityId the entity ID
     * @param action the moderation action
     * @param reason the reason for action
     * @param ipAddress the IP address
     * @param userAgent the user agent
     * @param successful whether action was successful
     */
    void recordAdminModerationAction(Long adminId, EntityType entityType, Long entityId, String action,
                                     String reason, String ipAddress, String userAgent, Boolean successful);

    /**
     * Record a payment event.
     *
     * @param userId the user ID
     * @param paymentId the payment ID
     * @param orderId the order ID (optional)
     * @param action the payment action
     * @param amount the payment amount
     * @param currency the currency code
     * @param paymentMethod the payment method
     * @param transactionId the transaction ID
     * @param ipAddress the IP address
     * @param successful whether payment was successful
     * @param errorMessage error message if failed
     */
    void recordPaymentEvent(Long userId, Long paymentId, Long orderId, String action, BigDecimal amount,
                           String currency, String paymentMethod, String transactionId, String ipAddress,
                           Boolean successful, String errorMessage);

    /**
     * Record a payout approval or rejection.
     *
     * @param adminId the admin user ID
     * @param photographerId the photographer user ID
     * @param payoutId the payout ID
     * @param action the action (APPROVE or REJECT)
     * @param reason the reason for action
     * @param ipAddress the IP address
     * @param userAgent the user agent
     */
    void recordPayoutAction(Long adminId, Long photographerId, Long payoutId, String action,
                           String reason, String ipAddress, String userAgent);

    /**
     * Record a system configuration change.
     *
     * @param adminId the admin user ID
     * @param configKey the configuration key
     * @param oldValue the old value
     * @param newValue the new value
     * @param ipAddress the IP address
     * @param userAgent the user agent
     */
    void recordSystemConfigChange(Long adminId, String configKey, String oldValue, String newValue,
                                  String ipAddress, String userAgent);

    /**
     * Record a generic audit event.
     *
     * @param userId the user ID (actor)
     * @param entityType the entity type
     * @param entityId the entity ID
     * @param action the action performed
     * @param ipAddress the IP address
     * @param userAgent the user agent
     * @param successful whether action was successful
     * @param metadata additional metadata as key-value pairs
     */
    void recordAuditEvent(Long userId, EntityType entityType, Long entityId, String action,
                         String ipAddress, String userAgent, Boolean successful, Map<String, Object> metadata);
}
