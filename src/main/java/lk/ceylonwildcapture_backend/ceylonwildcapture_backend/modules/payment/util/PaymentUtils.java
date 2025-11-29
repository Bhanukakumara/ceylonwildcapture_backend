package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Utility class for payment operations.
 */
public final class PaymentUtils {

    private PaymentUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generate unique payment reference.
     *
     * @return unique payment reference
     */
    public static String generatePaymentReference() {
        return "PAY-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Generate transaction ID.
     *
     * @return transaction ID
     */
    public static String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    /**
     * Convert amount to smallest currency unit (e.g., cents).
     *
     * @param amount amount in major units
     * @return amount in smallest units
     */
    public static long toSmallestCurrencyUnit(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(100)).longValue();
    }

    /**
     * Convert amount from smallest currency unit to major unit.
     *
     * @param amount amount in smallest units
     * @return amount in major units
     */
    public static BigDecimal fromSmallestCurrencyUnit(long amount) {
        return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100));
    }

    /**
     * Generate SHA-256 hash.
     *
     * @param input input string
     * @return SHA-256 hash
     */
    public static String generateSHA256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * Calculate platform fee.
     *
     * @param amount transaction amount
     * @param feePercentage fee percentage
     * @return platform fee
     */
    public static BigDecimal calculatePlatformFee(BigDecimal amount, BigDecimal feePercentage) {
        return amount.multiply(feePercentage).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calculate net amount after fee.
     *
     * @param amount total amount
     * @param fee fee amount
     * @return net amount
     */
    public static BigDecimal calculateNetAmount(BigDecimal amount, BigDecimal fee) {
        return amount.subtract(fee);
    }

    /**
     * Mask credit card number.
     *
     * @param cardNumber full card number
     * @return masked card number
     */
    public static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }

    /**
     * Get last four digits of card.
     *
     * @param cardNumber full card number
     * @return last four digits
     */
    public static String getLastFourDigits(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "";
        }
        return cardNumber.substring(cardNumber.length() - 4);
    }

    /**
     * Sanitize payment data for logging.
     *
     * @param data payment data
     * @return sanitized data
     */
    public static String sanitizeForLogging(String data) {
        if (data == null) {
            return "";
        }
        // Remove sensitive information patterns
        return data.replaceAll("\"(card_number|cvv|api_key|secret)\"\\s*:\\s*\"[^\"]+\"",
                              "\"$1\":\"***REDACTED***\"");
    }
}
