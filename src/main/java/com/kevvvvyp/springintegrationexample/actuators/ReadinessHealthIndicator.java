package com.kevvvvyp.springintegrationexample.actuators;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class ReadinessHealthIndicator extends AbstractHealthIndicator {
    private static final String INFO = "Info";
    private static String NOT_READY_MSG = "Not ready yet.";
    private boolean isReady = false;

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        if (isReady)
            builder.up().build();
        else
            builder.down().withDetail(INFO, NOT_READY_MSG);
    }
}
