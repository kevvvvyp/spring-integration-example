package com.kevvvvyp.springintegrationexample.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
@Validated
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    @Valid
    private final ApplicationProperties.RetryTemplate retryTemplate = new RetryTemplate();

    @Valid
    private final ApplicationProperties.RestTemplate restTemplate = new RestTemplate();

    @Data
    @NoArgsConstructor
    public static class RetryTemplate {

        @NotNull
        private Duration fixedBackOff;

        @Min(0)
        @Max(Integer.MAX_VALUE)
        private int maxRetries;
    }

    @Data
    @NoArgsConstructor
    public static class RestTemplate {

        @NotNull
        private Duration connectTimeout;

        @NotNull
        private Duration readTimeout;

        @NotNull
        private String outboundUrl;
    }
}



