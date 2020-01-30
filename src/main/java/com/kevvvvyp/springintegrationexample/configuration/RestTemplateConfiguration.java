package com.kevvvvyp.springintegrationexample.configuration;

import com.kevvvvyp.springintegrationexample.properties.ApplicationProperties;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Slf4j
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(@NonNull final ApplicationProperties applicationProperties,
                                     @NonNull final RestTemplateBuilder restTemplateBuilder,
                                     @NonNull final StringHttpMessageConverter stringHttpMessageConverter,
                                     @NonNull final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) throws MalformedURLException {

        final Duration readTimeout = this.getReadTimeout(applicationProperties);
        final Duration connectTimeout = this.getConnectionTimeout(applicationProperties);
        final URL outboundUrl = new URL(this.getOutboundUrl(applicationProperties));

        final RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(readTimeout)
                .setConnectTimeout(connectTimeout)
                .additionalMessageConverters(stringHttpMessageConverter)
                .additionalMessageConverters(mappingJackson2HttpMessageConverter)
                .build();

        log.info("RestTemplate configuration: readTimeout={}ms, connectTimeout={}ms, outboundUrl={}",
                readTimeout.toMillis(),
                connectTimeout.toMillis(),
                outboundUrl.toString());

        return restTemplate;
    }

    Duration getReadTimeout(ApplicationProperties applicationProperties) {
        return applicationProperties.getRestTemplate().getReadTimeout();
    }

    Duration getConnectionTimeout(ApplicationProperties applicationProperties) {
        return applicationProperties.getRestTemplate().getConnectTimeout();
    }

    String getOutboundUrl(ApplicationProperties applicationProperties) {
        return applicationProperties.getRestTemplate().getOutboundUrl();
    }
}
