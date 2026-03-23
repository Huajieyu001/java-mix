package top.huajieyu001.sync;

import lombok.ToString;

/**
 * @Author huajieyu
 * @Date 2026/3/23 20:08
 * @Version 1.0
 * @Description TODO
 */
@ToString
public class Account {

    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void debit(double amount) {
        balance -= amount;
    }

    public void credit(double amount) {
        balance += amount;
    }
}
