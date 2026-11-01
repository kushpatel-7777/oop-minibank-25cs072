
package service;

import model.Account;
import exception.BankException;
import java.util.concurrent.CountDownLatch;

public class DeadlockDemo {

    public static void demonstrateDeadlock(
            Account accountA, Account accountB) {

        System.out.println("\n========== DEADLOCK DEMONSTRATION ==========");

        CountDownLatch locksAcquired = new CountDownLatch(2);

        Thread thread1 = new Thread(() -> {
            synchronized (accountA) {
                System.out.println("Thread 1 locked Account A.");

                locksAcquired.countDown();

                try {
                    locksAcquired.await();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println("Thread 1 waiting for Account B.");

                synchronized (accountB) {
                    System.out.println("Thread 1 acquired Account B.");
                }
            }
        }, "Deadlock-Thread-1");

        Thread thread2 = new Thread(() -> {
            synchronized (accountB) {
                System.out.println("Thread 2 locked Account B.");

                locksAcquired.countDown();

                try {
                    locksAcquired.await();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println("Thread 2 waiting for Account A.");

                synchronized (accountA) {
                    System.out.println("Thread 2 acquired Account A.");
                }
            }
        }, "Deadlock-Thread-2");

        thread1.setDaemon(true);
        thread2.setDaemon(true);

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(1000);

            if (thread1.isAlive() && thread2.isAlive()) {
                System.out.println("Deadlock detected!");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void transferSafely(
            Account from, Account to, long amount) throws BankException {

        if (from == null || to == null) {
            throw new BankException("Account not found.");
        }

        Account first;
        Account second;

        if (from.getAccountNumber().compareTo(
                to.getAccountNumber()) < 0) {
            first = from;
            second = to;
        } else {
            first = to;
            second = from;
        }

        synchronized (first) {
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);

                System.out.println("Safe transfer successful!");
                System.out.println("Transferred Rs. " + amount);
            }
        }
    }
}
