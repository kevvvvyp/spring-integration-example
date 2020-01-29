package com.kevvvvyp.springintegrationexample.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;

@Slf4j
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RetryTemplate retryTemplate() {
        int maxAttempts = 3; //Todo make configurable
        Duration fixedBackOff = Duration.ofSeconds(1); //Todo make configurable

        final FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(fixedBackOff.toMillis());

        final CustomRetryPolicy retryPolicy = new CustomRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        log.info("RetryTemplate configuration: maxAttempts={}, fixedBackOff={}ms", maxAttempts, fixedBackOff.toMillis());

        return retryTemplate;
    }

    @Slf4j
    static class CustomRetryPolicy extends SimpleRetryPolicy {
        @Override
        public void close(RetryContext status) {
            if (status.getRetryCount() == super.getMaxAttempts())
                logFailedRetry(status);
            super.close(status);
        }

        @Override
        public void registerThrowable(RetryContext context, Throwable throwable) {
            logFailedRetry(context);
            super.registerThrowable(context, throwable);
        }

        private void logFailedRetry(RetryContext context) {
            log.error("Error after {} (of {}) retries.", context.getRetryCount(), super.getMaxAttempts());
        }
    }
}
