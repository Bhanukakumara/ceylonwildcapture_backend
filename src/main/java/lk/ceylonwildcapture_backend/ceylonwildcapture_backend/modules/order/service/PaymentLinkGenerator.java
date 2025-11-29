package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.order.entity.Order;

import java.util.Map;

/**
 * Service interface for payment link generation.
 * Defines business logic for generating payment checkout links,
 * preparing payment sessions, and handling payment redirects.
 */
public interface PaymentLinkGenerator {

    /**
     * Generate payment link for order.
     *
     * @param orderId the order ID
     * @return the payment checkout URL
     * @throws IllegalArgumentException if order not found
     */
    String generatePaymentLink(Long orderId);

    /**
     * Generate payment link with return URLs.
     *
     * @param orderId the order ID
     * @param successUrl the success redirect URL
     * @param cancelUrl the cancel redirect URL
     * @return the payment checkout URL
     * @throws IllegalArgumentException if order not found
     */
    String generatePaymentLink(Long orderId, String successUrl, String cancelUrl);

    /**
     * Generate payment session for order.
     *
     * @param order the order entity
     * @return the payment session data (DTO placeholder)
     * @throws IllegalArgumentException if order is invalid
     */
    Object generatePaymentSession(Order order);

    /**
     * Generate payment intent for order.
     *
     * @param orderId the order ID
     * @param paymentMethod the payment method
     * @return the payment intent data (DTO placeholder)
     * @throws IllegalArgumentException if order not found
     */
    Object generatePaymentIntent(Long orderId, String paymentMethod);

    /**
     * Generate checkout session with Stripe.
     *
     * @param orderId the order ID
     * @return the Stripe checkout session URL
     * @throws IllegalArgumentException if order not found
     */
    String generateStripeCheckoutSession(Long orderId);

    /**
     * Generate PayPal payment link.
     *
     * @param orderId the order ID
     * @return the PayPal checkout URL
     * @throws IllegalArgumentException if order not found
     */
    String generatePayPalPaymentLink(Long orderId);

    /**
     * Generate payment form data.
     *
     * @param orderId the order ID
     * @return the payment form data (DTO placeholder)
     * @throws IllegalArgumentException if order not found
     */
    Object generatePaymentFormData(Long orderId);

    /**
     * Get payment methods available for order.
     *
     * @param orderId the order ID
     * @return list of available payment methods
     */
    Object getAvailablePaymentMethods(Long orderId);

    /**
     * Validate payment link.
     *
     * @param paymentLink the payment link
     * @return true if payment link is valid
     */
    boolean validatePaymentLink(String paymentLink);

    /**
     * Generate payment redirect URL.
     *
     * @param orderId the order ID
     * @param paymentGateway the payment gateway
     * @return the redirect URL
     */
    String generatePaymentRedirectUrl(Long orderId, String paymentGateway);

    /**
     * Prepare payment metadata.
     *
     * @param order the order entity
     * @return the payment metadata
     */
    Map<String, String> preparePaymentMetadata(Order order);

    /**
     * Generate payment description.
     *
     * @param order the order entity
     * @return the payment description
     */
    String generatePaymentDescription(Order order);

    /**
     * Generate payment reference.
     *
     * @param orderId the order ID
     * @return the payment reference
     */
    String generatePaymentReference(Long orderId);

    /**
     * Get payment expiration time.
     *
     * @param orderId the order ID
     * @return the payment session expiration time
     */
    Object getPaymentExpiration(Long orderId);

    /**
     * Cancel payment session.
     *
     * @param orderId the order ID
     * @param sessionId the payment session ID
     * @throws IllegalArgumentException if session not found
     */
    void cancelPaymentSession(Long orderId, String sessionId);

    /**
     * Verify payment callback.
     *
     * @param callbackData the payment callback data
     * @return verification result
     */
    Object verifyPaymentCallback(Object callbackData);

    /**
     * Handle payment success callback.
     *
     * @param orderId the order ID
     * @param paymentData the payment data
     * @return the updated order
     */
    Object handlePaymentSuccess(Long orderId, Object paymentData);

    /**
     * Handle payment failure callback.
     *
     * @param orderId the order ID
     * @param failureReason the failure reason
     * @return the updated order
     */
    Object handlePaymentFailure(Long orderId, String failureReason);
}
