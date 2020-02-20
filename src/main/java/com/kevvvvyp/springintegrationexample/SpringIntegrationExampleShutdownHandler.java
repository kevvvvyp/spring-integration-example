package com.kevvvvyp.springintegrationexample;

import com.kevvvvyp.springintegrationexample.actuators.ReadinessHealthIndicator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringIntegrationExampleShutdownHandler implements ApplicationListener<ContextClosedEvent> {

    private final ReadinessHealthIndicator readinessHealthIndicator;

    public SpringIntegrationExampleShutdownHandler(ReadinessHealthIndicator ReadinessHealthIndicator) {
        this.readinessHealthIndicator = ReadinessHealthIndicator;
    }


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("Shutting down...");
        readinessHealthIndicator.setReady(false);
    }
}
