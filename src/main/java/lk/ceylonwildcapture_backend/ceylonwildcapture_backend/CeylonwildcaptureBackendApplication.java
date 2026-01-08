package lk.ceylonwildcapture_backend.ceylonwildcapture_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class CeylonwildcaptureBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CeylonwildcaptureBackendApplication.class, args);
	}

}
