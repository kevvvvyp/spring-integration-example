package com.kevvvvyp.springintegrationexample.configuration;

import com.kevvvvyp.springintegrationexample.properties.ApplicationProperties;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RetryTemplateConfigurationTest {

    @Spy
    private RetryTemplateConfiguration retryTemplateConfig;

    @Mock
    private ApplicationProperties mockAppProperties;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetryTemplate() {
        // Setup
        doReturn(3).when(retryTemplateConfig).getMaxRetries(Mockito.eq(mockAppProperties));
        doReturn(Duration.ofSeconds(1)).when(retryTemplateConfig).getFixedBackOff(Mockito.eq(mockAppProperties));

        // **** FUT ****
        final RetryTemplate actualRetryTemplate = retryTemplateConfig.retryTemplate(mockAppProperties);

        // Verification
        assertNotEquals(null, actualRetryTemplate);
        verify(retryTemplateConfig, times(1)).getMaxRetries(eq(mockAppProperties));
        verify(retryTemplateConfig, times(1)).getFixedBackOff(eq(mockAppProperties));
    }

    @Test
    @Ignore
    public void testCustomRetryPolicy() {
        //TODO implement
    }
}
