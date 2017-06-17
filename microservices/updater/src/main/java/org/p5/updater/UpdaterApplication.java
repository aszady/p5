package org.p5.updater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@SpringBootApplication
@EnableDiscoveryClient
public class UpdaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdaterApplication.class, args);
	}

    /**
     * Required to read translator urls from list in application.properties
     */
	@Bean public ConversionService conversionService() {
	    return new DefaultConversionService();
    }
}
