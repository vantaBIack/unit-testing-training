package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.BusinessException;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountService{

    public static List<Account> accounts = new ArrayList<>();

    public BigDecimal depositMoney(Account target, BigDecimal amount){
        validateInputData(target, amount);
        target.setBalance(target.getBalance().add(amount));
        return target.getBalance();
    }

    public BigDecimal withdrawMoney(Account origin, BigDecimal amount){
        validateInputData(origin, amount);
        BigDecimal finalWithdrawValue;

        if(origin.getType().equals(AccountType.SAVING)) {
            finalWithdrawValue = new BigDecimal(1.02).multiply(amount).setScale(2, BigDecimal.ROUND_FLOOR);
        } else {
            finalWithdrawValue = amount;
        }

        if (origin.getBalance().compareTo(finalWithdrawValue) >= 0){
            origin.setBalance(origin.getBalance().subtract(finalWithdrawValue));
            return origin.getBalance();
        } else {
            throw new BusinessException("This account does not have enough money for this operation");
        }
    }

    private void validateInputData(Account account, BigDecimal amount){
        if (amount == null){
            throw new BusinessException("Amount must be provided");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException("Amount must be greater than zero");
        }
        if (account == null){
            throw new BusinessException("Account must be provided");
        }
    }

    public Optional<BigDecimal> returnBalance(String accountId){
        return accounts.stream()
                .filter(account -> account.getId().equals(accountId))
                .map(Account::getBalance)
                .findFirst();
    }
}