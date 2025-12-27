package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Implementation of EmailService using JavaMailSender.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend-url:http://localhost:5173}")
    private String frontendUrl;

    @Override
    public void sendEmailVerification(User user, String verificationToken) {
        String verificationLink = generateVerificationLink(verificationToken);
        String subject = "Verify Your Email - Ceylon Wild Capture";
        
        String htmlContent = buildEmailVerificationTemplate(user.getFirstName(), verificationLink);
        
        try {
            sendHtmlEmail(user.getEmail(), subject, htmlContent);
            log.info("Verification email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send verification email to: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    @Override
    public void sendPasswordResetEmail(User user, String resetToken) {
        String resetLink = generatePasswordResetLink(resetToken);
        String subject = "Reset Your Password - Ceylon Wild Capture";
        
        String htmlContent = buildPasswordResetTemplate(user.getFirstName(), resetLink);
        
        try {
            sendHtmlEmail(user.getEmail(), subject, htmlContent);
            log.info("Password reset email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    @Override
    public void sendWelcomeEmail(User user) {
        String subject = "Welcome to Ceylon Wild Capture!";
        String text = String.format("Hello %s,\\n\\nWelcome to Ceylon Wild Capture! We're excited to have you join our community of wildlife photography enthusiasts.\\n\\nBest regards,\\nCeylon Wild Capture Team", 
            user.getFirstName());
        
        sendSimpleEmail(user.getEmail(), subject, text);
    }

    @Override
    public void sendAccountActivationEmail(User user) {
        String subject = "Account Activated - Ceylon Wild Capture";
        String text = String.format("Hello %s,\\n\\nYour account has been activated successfully!\\n\\nBest regards,\\nCeylon Wild Capture Team", 
            user.getFirstName());
        
        sendSimpleEmail(user.getEmail(), subject, text);
    }

    @Override
    public void sendAccountDeactivationEmail(User user, String reason) {
        String subject = "Account Deactivated - Ceylon Wild Capture";
        String text = String.format("Hello %s,\\n\\nYour account has been deactivated.\\nReason: %s\\n\\nBest regards,\\nCeylon Wild Capture Team", 
            user.getFirstName(), reason);
        
        sendSimpleEmail(user.getEmail(), subject, text);
    }

    @Override
    public void sendPasswordChangedEmail(User user) {
        String subject = "Password Changed - Ceylon Wild Capture";
        String text = String.format("Hello %s,\\n\\nYour password has been changed successfully. If you did not make this change, please contact us immediately.\\n\\nBest regards,\\nCeylon Wild Capture Team", 
            user.getFirstName());
        
        sendSimpleEmail(user.getEmail(), subject, text);
    }

    @Override
    public void sendEmailChangedNotification(User user, String newEmail) {
        String subject = "Email Address Changed - Ceylon Wild Capture";
        String text = String.format("Hello %s,\\n\\nYour email address has been changed to: %s\\n\\nBest regards,\\nCeylon Wild Capture Team", 
            user.getFirstName(), newEmail);
        
        sendSimpleEmail(user.getEmail(), subject, text);
    }

    @Override
    public void sendPhotographerVerificationApprovalEmail(User user) {
        String subject = "Photographer Verification Approved - Ceylon Wild Capture";
        String text = String.format("Hello %s,\\n\\nCongratulations! Your photographer profile has been verified. You can now start uploading and selling your wildlife photography.\\n\\nBest regards,\\nCeylon Wild Capture Team", 
            user.getFirstName());
        
        sendSimpleEmail(user.getEmail(), subject, text);
    }

    @Override
    public void sendPhotographerVerificationRejectionEmail(User user, String reason) {
        String subject = "Photographer Verification Status - Ceylon Wild Capture";
        String text = String.format("Hello %s,\\n\\nWe regret to inform you that your photographer verification was not approved.\\nReason: %s\\n\\nYou can resubmit your application after addressing the issues.\\n\\nBest regards,\\nCeylon Wild Capture Team", 
            user.getFirstName(), reason);
        
        sendSimpleEmail(user.getEmail(), subject, text);
    }

    @Override
    public void sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> templateVariables) {
        // Template email functionality can be implemented later with Thymeleaf or similar
        log.warn("Template email not yet implemented. Sending simple email instead.");
        sendSimpleEmail(to, subject, "Email content");
    }

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            
            mailSender.send(message);
            log.info("Simple email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send simple email to: {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            log.info("HTML email sent to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send HTML email to: {}", to, e);
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath) {
        // Attachment functionality can be implemented later if needed
        log.warn("Email with attachment not yet implemented");
        sendSimpleEmail(to, subject, text);
    }

    @Override
    public void sendBulkEmail(String[] recipients, String subject, String text) {
        for (String recipient : recipients) {
            sendSimpleEmail(recipient, subject, text);
        }
    }

    @Override
    public boolean isValidEmailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    @Override
    public String generateVerificationLink(String verificationToken) {
        return frontendUrl + "/verify-email?token=" + verificationToken;
    }

    @Override
    public String generatePasswordResetLink(String resetToken) {
        return frontendUrl + "/reset-password?token=" + resetToken;
    }

    /**
     * Build HTML template for email verification.
     */
    private String buildEmailVerificationTemplate(String firstName, String verificationLink) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head><meta charset='UTF-8'></head>" +
                "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
                "<h2 style='color: #10b981;'>Verify Your Email Address</h2>" +
                "<p>Hello " + firstName + ",</p>" +
                "<p>Thank you for registering with Ceylon Wild Capture! Please verify your email address by clicking the button below:</p>" +
                "<div style='text-align: center; margin: 30px 0;'>" +
                "<a href='" + verificationLink + "' style='background-color: #10b981; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; display: inline-block;'>Verify Email</a>" +
                "</div>" +
                "<p>Or copy and paste this link into your browser:</p>" +
                "<p style='word-break: break-all; color: #666;'>" + verificationLink + "</p>" +
                "<p style='color: #666; font-size: 14px; margin-top: 30px;'>This link will expire in 24 hours.</p>" +
                "<p style='color: #666; font-size: 14px;'>If you didn't create an account, please ignore this email.</p>" +
                "<hr style='border: none; border-top: 1px solid #eee; margin: 30px 0;'>" +
                "<p style='color: #999; font-size: 12px;'>Ceylon Wild Capture - Discover and share the wild beauty of Sri Lanka</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Build HTML template for password reset.
     */
    private String buildPasswordResetTemplate(String firstName, String resetLink) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head><meta charset='UTF-8'></head>" +
                "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
                "<h2 style='color: #10b981;'>Reset Your Password</h2>" +
                "<p>Hello " + firstName + ",</p>" +
                "<p>We received a request to reset your password. Click the button below to reset it:</p>" +
                "<div style='text-align: center; margin: 30px 0;'>" +
                "<a href='" + resetLink + "' style='background-color: #10b981; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; display: inline-block;'>Reset Password</a>" +
                "</div>" +
                "<p>Or copy and paste this link into your browser:</p>" +
                "<p style='word-break: break-all; color: #666;'>" + resetLink + "</p>" +
                "<p style='color: #666; font-size: 14px; margin-top: 30px;'>This link will expire in 1 hour.</p>" +
                "<p style='color: #666; font-size: 14px;'>If you didn't request a password reset, please ignore this email.</p>" +
                "<hr style='border: none; border-top: 1px solid #eee; margin: 30px 0;'>" +
                "<p style='color: #999; font-size: 12px;'>Ceylon Wild Capture - Discover and share the wild beauty of Sri Lanka</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
