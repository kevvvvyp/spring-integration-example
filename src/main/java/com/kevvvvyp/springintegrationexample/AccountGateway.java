package com.kevvvvyp.springintegrationexample;

import com.kevvvvyp.springintegrationexample.pojo.Account;
import com.kevvvvyp.springintegrationexample.pojo.AccountRequest;
import org.springframework.integration.annotation.MessagingGateway;

import javax.validation.constraints.NotNull;

@MessagingGateway
public interface AccountGateway {
    Account createAccount(@NotNull AccountRequest accountRequest);
}
