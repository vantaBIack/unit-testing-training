package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.BusinessException;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountServiceWithdrawTest {

    private AccountService accountService = new AccountService();

    @Test
    public void shouldWithdrawMoneyTypeAccountSuccessfully(){
        Account account = new Account();
        account.setBalance(new BigDecimal(100));
        account.setType(AccountType.ACCOUNT);
        accountService.withdrawMoney(account, new BigDecimal(100));
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    public void shouldWithdrawMoneyTypeSavingSuccessfully(){
        Account account = new Account();
        account.setBalance(new BigDecimal(102));
        account.setType(AccountType.SAVING);
        accountService.withdrawMoney(account, new BigDecimal(100));
        assertEquals(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_FLOOR), account.getBalance());
    }

    @Test
    public void shouldThrowExceptionWhenAccountBalanceIsNotEnoughForAccountTransaction(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            Account account = new Account();
            account.setBalance(new BigDecimal(102));
            account.setType(AccountType.ACCOUNT);
            accountService.withdrawMoney(account, new BigDecimal(105));
        });
        assertEquals("This account does not have enough money for this operation", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAccountBalanceIsNotEnoughForSavingTransaction(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            Account account = new Account();
            account.setBalance(new BigDecimal(102));
            account.setType(AccountType.SAVING);
            accountService.withdrawMoney(account, new BigDecimal(105));
        });
        assertEquals("This account does not have enough money for this operation", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsNull(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            Account account = new Account();
            AccountService accountService = new AccountService();
            accountService.withdrawMoney(account, null);
        });
        assertEquals("Amount must be provided", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsNegative(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            Account account = new Account();
            AccountService accountService = new AccountService();
            accountService.withdrawMoney(account, new BigDecimal(-10));
        });
        assertEquals("Amount must be greater than zero", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsZero(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            Account account = new Account();
            AccountService accountService = new AccountService();
            accountService.withdrawMoney(account, new BigDecimal(0));
        });
        assertEquals("Amount must be greater than zero", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAccountIsNull(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            AccountService accountService = new AccountService();
            accountService.withdrawMoney(null, new BigDecimal(10));
        });
        assertEquals("Account must be provided", runtimeException.getMessage());
    }

}



