package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.BusinessException;
import com.github.diegopacheco.xunit.testing.bank.Transfers;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountServiceTransferTest {

    AccountService accountService = new AccountService();

    @Test
    public void shouldTransferMoneySuccessfully(){
        Account origin = new Account();
        origin.setBalance(new BigDecimal(105));
        origin.setId("originId");
        Account target = new Account();
        target.setId("targetId");

        accountService.transferMoneyBetweenAccounts(origin, target, new BigDecimal(100));
        assertEquals(new BigDecimal(0).setScale(2, RoundingMode.FLOOR), origin.getBalance());
        assertEquals(new BigDecimal(100), target.getBalance());
        assertEquals(2, AccountService.transfers.size());
        List<Transfers> transfersList = AccountService.transfers;

        assertEquals("originId", transfersList.get(0).getOrigin().getId());
        assertEquals("targetId", transfersList.get(0).getTarget().getId());
        assertEquals(new BigDecimal(100), transfersList.get(0).getAmount());

        assertEquals(new BigDecimal(5).setScale(2, BigDecimal.ROUND_FLOOR),
                transfersList.get(1).getTarget().getBalance());
        assertEquals("bankId", transfersList.get(1).getTarget().getId());
    }

    @Test
    public void shouldThrowExceptionWhenOriginAccountDoesNothaveEnoughMoney(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            accountService.transferMoneyBetweenAccounts(new Account(), new Account(), new BigDecimal(100));
        });
        assertEquals("This account does not have enough money for this operation",
                runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsNull(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            AccountService accountService = new AccountService();
            accountService.transferMoneyBetweenAccounts(new Account(), new Account(), null);
        });
        assertEquals("Amount must be provided", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsNegative(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            AccountService accountService = new AccountService();
            accountService.transferMoneyBetweenAccounts(new Account(), new Account(), new BigDecimal(-10));
        });
        assertEquals("Amount must be greater than zero", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsZero(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            AccountService accountService = new AccountService();
            accountService.transferMoneyBetweenAccounts(new Account(), new Account(), new BigDecimal(0));
        });
        assertEquals("Amount must be greater than zero", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenOriginAccountIsNull(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            AccountService accountService = new AccountService();
            accountService.transferMoneyBetweenAccounts(null, new Account(), new BigDecimal(10));
        });
        assertEquals("Account must be provided", runtimeException.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTargetAccountIsNull(){
        BusinessException runtimeException = assertThrows(BusinessException.class, () -> {
            AccountService accountService = new AccountService();
            accountService.transferMoneyBetweenAccounts(new Account(), null, new BigDecimal(10));
        });
        assertEquals("Target account must be provided", runtimeException.getMessage());
    }
}
