package top.huajieyu001.sync;

public class TransferAccountService {

    private static final Object GLOBAL_LOCK = new Object();

    public boolean transfer(Account from, Account to, double amount) {
        if (from == null || to == null || amount <= 0 || from == to) {
            return false;
        }
        int hash1 = System.identityHashCode(from);
        int hash2 = System.identityHashCode(to);
        if (hash1 == hash2) {
            // hash相等，使用全局锁
            synchronized (GLOBAL_LOCK) {
                synchronized (from) {
                    synchronized (to) {
                        return doTransfer(from, to, amount);
                    }
                }
            }
        }
        Account lock1 = hash1 < hash2 ? from : to;
        Account lock2 = hash1 < hash2 ? to : from;
        // hash小的先上锁
        synchronized (lock1) {
            synchronized (lock2) {
                return doTransfer(from, to, amount);
            }
        }
    }

    private boolean doTransfer(Account from, Account to, double amount) {
        if (from.getBalance() < amount) {
            return false;
        }
        from.debit(amount);
        to.credit(amount);
        return true;
    }
}
