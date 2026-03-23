package top.huajieyu001;

import lombok.extern.slf4j.Slf4j;
import top.huajieyu001.sync.Account;
import top.huajieyu001.sync.AccountV2;
import top.huajieyu001.sync.TransferAccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author huajieyu
 * @Date 2026/3/23 20:08
 * @Version 1.0
 * @Description TODO
 */
@Slf4j
public class Application {

//    public static void Transfer

    public static void main(String[] args) {
        test3();
    }

    public static void test1(){
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);
        TransferAccountService transferAccountService = new TransferAccountService();
        transferAccountService.transfer(account1, account2, 500);
        System.out.println(account1);
        System.out.println(account2);
    }

    public static void test2(){
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);
        TransferAccountService transferAccountService = new TransferAccountService();
        transferAccountService.transfer(account1, account2, 2000);
        System.out.println(account1);
        System.out.println(account2);
    }

    public static void test3(){
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);

        CountDownLatch countDownLatch = new CountDownLatch(100);
        TransferAccountService transferAccountService = new TransferAccountService();

        List<Thread> threads = new ArrayList<Thread>();
        System.out.println("start");
        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                transferAccountService.transfer(account1, account2, 1);
                countDownLatch.countDown();
            }).start();
        }

        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                transferAccountService.transfer(account2, account1, 1);
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
            System.out.println("end");
            System.out.println(account1);
            System.out.println(account2);
            System.out.println(account1.getBalance() + account2.getBalance());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testV2(){
        AccountV2 account1 = new AccountV2(1000);
        AccountV2 account2 = new AccountV2(1000);

        CountDownLatch countDownLatch = new CountDownLatch(100);
        TransferAccountService transferAccountService = new TransferAccountService();
        TransferAccountService transferAccountService2 = new TransferAccountService();



        List<Thread> threads = new ArrayList<Thread>();
        System.out.println("start");
        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                transferAccountService.transferV2(account1, account2, 1);
                countDownLatch.countDown();
            }).start();
        }

        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                transferAccountService2.transferV2(account2, account1, 1);
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
            System.out.println("end");
            System.out.println(account1);
            System.out.println(account2);
            System.out.println(account1.getBalance() + account2.getBalance());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
