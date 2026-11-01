# MiniBank

A console-based banking application developed using Java as part of the Object-Oriented Programming practicals.

## Lab 1 – Menu Shell
- Java Records and Enums
- Scanner and switch expressions
- Menu-driven console application

## Lab 2 – Customer and Account Classes
- Encapsulation and constructors
- Customer ID and account number generation
- Deposit and withdrawal operations

## Lab 3 – Object Methods and Nested Classes
- `toString()`, `equals()` and `hashCode()`
- Nested Address class
- Customer cloning
- `instanceof` checking

## Lab 4 – Validation and Command Parsing
- Regex validation for mobile, email, PAN and IFSC
- TransactionType enum
- Command record and parser
- StatementFormatter using StringBuilder

## Lab 5 – Abstract Classes and Polymorphism
- Abstract Account class
- SavingsAccount
- CurrentAccount
- FixedDepositAccount
- Method overriding
- Runtime polymorphism
- Account-specific withdrawal rules

## Lab 6 – Interfaces, Packages and Runnable JAR
- Transactable interface for deposit and withdrawal operations
- InterestBearing interface with default `yearlyInterest()` method
- WithdrawRule functional interface using anonymous class and lambda expression
- Premium marker interface
- Organized Java classes into `model`, `util` and `service` packages
- Static import for mobile number validation
- Created and executed a runnable MiniBank JAR file

## Lab 7 – Annotations and Reflection
- Added `@Override` annotations to overridden methods
- Used `@FunctionalInterface` for WithdrawRule
- Created custom annotations: `@Id`, `@Positive` and `@MaxLength`
- Applied annotations to Account and Customer fields
- Implemented AnnotationValidator using Java Reflection
- Used `getDeclaredFields()`, `isAnnotationPresent()`, `setAccessible(true)` and `get()` to inspect fields
- Validated positive account balance and maximum customer name length
- Tested annotation-based validation using invalid objects

## Lab 8 – Exception Handling
- Created custom exception hierarchy using BankException
- Implemented InsufficientFundsException with shortfall field
- Created AccountNotFoundException and InvalidAmountException
- Added exception handling to deposit and withdrawal operations
- Implemented `transfer()` method with try-catch-finally blocks
- Used checked exceptions for banking operations
- Displayed friendly error messages for banking errors
- Created BankSession class implementing AutoCloseable
- Demonstrated try-with-resources for automatic resource management

## Lab 9 – Multithreading and Synchronization
- Created AccountWorker class implementing Runnable
- Used multiple threads to perform concurrent banking operations
- Created 10 threads accessing a single shared account
- Used the `start()` method to execute threads
- Used `join()` to wait for thread completion
- Printed thread names to observe thread lifecycle
- Used the synchronized keyword in `deposit()` and `withdraw()`
- Ensured thread-safe balance updates
- Verified the expected final balance after concurrent deposits

## Lab 10 – Advanced Multithreading
- Created TransactionProcessor using ExecutorService
- Implemented a fixed thread pool of 4 threads
- Submitted multiple deposit and withdrawal tasks using `execute()`
- Used `shutdown()` and `awaitTermination()` to manage thread execution
- Created TransactionBuffer using a shared array buffer
- Implemented the Producer-Consumer problem using `wait()` and `notify()`
- Demonstrated deadlock using two threads locking accounts in opposite order
- Detected deadlock using thread status
- Implemented deadlock prevention using consistent lock ordering
- Performed safe account transfers without deadlock

## Lab 11 – File I/O, Serialization and Reporting
- Implemented Serializable interface in Account, Customer and account subclasses
- Added `serialVersionUID` to serializable classes
- Created StatePersister to save and load account data
- Used ObjectOutputStream and ObjectInputStream for object serialization
- Created TransactionLog to append transaction records using Java NIO
- Used Files.write() with CREATE and APPEND options
- Created ReportGenerator to read transaction log files
- Used DirectoryStream to access log files
- Used BufferedReader to read transaction records line by line
- Used BasicFileAttributes to retrieve file size and last modified time
- Calculated total deposits and withdrawals
- Generated and displayed an end-of-day report

## Lab 12 – Collections Framework
- Created AccountStore using HashMap to store accounts
- Used account numbers as unique keys in the HashMap
- Implemented add(), get() and getAll() methods
- Used ArrayList to maintain transaction history
- Implemented sortedByBalance() using Comparator
- Sorted accounts by balance from lowest to highest
- Implemented Comparable<Account> in the Account class
- Overrode compareTo() to compare accounts by account number
- Used ConcurrentHashMap in TransactionProcessor for thread-safe account storage
- Tested account retrieval, transaction history and balance sorting

## Lab 13 – Generics and Generic Repository
- Created a generic `Repository<T>` class for reusable data storage
- Used `HashMap<String, T>` to store objects using their IDs
- Implemented `save()`, `findById()`, `findAll()` and `delete()` methods
- Used Java Reflection to find fields marked with the `@Id` annotation
- Retrieved object IDs dynamically using reflection
- Implemented `saveAll()` using `@SafeVarargs` to save multiple objects
- Created a bounded generic method `average(N[] values)` using `N extends Number`
- Tested Repository with multiple Account objects
- Verified account saving, retrieval, listing, deletion and average calculation

## Project Structure

```text
oop-minibank-25cs072/
├── model/
│   ├── annotation/
│   ├── Account.java
│   ├── AccountWorker.java
│   ├── Customer.java
│   ├── SavingsAccount.java
│   ├── CurrentAccount.java
│   ├── FixedDepositAccount.java
│   ├── Command.java
│   ├── TransactionType.java
│   ├── Transactable.java
│   ├── InterestBearing.java
│   ├── WithdrawRule.java
│   ├── Premium.java
│   ├── BankInfo.java
│   └── MenuOption.java
├── exception/
│   ├── BankException.java
│   ├── InsufficientFundsException.java
│   ├── AccountNotFoundException.java
│   └── InvalidAmountException.java
├── util/
│   ├── AnnotationValidator.java
│   ├── Validator.java
│   ├── CommandParser.java
│   └── StatementFormatter.java
├── service/
│   ├── MiniBank.java
│   ├── BankSession.java
│   ├── TransactionProcessor.java
│   ├── TransactionBuffer.java
│   ├── DeadlockDemo.java
│   ├── StatePersister.java
│   ├── TransactionLog.java
│   ├── ReportGenerator.java
│   ├── AccountStore.java
│   └── Repository.java
├── manifest.txt
├── MiniBank.jar
├── README.md
└── .gitignore
```

## Requirements
- Java JDK 17 or above

## How to Compile and Run

Compile:

```bash
javac -d out model/*.java model/annotation/*.java exception/*.java util/*.java service/*.java
```

Run:

```bash
java -cp out service.MiniBank
```

## Create and Run Runnable JAR

Create the JAR:

```bash
jar cfm MiniBank.jar manifest.txt -C out .
```

Run the JAR:

```bash
java -jar MiniBank.jar
```

## Student Details
- Name: Kush Patel
- Roll No: 25CS072
- Course: B.Tech CSE