package top.huajieyu001.sync;

import lombok.ToString;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author huajieyu
 * @Date 2026/3/23 20:08
 * @Version 1.0
 * @Description TODO
 */
@ToString
public class AccountV2 {

    private double balance;

    private ReentrantLock lock = new ReentrantLock();

    public AccountV2(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public void debit(double amount) {
        if(!lock.tryLock()){
            return;
        }
        try {
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }

    public void credit(double amount) {
        if(!lock.tryLock()){
            return;
        }
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }
}
