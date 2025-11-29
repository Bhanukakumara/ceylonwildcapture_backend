package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for validating payment gateway signatures.
 */
public final class SignatureValidator {

    private static final String HMAC_SHA256 = "HmacSHA256";

    private SignatureValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Validate HMAC SHA256 signature.
     *
     * @param payload payload data
     * @param signature signature to validate
     * @param secret secret key
     * @return true if valid
     */
    public static boolean validateHmacSha256(String payload, String signature, String secret) {
        try {
            String computedSignature = generateHmacSha256(payload, secret);
            return computedSignature.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generate HMAC SHA256 signature.
     *
     * @param payload payload data
     * @param secret secret key
     * @return generated signature
     * @throws NoSuchAlgorithmException if algorithm not found
     * @throws InvalidKeyException if key is invalid
     */
    public static String generateHmacSha256(String payload, String secret)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Validate signature with timestamp.
     *
     * @param payload payload data
     * @param signature signature to validate
     * @param timestamp timestamp
     * @param secret secret key
     * @return true if valid
     */
    public static boolean validateSignatureWithTimestamp(String payload, String signature,
                                                          long timestamp, String secret) {
        try {
            String signedPayload = timestamp + "." + payload;
            return validateHmacSha256(signedPayload, signature, secret);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate Stripe signature.
     *
     * @param payload webhook payload
     * @param signatureHeader Stripe signature header
     * @param webhookSecret webhook secret
     * @return true if valid
     */
    public static boolean validateStripeSignature(String payload, String signatureHeader, String webhookSecret) {
        if (signatureHeader == null || !signatureHeader.contains("t=") || !signatureHeader.contains("v1=")) {
            return false;
        }

        try {
            String[] parts = signatureHeader.split(",");
            long timestamp = 0;
            String signature = null;

            for (String part : parts) {
                if (part.startsWith("t=")) {
                    timestamp = Long.parseLong(part.substring(2));
                } else if (part.startsWith("v1=")) {
                    signature = part.substring(3);
                }
            }

            if (timestamp == 0 || signature == null) {
                return false;
            }

            return validateSignatureWithTimestamp(payload, signature, timestamp, webhookSecret);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if timestamp is within tolerance.
     *
     * @param timestamp timestamp to check
     * @param toleranceSeconds tolerance in seconds
     * @return true if within tolerance
     */
    public static boolean isTimestampWithinTolerance(long timestamp, long toleranceSeconds) {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        return Math.abs(currentTimestamp - timestamp) <= toleranceSeconds;
    }
}
