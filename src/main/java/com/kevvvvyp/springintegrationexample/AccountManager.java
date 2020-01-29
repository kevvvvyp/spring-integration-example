package com.kevvvvyp.springintegrationexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountManager {

    public Account accountLookup(@Payload AccountRequest accountRequest) {
        log.info("Looking up account for request: {}", accountRequest);
        return new Account("Joe Bloggs", accountRequest.getNumber(), 300.0);
    }

    public Account processing(@Payload Account account) {
        log.info("Received account for processing: {}", account);
        return account;
    }
}
