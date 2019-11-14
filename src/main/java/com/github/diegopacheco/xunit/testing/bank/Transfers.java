package com.github.diegopacheco.xunit.testing.bank;

import com.github.diegopacheco.xunit.testing.bank.model.Account;

import java.math.BigDecimal;
import java.util.Date;

public class Transfers {

    private Account origin;
    private Account target;
    private BigDecimal amount;
    private Date date;
    private String id;
    private BigDecimal fee;

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    public Account getTarget() {
        return target;
    }

    public void setTarget(Account target) {
        this.target = target;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
