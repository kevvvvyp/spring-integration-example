package com.kevvvvyp.springintegrationexample.configuration;

import com.kevvvvyp.springintegrationexample.properties.ApplicationProperties;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RestTemplateConfigurationTest {

    @Spy
    private RestTemplateConfiguration restTemplateConfig;

    @Mock
    private ApplicationProperties mockAppProperties;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRestTemplate() throws Exception {
        // Setup
        final RestTemplate expectedRestTemplate = mock(RestTemplate.class);
        final Duration expectedReadTimeout = Duration.ofSeconds(5);
        final Duration expectedConnectionTimeout = Duration.ofSeconds(5);
        final String expectedOutboundUrl = "http://localhost:8081/helloWorld";

        doReturn(expectedReadTimeout).when(restTemplateConfig).getReadTimeout(any());
        doReturn(expectedConnectionTimeout).when(restTemplateConfig).getConnectionTimeout(any());
        doReturn(expectedOutboundUrl).when(restTemplateConfig).getOutboundUrl(any());

        doReturn(restTemplateBuilder).when(restTemplateBuilder).setReadTimeout(eq(expectedReadTimeout));
        doReturn(restTemplateBuilder).when(restTemplateBuilder).setConnectTimeout(eq(expectedReadTimeout));
        doReturn(restTemplateBuilder).when(restTemplateBuilder).setConnectTimeout(eq(expectedReadTimeout));
        doReturn(restTemplateBuilder).when(restTemplateBuilder).additionalMessageConverters(eq(mappingJackson2HttpMessageConverter));
        doReturn(expectedRestTemplate).when(restTemplateBuilder).build();

        // **** FUT ****
        final RestTemplate actualRestTemplate = restTemplateConfig.restTemplate(mockAppProperties,
                restTemplateBuilder, mappingJackson2HttpMessageConverter);

        // Verification
        verify(restTemplateBuilder, times(1)).setReadTimeout(eq(expectedReadTimeout));
        verify(restTemplateBuilder, times(1)).setConnectTimeout(eq(expectedReadTimeout));
        verify(restTemplateBuilder, times(1)).additionalMessageConverters(eq(mappingJackson2HttpMessageConverter));

        Assert.assertEquals(expectedRestTemplate, actualRestTemplate);
    }

    @Test
    public void testRestTemplate_ThrowsMalformedURLException() {
        // Setup
        final Duration expectedReadTimeout = Duration.ofSeconds(5);
        final Duration expectedConnectionTimeout = Duration.ofSeconds(5);
        final String expectedOutboundUrl = "jargon";

        doReturn(expectedReadTimeout).when(restTemplateConfig).getReadTimeout(any());
        doReturn(expectedConnectionTimeout).when(restTemplateConfig).getConnectionTimeout(any());
        doReturn(expectedOutboundUrl).when(restTemplateConfig).getOutboundUrl(any());

        assertThrows(MalformedURLException.class, () -> {
            restTemplateConfig.restTemplate(mockAppProperties, restTemplateBuilder, mappingJackson2HttpMessageConverter);
        });
    }
}
