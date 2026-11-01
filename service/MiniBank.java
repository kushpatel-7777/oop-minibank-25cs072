
package service;

import java.util.Scanner;

import model.*;
import util.AnnotationValidator;
import util.CommandParser;
import util.StatementFormatter;
import util.Validator;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import service.Repository;

import static util.Validator.isValidMobile;

public class MiniBank {

    public static void main(String[] args) {

        BankInfo bank = new BankInfo("MiniBank", "Changa Branch");

        System.out.println("================================");
        System.out.println("       " + bank.name());
        System.out.println("       " + bank.branch());
        System.out.println("================================");

        Customer.Address address = new Customer.Address(
                "Hostel Block A", "Changa", "388421"
        );

        Customer customer = new Customer(
                "Kush Patel", "kush@gmail.com", "9876543210", address
        );

        System.out.println("\n========== CUSTOMER DETAILS ==========");
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());

        Account[] accounts = {
                new SavingsAccount("Kush Patel", 5000, 1000),
                new CurrentAccount("Rahul Shah", 3000, 2000),
                new FixedDepositAccount("Aarav Patel", 10000)
        };

        System.out.println("\n========== ANNOTATION VALIDATION ==========");

        Account invalidAccount = new SavingsAccount(
                "Test User", -5000, 1000
        );

        String[] errors = AnnotationValidator.validate(invalidAccount);

        for (String error : errors) {
            System.out.println("Validation Error: " + error);
        }

        Customer invalidCustomer = new Customer(
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789",
                "test@gmail.com",
                "9876543210"
        );

        String[] customerErrors =
                AnnotationValidator.validate(invalidCustomer);

        for (String error : customerErrors) {
            System.out.println("Customer Validation Error: " + error);
        }

        System.out.println("\n========== EXCEPTION HANDLING ==========");

        try {
            accounts[0].deposit(2000);
            accounts[0].withdraw(1000);

            accounts[1].deposit(1500);
            accounts[1].withdraw(500);

            accounts[2].deposit(2000);

        } catch (InsufficientFundsException e) {
            System.out.println("Bank Error: " + e.getMessage());
            System.out.println("Shortfall: Rs. " + e.getShortfall());

        } catch (InvalidAmountException e) {
            System.out.println("Invalid Amount: " + e.getMessage());

        } finally {
            System.out.println("Banking operations completed.");
        }

        System.out.println("\n========== INSUFFICIENT FUNDS TEST ==========");

        try {
            accounts[0].withdraw(100000);

        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal Failed: " + e.getMessage());
            System.out.println("Shortfall: Rs. " + e.getShortfall());

        } catch (InvalidAmountException e) {
            System.out.println("Invalid Amount: " + e.getMessage());
        }

        System.out.println("\n========== TRANSFER TEST ==========");

        try {
            accounts[0].transfer(accounts[1], 500);
        } catch (BankException e) {
            System.out.println("Transfer Failed: " + e.getMessage());
        } finally {
            System.out.println("Transfer test completed.");
        }

        System.out.println("\n========== TRY WITH RESOURCES ==========");

        try (BankSession session = new BankSession()) {
            System.out.println("Performing banking operations...");
        } catch (Exception e) {
            System.out.println("Session Error: " + e.getMessage());
        }

        System.out.println("\n========== ACCOUNT DETAILS ==========");

        for (Account account : accounts) {
            System.out.println(account);
        }

        System.out.println("\n========== ACCOUNT STORE TEST ==========");

        AccountStore accountStore = new AccountStore();

        for (Account account : accounts) {
            accountStore.add(account);
        }

        accountStore.addTransaction("DEPOSIT AC0001 2000");
        accountStore.addTransaction("WITHDRAW AC0002 500");
        accountStore.addTransaction("TRANSFER AC0001 AC0002 500");

        System.out.println("Account retrieved by account number:");
        System.out.println(accountStore.get(accounts[0].getAccountNumber()));

        System.out.println("\nAll accounts sorted by balance (lowest to highest):");
        for (Account account : accountStore.sortedByBalance()) {
            System.out.println(account);
        }

        System.out.println("\nTransaction History:");
        for (String transaction : accountStore.getTransactionHistory()) {
            System.out.println(transaction);
        }

