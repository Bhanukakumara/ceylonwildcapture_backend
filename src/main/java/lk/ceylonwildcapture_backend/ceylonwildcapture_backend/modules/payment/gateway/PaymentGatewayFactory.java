package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.gateway;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;

/**
 * Factory interface for creating payment gateway clients.
 */
public interface PaymentGatewayFactory {

    /**
     * Get payment gateway client for provider.
     *
     * @param provider payment provider
     * @return payment gateway client
     */
    PaymentGatewayClient getGatewayClient(PaymentProvider provider);

    /**
     * Check if provider is supported.
     *
     * @param provider payment provider
     * @return true if supported
     */
    boolean isProviderSupported(PaymentProvider provider);
}
