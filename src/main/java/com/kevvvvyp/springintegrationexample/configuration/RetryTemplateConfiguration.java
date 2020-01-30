package com.kevvvvyp.springintegrationexample.configuration;

import com.kevvvvyp.springintegrationexample.properties.ApplicationProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessagingException;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.Duration;

@Slf4j
@Configuration
public class RetryTemplateConfiguration {

    @Bean
    public RetryTemplate retryTemplate(@NonNull final ApplicationProperties applicationProperties) {
        //Todo validation
        final int maxAttempts = getMaxRetries(applicationProperties);
        final Duration fixedBackOff = getFixedBackOff(applicationProperties);

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

    int getMaxRetries(ApplicationProperties applicationProperties) {
        return applicationProperties.getRetryTemplate().getMaxRetries();
    }

    Duration getFixedBackOff(ApplicationProperties applicationProperties) {
        return applicationProperties.getRetryTemplate().getFixedBackOff();
    }

    @Data
    @Slf4j
    @EqualsAndHashCode(callSuper = true)
    static class CustomRetryPolicy extends SimpleRetryPolicy {
        @Override
        public void close(RetryContext status) {
            if (status.getRetryCount() == super.getMaxAttempts())
                logFailedRetry(status, status.getLastThrowable());
            super.close(status);
        }

        @Override
        public void registerThrowable(RetryContext context, Throwable throwable) {
            logFailedRetry(context, throwable);
            super.registerThrowable(context, throwable);
        }

        private void logFailedRetry(RetryContext context, Throwable throwable) {
            String statusCodeError = "";
            if (throwable instanceof MessagingException) {
                MessagingException messagingException = (MessagingException) throwable;
                Throwable rootCause = messagingException.getRootCause();
                if (rootCause instanceof HttpStatusCodeException)
                    statusCodeError = "HttpStatus: " + rootCause.getMessage();
            }

            log.error("Error after {} (of {}) retries. {}", context.getRetryCount(), super.getMaxAttempts(), statusCodeError);
        }
    }
}
