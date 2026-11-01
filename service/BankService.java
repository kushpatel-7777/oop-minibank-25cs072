
package service;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import exception.BankException;
import exception.AccountNotFoundException;
import exception.InvalidAmountException;
import exception.InsufficientFundsException;
import util.Validator;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class BankService {

    private Repository<Account> repository;
    private AccountStore accountStore;
    private TransactionProcessor transactionProcessor;

    public BankService(
            Repository<Account> repository,
            AccountStore accountStore,
            TransactionProcessor transactionProcessor) {

        this.repository = repository;
        this.accountStore = accountStore;
        this.transactionProcessor = transactionProcessor;
    }

    public Account openAccount(
            String type,
            String ownerName,
            String email,
            String mobile,
            long balance,
            long accountLimit) throws InvalidAmountException {

        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty.");
        }

        if (!Validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        if (!Validator.isValidMobile(mobile)) {
            throw new IllegalArgumentException("Invalid mobile number.");
        }

        if (balance < 0) {
            throw new InvalidAmountException("Initial balance cannot be negative.");
        }

        Account account;

        switch (type.toUpperCase()) {
            case "SAVINGS":
                account = new SavingsAccount(ownerName, balance, accountLimit);
                break;

            case "CURRENT":
                account = new CurrentAccount(ownerName, balance, accountLimit);
                break;

            case "FIXED":
                account = new FixedDepositAccount(ownerName, balance);
                break;

            default:
                throw new IllegalArgumentException("Invalid account type.");
        }

        repository.save(account);
        accountStore.add(account);
        transactionProcessor.addAccount(account);

        recordTransaction(
                "OPEN " + account.getAccountNumber() + " " + balance
        );

        return account;
    }

    public void deposit(String accountNumber, long amount)
            throws AccountNotFoundException, InvalidAmountException {

        Account account = findAccount(accountNumber);

        account.deposit(amount);

        recordTransaction(
                "DEPOSIT " + accountNumber + " " + amount
        );

        System.out.println("Deposit successful!");
    }

    public void withdraw(String accountNumber, long amount)
            throws BankException {

        Account account = findAccount(accountNumber);

        account.withdraw(amount);

        recordTransaction(
                "WITHDRAW " + accountNumber + " " + amount
        );

        System.out.println("Withdrawal successful!");
    }

    public void transfer(String fromAccount, String toAccount, long amount)
            throws BankException {

        Account source = findAccount(fromAccount);
        Account destination = findAccount(toAccount);

        source.transfer(destination, amount);

        recordTransaction(
                "TRANSFER " + fromAccount + " " + amount
        );
    }

    public String statement(String accountNumber)
            throws AccountNotFoundException {

        Account account = findAccount(accountNumber);

        StringBuilder result = new StringBuilder();

        result.append("\n========== ACCOUNT STATEMENT ==========\n");
        result.append(account).append("\n");
        result.append("\nTransaction History:\n");

        for (String transaction : accountStore.getTransactionHistory()) {
            if (transaction.contains(accountNumber)) {
                result.append(transaction).append("\n");
            }
        }

        return result.toString();
    }

    public ArrayList<Account> listAccounts() {
        return accountStore.sortedByBalance();
    }

    public void processBatchDeposits(
            String accountNumber,
            long amount,
            int count) throws Exception {

        Account account = findAccount(accountNumber);

        if (amount <= 0 || count <= 0) {
            throw new InvalidAmountException(
                    "Amount and transaction count must be greater than zero."
            );
        }

        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            transactionProcessor.submit(() -> {
                try {
                    account.deposit(amount);

                    synchronized (this) {
                        recordTransaction(
                                "DEPOSIT " + accountNumber + " " + amount
                        );
                    }

                } catch (InvalidAmountException e) {
                    System.out.println(e.getMessage());

                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        System.out.println(count + " batch deposits completed.");
    }

    private Account findAccount(String accountNumber)
            throws AccountNotFoundException {

        Account account = repository.findById(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(
                    "Account not found: " + accountNumber
            );
        }

        return account;
    }

    private synchronized void recordTransaction(String transaction) {
        accountStore.addTransaction(transaction);
    }
}