        System.out.println("\n========== GENERIC REPOSITORY TEST ==========");

Repository<Account> accountRepository = new Repository<>();

accountRepository.saveAll(accounts);

System.out.println("Accounts saved successfully!");

Account foundAccount = accountRepository.findById(
    accounts[0].getAccountNumber()
);

System.out.println("\nAccount fetched by ID:");
System.out.println(foundAccount);

System.out.println("\nAll accounts in Repository:");

for (Account account : accountRepository.findAll()) {
    System.out.println(account);
}

System.out.println("\nTesting delete operation:");

boolean deleted = accountRepository.delete(
    accounts[2].getAccountNumber()
);

System.out.println("Account deleted: " + deleted);

System.out.println("\nTesting bounded generic method:");

Integer[] numbers = {10, 20, 30, 40, 50};

System.out.println("Average: " + Repository.average(numbers));

        System.out.println("\n========== POLYMORPHISM TEST ==========");

        for (Account account : accounts) {
            System.out.println("Account: " + account.getAccountNumber());
            System.out.println("Owner: " + account.getOwnerName());
            System.out.println("Interest Rate: " + account.interestRate() + "%");
            System.out.println("Balance: Rs. " + account.getBalance());
            System.out.println("--------------------------------");
        }

        System.out.println("\n========== INTEREST BEARING TEST ==========");

        for (Account account : accounts) {
            System.out.println("Yearly Interest: Rs. " + account.yearlyInterest());
        }

        System.out.println("\n========== PREMIUM ACCOUNT TEST ==========");

        if (accounts[0] instanceof Premium) {
            System.out.println("Savings Account is a Premium Account.");
        }

        System.out.println("\n========== WITHDRAW RULE TEST ==========");

