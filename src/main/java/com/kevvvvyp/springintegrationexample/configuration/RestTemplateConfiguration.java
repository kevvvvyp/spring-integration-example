package com.kevvvvyp.springintegrationexample.configuration;

import com.kevvvvyp.springintegrationexample.properties.ApplicationProperties;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Slf4j
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(@NonNull final ApplicationProperties applicationProperties,
                                     @NonNull final RestTemplateBuilder restTemplateBuilder,
                                     @NonNull final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {

        final Duration readTimeout = applicationProperties.getRestTemplate().getReadTimeout();
        final Duration connectTimeout = applicationProperties.getRestTemplate().getConnectTimeout();

        final RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(readTimeout)
                .setConnectTimeout(connectTimeout)
                .additionalMessageConverters(mappingJackson2HttpMessageConverter)
                .build();

        log.info("RestTemplate configuration: readTimeout={}ms, connectTimeout={}ms",
                readTimeout.toMillis(),
                connectTimeout.toMillis());

        return restTemplate;
    }
}
