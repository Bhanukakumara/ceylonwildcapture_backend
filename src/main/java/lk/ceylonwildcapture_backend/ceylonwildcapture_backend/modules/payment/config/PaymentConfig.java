package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.config;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;

/**
 * Interface for payment gateway configuration.
 */
public interface PaymentConfig {

    /**
     * Get API key for provider.
     *
     * @param provider payment provider
     * @return API key
     */
    String getApiKey(PaymentProvider provider);

    /**
     * Get API secret for provider.
     *
     * @param provider payment provider
     * @return API secret
     */
    String getApiSecret(PaymentProvider provider);

    /**
     * Get webhook secret for provider.
     *
     * @param provider payment provider
     * @return webhook secret
     */
    String getWebhookSecret(PaymentProvider provider);

    /**
     * Get base URL for provider.
     *
     * @param provider payment provider
     * @return base URL
     */
    String getBaseUrl(PaymentProvider provider);

    /**
     * Get success redirect URL.
     *
     * @return success redirect URL
     */
    String getSuccessRedirectUrl();

    /**
     * Get failure redirect URL.
     *
     * @return failure redirect URL
     */
    String getFailureRedirectUrl();

    /**
     * Get cancel redirect URL.
     *
     * @return cancel redirect URL
     */
    String getCancelRedirectUrl();

    /**
     * Get default currency.
     *
     * @return default currency code
     */
    String getDefaultCurrency();

    /**
     * Get payment timeout in minutes.
     *
     * @return timeout in minutes
     */
    int getPaymentTimeoutMinutes();

    /**
     * Check if provider is enabled.
     *
     * @param provider payment provider
     * @return true if enabled
     */
    boolean isProviderEnabled(PaymentProvider provider);
}