        WithdrawRule anonymousRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return account.canWithdraw(amount);
            }
        };

        WithdrawRule lambdaRule = (account, amount) ->
                account.canWithdraw(amount);

        System.out.println("Anonymous Class Result: "
                + anonymousRule.allow(accounts[0], 500));

        System.out.println("Lambda Result: "
                + lambdaRule.allow(accounts[0], 500));

        System.out.println("\n========== WITHDRAWAL TEST ==========");

        try {
            System.out.println("Savings withdrawal of Rs. 1000: "
                    + accounts[0].withdraw(1000));

            System.out.println("Current withdrawal of Rs. 6000: "
                    + accounts[1].withdraw(6000));

            System.out.println("Fixed Deposit withdrawal of Rs. 1000: "
                    + accounts[2].withdraw(1000));

        } catch (InsufficientFundsException e) {
            System.out.println("Bank Error: " + e.getMessage());
            System.out.println("Shortfall: Rs. " + e.getShortfall());

        } catch (InvalidAmountException e) {
            System.out.println("Invalid Amount: " + e.getMessage());
        }

        System.out.println("\n========== VALIDATION TESTS ==========");

        System.out.println("Valid Mobile: " +
                isValidMobile("9876543210"));

        System.out.println("Invalid Mobile: " +
                isValidMobile("123456"));

        System.out.println("Valid Email: " +
                Validator.isValidEmail("kush@gmail.com"));

        System.out.println("Invalid Email: " +
                Validator.isValidEmail("kushgmail.com"));

        System.out.println("Valid PAN: " +
                Validator.isValidPan("ABCDE1234F"));

        System.out.println("Invalid PAN: " +
                Validator.isValidPan("ABC123"));

        System.out.println("Valid IFSC: " +
                Validator.isValidIfsc("SBIN0001234"));

        System.out.println("Invalid IFSC: " +
                Validator.isValidIfsc("SBIN123"));

        System.out.println("\n========== COMMAND PARSER ==========");

        Command command = CommandParser.parse("DEPOSIT AC0001 500");

        System.out.println("Transaction Type: " + command.type());
        System.out.println("Account Number: " + command.accountNumber());
        System.out.println("Amount: Rs. " + command.amount());

        System.out.println("\n" +
                StatementFormatter.buildStatement(accounts[0]));

        System.out.println("\n========== MULTITHREADING TEST ==========");

        Account sharedAccount = new SavingsAccount(
                "Thread Test Account", 0, 1000
        );

        int threadCount = 10;
        int times = 1000;
        long amount = 1;

        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            AccountWorker worker = new AccountWorker(
                    sharedAccount, times, amount
            );

            threads[i] = new Thread(
                    worker, "Bank-Thread-" + (i + 1)
            );

            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted.");
                break;
            }
        }

        System.out.println("Expected Balance: Rs. "
                + (threadCount * times * amount));

        System.out.println("Actual Balance: Rs. "
                + sharedAccount.getBalance());

        System.out.println("\n========== TRANSACTION PROCESSOR TEST ==========");

        Account transactionAccount = new SavingsAccount(
                "Transaction Test", 10000, 1000
        );

        TransactionProcessor processor = new TransactionProcessor();

        processor.addAccount(transactionAccount);

        System.out.println("Account added to ConcurrentHashMap: "
                + processor.getAccount(transactionAccount.getAccountNumber()));

        for (int i = 1; i <= 10; i++) {
            final int taskNumber = i;

            processor.submit(() -> {
                try {
                    if (taskNumber % 2 == 0) {
                        processor.getAccount(transactionAccount.getAccountNumber()).deposit(500);

                        System.out.println(
                                Thread.currentThread().getName()
                                + " deposited Rs. 500"
                        );

                    } else {
                        processor.getAccount(transactionAccount.getAccountNumber()).withdraw(200);

                        System.out.println(
                                Thread.currentThread().getName()
                                + " withdrew Rs. 200"
                        );
                    }

                } catch (BankException e) {
                    System.out.println("Transaction failed: " + e.getMessage());
                }
            });
        }

        processor.stop();

        System.out.println("Final Transaction Balance: Rs. "
                + transactionAccount.getBalance());

        System.out.println("\n========== PRODUCER CONSUMER TEST ==========");

        TransactionBuffer buffer = new TransactionBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.produce(i * 100);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.consume();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producer-Consumer interrupted.");
        }

        System.out.println("\n========== DEADLOCK DEMONSTRATION ==========");

        Account deadlockA = new SavingsAccount(
                "Account A", 5000, 1000
        );

        Account deadlockB = new SavingsAccount(
                "Account B", 5000, 1000
        );

        DeadlockDemo.demonstrateDeadlock(deadlockA, deadlockB);

        System.out.println("\n========== DEADLOCK PREVENTION ==========");

        Account safeA = new SavingsAccount(
                "Safe Account A", 5000, 1000
        );

        Account safeB = new SavingsAccount(
                "Safe Account B", 5000, 1000
        );

        try {
            DeadlockDemo.transferSafely(safeA, safeB, 500);
            DeadlockDemo.transferSafely(safeB, safeA, 300);

        } catch (BankException e) {
            System.out.println("Safe transfer failed: " + e.getMessage());
        }

        System.out.println("Safe Account A Balance: Rs. "
                + safeA.getBalance());

        System.out.println("Safe Account B Balance: Rs. "
                + safeB.getBalance());

                
        System.out.println("\n========== FILE PERSISTENCE TEST ==========");

        Path dataFolder = Paths.get("data");
        Path logsFolder = dataFolder.resolve("logs");
        Path stateFile = dataFolder.resolve("accounts.dat");

        try {
            Files.createDirectories(logsFolder);

            StatePersister.save(accounts, stateFile);

            Account[] loadedAccounts = StatePersister.load(stateFile);

            System.out.println("\nReloaded Account Details:");

            for (Account account : loadedAccounts) {
                System.out.println(account);
            }

        } catch (IOException e) {
            System.out.println("File operation error: " + e.getMessage());
        }

        System.out.println("\n========== TRANSACTION LOG TEST ==========");

        Path logFile = logsFolder.resolve("transactions.txt");

        TransactionLog.append(logFile, "DEPOSIT 500");
        TransactionLog.append(logFile, "WITHDRAW 200");
        TransactionLog.append(logFile, "DEPOSIT 1000");
        TransactionLog.append(logFile, "WITHDRAW 300");

        System.out.println("Transaction logs saved successfully.");

        System.out.println("\n========== REPORT GENERATION TEST ==========");

        Path reportFile = dataFolder.resolve("end-of-day-report.txt");

        ReportGenerator.generate(logsFolder, reportFile);

        try {
            String report = Files.readString(reportFile);
            System.out.println("\n" + report);

        } catch (IOException e) {
            System.out.println("Unable to read report: " + e.getMessage());
        }


        Scanner sc = new Scanner(System.in);

        
Repository<Account> finalRepository = new Repository<>();

AccountStore finalAccountStore = new AccountStore();

TransactionProcessor finalProcessor = new TransactionProcessor();

Path dataFolderFinal = Paths.get("data");
Path logsFolderFinal = dataFolderFinal.resolve("logs");
Path stateFileFinal = dataFolderFinal.resolve("accounts.dat");

try {
    Files.createDirectories(logsFolderFinal);

    Account[] savedAccounts = StatePersister.load(stateFileFinal);

    if (savedAccounts.length > 0) {
        for (Account account : savedAccounts) {
            finalRepository.save(account);
            finalAccountStore.add(account);
            finalProcessor.addAccount(account);
        }
    } else {
        for (Account account : accounts) {
            finalRepository.save(account);
            finalAccountStore.add(account);
            finalProcessor.addAccount(account);
        }
    }

} catch (Exception e) {
    System.out.println("Startup error: " + e.getMessage());
}

