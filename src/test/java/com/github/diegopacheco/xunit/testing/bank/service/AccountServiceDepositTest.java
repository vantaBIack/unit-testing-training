package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.BusinessException;
import com.github.diegopacheco.xunit.testing.bank.model.Account;

import com.github.diegopacheco.xunit.testing.bank.model.User;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountServiceDepositTest {

    @Test
    public void shouldDepositMoneySuccessfully(){
        Account account = new Account();
        AccountService accountService = new AccountService();
        accountService.depositMoney(account, new BigDecimal(10));

        assertEquals(new BigDecimal(10), account.getBalance());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsNull(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            Account account = new Account();
            AccountService accountService = new AccountService();
            accountService.depositMoney(account, null);
        });
        assertEquals("Amount must be provided", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsNegative(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            Account account = new Account();
            AccountService accountService = new AccountService();
            accountService.depositMoney(account, new BigDecimal(-10));
        });
        assertEquals("Amount must be greater than zero", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsZero(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            Account account = new Account();
            AccountService accountService = new AccountService();
            accountService.depositMoney(account, new BigDecimal(0));
        });
        assertEquals("Amount must be greater than zero", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAccountIsNull(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            AccountService accountService = new AccountService();
            accountService.depositMoney(null, new BigDecimal(10));
        });
        assertEquals("Account must be provided", runtimeException.getMessage());
    }
}

