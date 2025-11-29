package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.util;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;

import java.util.Map;

/**
 * Interface for email sending operations.
 * Provides methods for sending various types of emails including verification,
 * password reset, and notifications.
 */
public interface EmailService {

    /**
     * Send email verification email to user.
     *
     * @param user the user entity
     * @param verificationToken the email verification token
     * @throws RuntimeException if email sending fails
     */
    void sendEmailVerification(User user, String verificationToken);

    /**
     * Send password reset email to user.
     *
     * @param user the user entity
     * @param resetToken the password reset token
     * @throws RuntimeException if email sending fails
     */
    void sendPasswordResetEmail(User user, String resetToken);

    /**
     * Send welcome email to new user.
     *
     * @param user the user entity
     * @throws RuntimeException if email sending fails
     */
    void sendWelcomeEmail(User user);

    /**
     * Send account activation email.
     *
     * @param user the user entity
     * @throws RuntimeException if email sending fails
     */
    void sendAccountActivationEmail(User user);

    /**
     * Send account deactivation email.
     *
     * @param user the user entity
     * @param reason the deactivation reason
     * @throws RuntimeException if email sending fails
     */
    void sendAccountDeactivationEmail(User user, String reason);

    /**
     * Send password changed confirmation email.
     *
     * @param user the user entity
     * @throws RuntimeException if email sending fails
     */
    void sendPasswordChangedEmail(User user);

    /**
     * Send email address changed confirmation email.
     *
     * @param user the user entity
     * @param newEmail the new email address
     * @throws RuntimeException if email sending fails
     */
    void sendEmailChangedNotification(User user, String newEmail);

    /**
     * Send photographer verification approval email.
     *
     * @param user the user entity
     * @throws RuntimeException if email sending fails
     */
    void sendPhotographerVerificationApprovalEmail(User user);

    /**
     * Send photographer verification rejection email.
     *
     * @param user the user entity
     * @param reason the rejection reason
     * @throws RuntimeException if email sending fails
     */
    void sendPhotographerVerificationRejectionEmail(User user, String reason);

    /**
     * Send generic email with template.
     *
     * @param to the recipient email address
     * @param subject the email subject
     * @param templateName the email template name
     * @param templateVariables the template variables
     * @throws RuntimeException if email sending fails
     */
    void sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> templateVariables);

    /**
     * Send simple text email.
     *
     * @param to the recipient email address
     * @param subject the email subject
     * @param text the email body text
     * @throws RuntimeException if email sending fails
     */
    void sendSimpleEmail(String to, String subject, String text);

    /**
     * Send HTML email.
     *
     * @param to the recipient email address
     * @param subject the email subject
     * @param htmlContent the HTML email content
     * @throws RuntimeException if email sending fails
     */
    void sendHtmlEmail(String to, String subject, String htmlContent);

    /**
     * Send email with attachment.
     *
     * @param to the recipient email address
     * @param subject the email subject
     * @param text the email body text
     * @param attachmentPath the file path to attach
     * @throws RuntimeException if email sending fails
     */
    void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath);

    /**
     * Send bulk emails to multiple recipients.
     *
     * @param recipients the list of recipient email addresses
     * @param subject the email subject
     * @param text the email body text
     * @throws RuntimeException if email sending fails
     */
    void sendBulkEmail(String[] recipients, String subject, String text);

    /**
     * Validate email address format.
     *
     * @param email the email address to validate
     * @return true if email format is valid
     */
    boolean isValidEmailFormat(String email);

    /**
     * Generate verification link for email verification.
     *
     * @param verificationToken the verification token
     * @return the verification URL
     */
    String generateVerificationLink(String verificationToken);

    /**
     * Generate password reset link.
     *
     * @param resetToken the password reset token
     * @return the password reset URL
     */
    String generatePasswordResetLink(String resetToken);
}
