package com.github.diegopacheco.xunit.testing.bank.model;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;

import java.math.BigDecimal;


public class Account{

    private User user;
    private BigDecimal balance = BigDecimal.ZERO;
    private AccountType type;
    private String id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}