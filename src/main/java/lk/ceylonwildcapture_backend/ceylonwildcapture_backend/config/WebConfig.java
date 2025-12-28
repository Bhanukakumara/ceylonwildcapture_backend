package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.DIRECT;

/**
 * Web configuration for Spring Data Web Support.
 * Configures page serialization to use the direct flat structure.
 * This ensures compatibility with the frontend PageResponse interface.
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = DIRECT)
public class WebConfig {
    // Configuration is handled via annotation
}
