package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.BusinessException;
import com.github.diegopacheco.xunit.testing.bank.Transfers;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;
import java.math.BigDecimal;
import java.util.*;

public class AccountService{

    public static List<Account> accounts = new ArrayList<>();
    public static List<Transfers> transfers = new ArrayList<>();
    private static Account bankAccount;

    static {
        bankAccount = new Account();
        bankAccount.setId("bankId");
    }

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

    private void validateTrasnferInputData(Account origin, Account target, BigDecimal amount){
        validateInputData(origin, amount);
        if(null == target){
            throw new BusinessException("Target account must be provided");
        }
    }

    public Optional<BigDecimal> returnBalance(String accountId){
        return accounts.stream()
                .filter(account -> account.getId().equals(accountId))
                .map(Account::getBalance)
                .findFirst();
    }

    public Transfers transferMoneyBetweenAccounts(Account origin, Account target, BigDecimal amount){
        validateTrasnferInputData(origin, target, amount);
        BigDecimal finalWithdrawValue =
                new BigDecimal(1.05).multiply(amount).setScale(2, BigDecimal.ROUND_FLOOR);
        if(origin.getBalance().compareTo(finalWithdrawValue) >= 0){
            origin.setBalance(origin.getBalance().subtract(finalWithdrawValue));
            target.setBalance(target.getBalance().add(amount));
            bankAccount.setBalance(bankAccount.getBalance().add(finalWithdrawValue.subtract(amount)));

            Transfers transfer = new Transfers();
            transfer.setAmount(amount);
            transfer.setDate(new Date());
            transfer.setFee(finalWithdrawValue.subtract(amount));
            transfer.setId(UUID.randomUUID().toString());
            transfer.setOrigin(origin);
            transfer.setTarget(target);
            transfers.add(transfer);

            Transfers transferToBank = new Transfers();
            transferToBank.setAmount(origin.getBalance().subtract(finalWithdrawValue));
            transferToBank.setDate(new Date());
            transferToBank.setOrigin(origin);
            transferToBank.setTarget(bankAccount);
            transferToBank.setId(UUID.randomUUID().toString());
            transferToBank.setFee(finalWithdrawValue.subtract(amount));
            transfers.add(transferToBank);

            return transfer;
        } else {
            throw new BusinessException("This account does not have enough money for this operation");
        }
    }
}