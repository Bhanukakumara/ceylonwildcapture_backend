package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.audit.enums;

/**
 * Enum representing the different types of audit events in the system.
 */
public enum AuditType {
    DOWNLOAD,
    LOGIN,
    LOGIN_FAILED,
    ADMIN_MODERATION,
    PAYOUT_ACTION,
    PAYMENT_EVENT,
    USER_UPDATE
}
