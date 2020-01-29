package com.kevvvvyp.springintegrationexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;

@Slf4j
@SpringBootApplication
@EnableIntegration
@ImportResource({"classpath:META-INF/spring/integration/integration.xml"})
public class SpringIntegrationExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationExampleApplication.class, args);
    }

}