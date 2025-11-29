package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.gateway.impl;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.GatewayPayloadDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.PaymentIntentResponseDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.RefundRequestDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.gateway.PaymentGatewayClient;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayClientImpl implements PaymentGatewayClient {

    @Override
    public PaymentIntentResponseDto createPaymentIntent(GatewayPayloadDto payload) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PaymentIntentResponseDto capturePayment(String paymentId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PaymentIntentResponseDto cancelPayment(String paymentId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PaymentIntentResponseDto processRefund(RefundRequestDto refundRequest) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PaymentIntentResponseDto retrievePayment(String paymentId) {
        // TODO: Implement actual business logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean verifyPayment(String paymentId, String signature) {
        // TODO: Implement actual business logic
        return false;
    }

    @Override
    public PaymentProvider getProvider() {
        // TODO: Implement actual business logic
        return null;
    }

    @Override
    public boolean isAvailable() {
        // TODO: Implement actual business logic
        return false;
    }
}
