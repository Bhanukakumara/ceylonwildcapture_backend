package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.WebhookEventDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.service.PaymentWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for payment webhook operations.
 */
@RestController
@RequestMapping("/api/v1/payments/webhook")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentWebhookService webhookService;

    /**
     * Handle Stripe webhook.
     *
     * @param payload webhook payload
     * @param signature Stripe signature header
     * @param request HTTP request
     * @return webhook event
     */
    @PostMapping("/stripe")
    public ResponseEntity<WebhookEventDto> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature,
            HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        WebhookEventDto event = webhookService.handleGatewayWebhook(
                PaymentProvider.STRIPE, payload, signature, ipAddress);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(event);
    }

    /**
     * Handle PayPal webhook.
     *
     * @param payload webhook payload
     * @param signature PayPal signature header
     * @param request HTTP request
     * @return webhook event
     */
    @PostMapping("/paypal")
    public ResponseEntity<WebhookEventDto> handlePayPalWebhook(
            @RequestBody String payload,
            @RequestHeader(value = "PAYPAL-TRANSMISSION-SIG", required = false) String signature,
            HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        WebhookEventDto event = webhookService.handleGatewayWebhook(
                PaymentProvider.PAYPAL, payload, signature, ipAddress);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(event);
    }

    /**
     * Handle Razorpay webhook.
     *
     * @param payload webhook payload
     * @param signature Razorpay signature header
     * @param request HTTP request
     * @return webhook event
     */
    @PostMapping("/razorpay")
    public ResponseEntity<WebhookEventDto> handleRazorpayWebhook(
            @RequestBody String payload,
            @RequestHeader(value = "X-Razorpay-Signature", required = false) String signature,
            HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        WebhookEventDto event = webhookService.handleGatewayWebhook(
                PaymentProvider.RAZORPAY, payload, signature, ipAddress);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(event);
    }

    /**
     * Retry failed webhook processing (admin only).
     *
     * @param eventId event ID
     * @return success response
     */
    @PostMapping("/{eventId}/retry")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> retryWebhookProcessing(@PathVariable Long eventId) {
        webhookService.retryWebhookProcessing(eventId);
        return ResponseEntity.ok().build();
    }

    /**
     * Get webhook events by payment ID (admin only).
     *
     * @param paymentId payment ID
     * @param pageable pagination parameters
     * @return page of webhook events
     */
    @GetMapping("/payment/{paymentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<WebhookEventDto>> getWebhookEventsByPaymentId(
            @PathVariable String paymentId,
            Pageable pageable) {
        Page<WebhookEventDto> events = webhookService.getWebhookEventsByPaymentId(paymentId, pageable);
        return ResponseEntity.ok(events);
    }

    /**
     * Get webhook events by provider (admin only).
     *
     * @param provider payment provider
     * @param pageable pagination parameters
     * @return page of webhook events
     */
    @GetMapping("/provider/{provider}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<WebhookEventDto>> getWebhookEventsByProvider(
            @PathVariable PaymentProvider provider,
            Pageable pageable) {
        Page<WebhookEventDto> events = webhookService.getWebhookEventsByProvider(provider, pageable);
        return ResponseEntity.ok(events);
    }

    /**
     * Get failed webhook events (admin only).
     *
     * @param pageable pagination parameters
     * @return page of failed webhook events
     */
    @GetMapping("/failed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<WebhookEventDto>> getFailedWebhookEvents(Pageable pageable) {
        Page<WebhookEventDto> events = webhookService.getFailedWebhookEvents(pageable);
        return ResponseEntity.ok(events);
    }
}
