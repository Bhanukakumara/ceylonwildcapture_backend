package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums;

/**
 * Enumeration of audit types tracked in the system.
 */
public enum AuditType {
    /**
     * Photo download event via licensed access
     */
    DOWNLOAD,

    /**
     * Successful user login
     */
    LOGIN,

    /**
     * Failed user login attempt
     */
    LOGIN_FAILED,

    /**
     * Admin moderation action on content
     */
    ADMIN_MODERATION,

    /**
     * Payout approval or rejection action
     */
    PAYOUT_ACTION,

    /**
     * Payment-related event
     */
    PAYMENT_EVENT,

    /**
     * User profile or account update
     */
    USER_UPDATE,

    /**
     * Password reset event
     */
    PASSWORD_RESET
}
