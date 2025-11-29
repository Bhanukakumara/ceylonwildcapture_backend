package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.repository;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity.WebhookEvent;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.PaymentProvider;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.enums.WebhookEventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for WebhookEvent entity.
 */
@Repository
public interface WebhookEventRepository extends JpaRepository<WebhookEvent, Long> {

    /**
     * Find webhook event by event ID.
     *
     * @param eventId event ID
     * @return optional webhook event
     */
    Optional<WebhookEvent> findByEventId(String eventId);

    /**
     * Find webhook events by provider.
     *
     * @param provider payment provider
     * @param pageable pagination parameters
     * @return page of webhook events
     */
    Page<WebhookEvent> findByProvider(PaymentProvider provider, Pageable pageable);

    /**
     * Find webhook events by event type.
     *
     * @param eventType event type
     * @param pageable pagination parameters
     * @return page of webhook events
     */
    Page<WebhookEvent> findByEventType(WebhookEventType eventType, Pageable pageable);

    /**
     * Find webhook events by payment ID.
     *
     * @param paymentId payment ID
     * @param pageable pagination parameters
     * @return page of webhook events
     */
    Page<WebhookEvent> findByPaymentId(String paymentId, Pageable pageable);

    /**
     * Find unprocessed webhook events.
     *
     * @param processed processed flag
     * @param pageable pagination parameters
     * @return page of unprocessed webhook events
     */
    Page<WebhookEvent> findByProcessed(Boolean processed, Pageable pageable);

    /**
     * Find unprocessed events by provider.
     *
     * @param provider payment provider
     * @param processed processed flag
     * @return list of unprocessed events
     */
    List<WebhookEvent> findByProviderAndProcessedOrderByCreatedAtAsc(PaymentProvider provider, Boolean processed);

    /**
     * Find webhook events by date range.
     *
     * @param startDate start date
     * @param endDate end date
     * @param pageable pagination parameters
     * @return page of webhook events
     */
    Page<WebhookEvent> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find failed webhook events (with processing errors).
     *
     * @param pageable pagination parameters
     * @return page of failed webhook events
     */
    @Query("SELECT w FROM WebhookEvent w WHERE w.processingError IS NOT NULL ORDER BY w.createdAt DESC")
    Page<WebhookEvent> findFailedWebhookEvents(Pageable pageable);

    /**
     * Find webhook events by retry count above threshold.
     *
     * @param retryCount retry count threshold
     * @param pageable pagination parameters
     * @return page of webhook events
     */
    @Query("SELECT w FROM WebhookEvent w WHERE w.retryCount >= :retryCount ORDER BY w.createdAt DESC")
    Page<WebhookEvent> findByRetryCountGreaterThanEqual(@Param("retryCount") Integer retryCount, Pageable pageable);

    /**
     * Check if event ID exists.
     *
     * @param eventId event ID
     * @return true if exists
     */
    boolean existsByEventId(String eventId);

    /**
     * Count unprocessed events.
     *
     * @param processed processed flag
     * @return unprocessed event count
     */
    long countByProcessed(Boolean processed);

    /**
     * Count events by provider.
     *
     * @param provider payment provider
     * @return event count
     */
    long countByProvider(PaymentProvider provider);

    /**
     * Delete old processed webhook events.
     *
     * @param processed processed flag
     * @param olderThan cutoff date
     * @return number of deleted events
     */
    long deleteByProcessedAndCreatedAtBefore(Boolean processed, LocalDateTime olderThan);
}
