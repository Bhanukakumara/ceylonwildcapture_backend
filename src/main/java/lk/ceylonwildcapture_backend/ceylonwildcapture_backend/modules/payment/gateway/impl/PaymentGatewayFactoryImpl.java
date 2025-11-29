package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.gateway.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.gateway.PaymentGatewayClient;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.gateway.PaymentGatewayFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayFactoryImpl implements PaymentGatewayFactory {

    @Override
    public PaymentGatewayClient getGatewayClient(PaymentProvider provider) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isProviderSupported(PaymentProvider provider) {
        // TODO: Implement actual business logic
        return false;
    }
}
