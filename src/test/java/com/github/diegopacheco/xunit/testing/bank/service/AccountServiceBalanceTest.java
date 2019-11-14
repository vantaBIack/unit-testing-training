package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.model.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceBalanceTest {

    AccountService accountService = new AccountService();

    @Test
    public void shouldReturnBalanceSuccessfully(){
        Account account = new Account();
        account.setId("test");
        AccountService.accounts.add(account);
        assertEquals(Optional.of(BigDecimal.ZERO), accountService.returnBalance("test"));
    }

    @Test
    public void shouldReturnNullWhenClientIsNotFound(){
        assertEquals(Optional.empty(), accountService.returnBalance("lala"));
    }
}
