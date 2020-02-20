package com.kevvvvyp.springintegrationexample;

import com.kevvvvyp.springintegrationexample.actuators.ReadinessHealthIndicator;
import com.kevvvvyp.springintegrationexample.properties.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;

import java.util.Objects;

@Slf4j
@EnableIntegration
@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@ImportResource("classpath:META-INF/spring/integration/integration.xml")
public class SpringIntegrationExampleApplication {

    public static void main(String[] args) {
        final ApplicationContext app = SpringApplication.run(SpringIntegrationExampleApplication.class, args);

        final ApplicationProperties appProperties = app.getBean(ApplicationProperties.class);
        log.info("Loaded application properties: {}", appProperties.toString());

        final ReadinessHealthIndicator readinessIndicator = Objects.requireNonNull(app.getBean(ReadinessHealthIndicator.class),
                "Could not find bean " + ReadinessHealthIndicator.class.getName());

        readinessIndicator.setReady(true);
    }

}
