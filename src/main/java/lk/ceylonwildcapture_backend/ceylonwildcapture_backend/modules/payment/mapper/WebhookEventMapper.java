package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.mapper;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.dto.WebhookEventDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.payment.entity.WebhookEvent;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper interface for converting WebhookEvent entities to DTOs.
 */
public interface WebhookEventMapper {

    /**
     * Convert WebhookEvent entity to WebhookEventDto.
     *
     * @param webhookEvent webhook event entity
     * @return webhook event DTO
     */
    WebhookEventDto toWebhookEventDto(WebhookEvent webhookEvent);

    /**
     * Convert list of WebhookEvent entities to list of WebhookEventDto.
     *
     * @param webhookEvents list of webhook event entities
     * @return list of webhook event DTOs
     */
    List<WebhookEventDto> toWebhookEventDtoList(List<WebhookEvent> webhookEvents);

    /**
     * Convert Page of WebhookEvent entities to Page of WebhookEventDto.
     *
     * @param webhookEvents page of webhook event entities
     * @return page of webhook event DTOs
     */
    Page<WebhookEventDto> toWebhookEventDtoPage(Page<WebhookEvent> webhookEvents);
}
