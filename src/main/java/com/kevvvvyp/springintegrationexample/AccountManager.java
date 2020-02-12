package com.kevvvvyp.springintegrationexample;

import com.kevvvvyp.springintegrationexample.pojo.Account;
import com.kevvvvyp.springintegrationexample.pojo.AccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountManager {

    public AccountRequest validation(@Payload AccountRequest request) {
        return request;
    }

    public Account accountLookup(@Payload AccountRequest accountRequest) {
        return new Account("Joe Bloggs", accountRequest.getNumber(), 300.0);
    }

    public Account processing(@Payload Account account) {
        return account;
    }


    public Message<String> createResponse(Message<Account> message) {
        return MessageBuilder.withPayload("Account created")
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .setHeader(HttpHeaders.ACCEPT_CHARSET, "UTF-8")
                .build();
    }

    public String postReply(Message<String> message) {
        log.info("Reply sent");
        return "test";
    }
}
