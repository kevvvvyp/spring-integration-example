package com.kevvvvyp.springintegrationexample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.integration.http.support.DefaultHttpHeaderMapper;

@Configuration
public class IntegrationConfiguration {

    @Bean
    public DefaultHttpHeaderMapper defaultHeaderMapper() {
        final DefaultHttpHeaderMapper headerMapper = new DefaultHttpHeaderMapper();
        headerMapper.setInboundHeaderNames("*"); //TODO define expected headers
        headerMapper.setOutboundHeaderNames(HttpHeaders.ACCEPT, HttpHeaders.ACCEPT_CHARSET, HttpHeaders.CONTENT_TYPE);
        return headerMapper;
    }
}
