package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

/**
 * Web configuration for Spring Data Web Support.
 * Configures page serialization to use DTOs instead of directly serializing
 * PageImpl instances.
 * This ensures a stable JSON structure for paginated responses.
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class WebConfig {
    // Configuration is handled via annotation
}