BankService bankService = new BankService(
        finalRepository,
        finalAccountStore,
        finalProcessor
);

System.out.println("\nMiniBank service initialized successfully.");


        

while (true) {
    System.out.println("\n========== MINIBANK MAIN MENU ==========");
    System.out.println("1. Open Account");
    System.out.println("2. Deposit");
    System.out.println("3. Withdraw");
    System.out.println("4. Transfer");
    System.out.println("5. View Account Statement");
    System.out.println("6. List All Accounts");
    System.out.println("7. Process Batch Transactions");
    System.out.println("8. Generate End-of-Day Report");
    System.out.println("9. Exit");
    System.out.print("Enter your choice: ");

    int choice = sc.nextInt();
    sc.nextLine();

    MenuOption option = switch (choice) {
        case 1 -> MenuOption.OPEN_ACCOUNT;
        case 2 -> MenuOption.DEPOSIT;
        case 3 -> MenuOption.WITHDRAW;
        case 4 -> MenuOption.TRANSFER;
        case 5 -> MenuOption.STATEMENT;
        case 6 -> MenuOption.LIST_ACCOUNTS;
        case 7 -> MenuOption.BATCH_TRANSACTIONS;
        case 8 -> MenuOption.GENERATE_REPORT;
        case 9 -> MenuOption.EXIT;
        default -> null;
    };

    if (option == null) {
        System.out.println("Invalid choice!");
        continue;
    }

    try {
        switch (option) {
            case OPEN_ACCOUNT -> {
                System.out.print("Owner name: ");
                String name = sc.nextLine();

                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Mobile: ");
                String mobile = sc.nextLine();

                System.out.println("1. Savings  2. Current  3. Fixed Deposit");
                System.out.print("Choose account type: ");
                int type = sc.nextInt();

                System.out.print("Initial balance: ");
                long balance = sc.nextLong();

                long limit = 0;

                if (type == 1 || type == 2) {
                    System.out.print("Minimum balance / overdraft limit: ");
                    limit = sc.nextLong();
                }

                String accountType = switch (type) {
                    case 1 -> "SAVINGS";
                    case 2 -> "CURRENT";
                    case 3 -> "FIXED";
                    default -> "";
                };

                Account account = bankService.openAccount(
                        accountType, name, email, mobile, balance, limit
                );

                System.out.println("Account opened successfully!");
                System.out.println(account);
            }

            case DEPOSIT -> {
                System.out.print("Account number: ");
                String id = sc.nextLine();

                System.out.print("Deposit amount: ");
                long depositAmount = sc.nextLong();

                bankService.deposit(id, depositAmount);
            }

            case WITHDRAW -> {
                System.out.print("Account number: ");
                String id = sc.nextLine();

                System.out.print("Withdrawal amount: ");
                	long withdrawalAmount = sc.nextLong();

                bankService.withdraw(id, withdrawalAmount);
            }

            case TRANSFER -> {
                System.out.print("From account number: ");
                String from = sc.nextLine();

                System.out.print("To account number: ");
                String to = sc.nextLine();

                System.out.print("Transfer amount: ");
                long transferAmount = sc.nextLong();

                bankService.transfer(from, to, transferAmount);
            }

            case STATEMENT -> {
                System.out.print("Account number: ");
                String id = sc.nextLine();

                System.out.println(bankService.statement(id));
            }

            case LIST_ACCOUNTS -> {
                System.out.println("\n========== ALL BANK ACCOUNTS ==========");

                for (Account account : bankService.listAccounts()) {
                    System.out.println(account);
                }
            }

            case BATCH_TRANSACTIONS -> {
                System.out.print("Account number: ");
                String id = sc.nextLine();

                System.out.print("Amount per deposit: ");
                	long batchAmount = sc.nextLong();

                System.out.print("Number of deposits: ");
                int count = sc.nextInt();

                bankService.processBatchDeposits(id, batchAmount, count);
            }

            case GENERATE_REPORT -> {
                Path menuReportFile = dataFolderFinal.resolve("end-of-day-report.txt");

ReportGenerator.generate(logsFolderFinal, menuReportFile);

System.out.println(Files.readString(menuReportFile));
            }

            case EXIT -> {
                Account[] finalAccounts =
                        finalRepository.findAll().toArray(new Account[0]);

                StatePersister.save(finalAccounts, stateFileFinal);

                finalProcessor.stop();

                System.out.println("Thank you for using MiniBank!");
            }
        }

    } catch (Exception e) {
        System.out.println("Operation failed: " + e.getMessage());
    }

    if (option == MenuOption.EXIT) {
        break;
    }
}



        sc.close();
    }
}
