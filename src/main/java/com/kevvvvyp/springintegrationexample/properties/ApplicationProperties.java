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

    private static final String MISSING_MSG = "Missing property";
    private static final String OUT_OF_BOUNDS = "Property was out of bounds";

    @Valid
    private final ApplicationProperties.RetryTemplate retryTemplate = new RetryTemplate();

    @Valid
    private final ApplicationProperties.RestTemplate restTemplate = new RestTemplate();

    @Data
    @NoArgsConstructor
    public static class RetryTemplate {

        @NotNull(message = MISSING_MSG)
        private Duration fixedBackOff;

        @Min(value = 0, message = OUT_OF_BOUNDS)
        @Max(value = Integer.MAX_VALUE, message = OUT_OF_BOUNDS)
        private int maxRetries;
    }

    @Data
    @NoArgsConstructor
    public static class RestTemplate {

        @NotNull(message = MISSING_MSG)
        private Duration connectTimeout;

        @NotNull(message = MISSING_MSG)
        private Duration readTimeout;

        @NotNull(message = MISSING_MSG)
        private String outboundUrl;
    }
}



