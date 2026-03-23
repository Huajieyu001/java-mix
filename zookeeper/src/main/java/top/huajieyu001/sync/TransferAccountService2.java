package top.huajieyu001.sync;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author huajieyu
 * @Date 2026/3/23 20:10
 * @Version 1.0
 * @Description TODO
 */
public class TransferAccountService2 {

    private ReentrantLock lock = new ReentrantLock();

    public void transfer(Account from, Account to, double amount) {
        if (from == null || to == null) {
            return;
        }
        if(amount < 0){
            return;
        }
        if(from == to){
            return;
        }
        if (from.getBalance() < amount) {
            return;
        }
        int hash1 = System.identityHashCode(from);
        int hash2 = System.identityHashCode(to);
        Account lock1 = from, lock2 = to;
        if(hash1 > hash2) {
            lock1 = to;
            lock2 = from;
        }
        // hash小的先上锁
        synchronized (lock1) {
            if (from.getBalance() < amount) {
                return;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                from.debit(amount);
                to.credit(amount);
            }
        }
    }

    public void transferV1(Account from, Account to, int amount) {
        if (from.getBalance() < amount) {
            return;
        }
        synchronized (from) {
            if (from.getBalance() < amount) {
                return;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (to) {
                from.debit(amount);
                to.credit(amount);
            }
        }
    }

    public void transferV2(AccountV2 from, AccountV2 to, int amount) {
        if (from.getBalance() < amount) {
            return;
        }
        if (!lock.tryLock()) {
            return;
        }
        try {
            if (from.getBalance() < amount) {
                return;
            }
            from.debit(amount);
            to.credit(amount);
        } finally {
            lock.unlock();
        }
    }
}
