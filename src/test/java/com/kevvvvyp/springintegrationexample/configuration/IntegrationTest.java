package com.kevvvvyp.springintegrationexample.configuration;

import com.kevvvvyp.springintegrationexample.pojo.AccountRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    MessageChannel inboundRequestChannel;

    @Autowired
    QueueChannel queueChannel;

    @Autowired
    ConfigurableApplicationContext configurableApplicationContext;

    @Test
    public void testAccountRequest() { //TODO
        AccountRequest accountRequest = new AccountRequest(12345678L);
        inboundRequestChannel.send(MessageBuilder.withPayload(accountRequest).build());
        final Message<?> outboundRequest = queueChannel.receive(5000L);
        Assertions.assertThat(outboundRequest.getPayload()).isNotNull();
    }

    @TestConfiguration
    static class BridgeConfig {

        @Bean
        @BridgeFrom("outboundRequestChannel")
        public QueueChannel queueChannel() {
            return new QueueChannel();
        }
    }
}
