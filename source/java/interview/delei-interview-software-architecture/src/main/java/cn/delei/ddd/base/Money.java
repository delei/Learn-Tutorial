package cn.delei.ddd.base;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {
    private BigDecimal amount;
    private Currency currency;
    
    public Money(BigDecimal amount) {
        this.amount = amount;
        this.currency = Currency.getInstance("CNY");
    }

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
