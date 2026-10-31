**# MiniBank**

A console-based banking application developed using Java as part of the Object-Oriented Programming practicals.

**## Lab 1 – Menu Shell**
\- Java Records and Enums
\- Scanner and switch expressions

**## Lab 2 – Customer and Account Classes**
\- Encapsulation and constructors
\- Customer ID and account number generation
\- Deposit and withdrawal operations

**## Lab 3 – Object Methods and Nested Classes**
\- `toString()`, `equals()` and `hashCode()`
\- Nested Address class
\- Customer cloning
\- `instanceof` checking

**## Lab 4 – Validation and Command Parsing**
\- Regex validation for mobile, email, PAN and IFSC
\- TransactionType enum
\- Command record and parser
\- StatementFormatter using StringBuilder

**## Lab 5 – Abstract Classes and Polymorphism**
\- Abstract Account class
\- SavingsAccount
\- CurrentAccount
\- FixedDepositAccount
\- Method overriding
\- Runtime polymorphism
\- Account-specific withdrawal rules

**## Lab 6 – Interfaces, Packages and Runnable JAR**
\- Transactable interface for deposit and withdrawal operations
\- InterestBearing interface with default `yearlyInterest()` method
\- WithdrawRule functional interface using anonymous class and lambda expression
\- Premium marker interface
\- Organized Java classes into `model`, `util` and `service` packages
\- Static import for mobile number validation
\- Created and executed a runnable MiniBank JAR file

**## Lab 7 – Annotations and Reflection**
\- Added `@Override` annotations to overridden methods
\- Used `@FunctionalInterface` for WithdrawRule
\- Created custom annotations: `@Id`, `@Positive` and `@MaxLength`
\- Applied annotations to Account and Customer fields
\- Implemented AnnotationValidator using Java Reflection
\- Used `getDeclaredFields()`, `isAnnotationPresent()`, `setAccessible(true)` and `get()` to inspect fields
\- Validated positive account balance and maximum customer name length
\- Tested annotation-based validation using invalid objects

**## Lab 8 – Exception Handling**
\- Created custom exception hierarchy using BankException
\- Implemented InsufficientFundsException with shortfall field
\- Created AccountNotFoundException and InvalidAmountException
\- Added exception handling to deposit and withdrawal operations
\- Implemented transfer() method with try-catch-finally blocks
\- Used checked exceptions for banking operations
\- Displayed friendly error messages for banking errors
\- Created BankSession class implementing AutoCloseable
\- Demonstrated try-with-resources for automatic resource management

**## Lab 9 – Multithreading and Synchronization**
\- Created AccountWorker class implementing Runnable
\- Used multiple threads to perform concurrent banking operations
\- Created 10 threads accessing a single shared account
\- Used the start() method to execute threads
\- Used join() to wait for thread completion
\- Printed thread names to observe thread lifecycle
\- Demonstrated race conditions in shared account operations
\- Used the synchronized keyword in deposit() and withdraw()
\- Ensured thread-safe balance updates
\- Verified the expected final balance after concurrent deposits

**## Project Structure**

```text
oop-minibank-25cs072/
├── model/
│   ├── annotation/
│   ├── Account.java
│   ├── AccountWorker.java
│   ├── Customer.java
│   └── ...
├── exception/
│   ├── BankException.java
│   ├── InsufficientFundsException.java
│   ├── AccountNotFoundException.java
│   └── InvalidAmountException.java
├── util/
│   ├── AnnotationValidator.java
│   └── ...
├── service/
│   ├── MiniBank.java
│   └── BankSession.java
├── manifest.txt
├── MiniBank.jar
├── README.md
└── .gitignore
```

**## Requirements**
\- Java JDK 17 or above

**## How to Compile and Run**

Compile:

```bash
javac -d out model/*.java model/annotation/*.java exception/*.java util/*.java service/*.java
```

Run:

```bash
java -cp out service.MiniBank
```

**## Create and Run Runnable JAR**

Create the JAR:

```bash
jar cfm MiniBank.jar manifest.txt -C out .
```

Run the JAR:

```bash
java -jar MiniBank.jar
```

**## Student Details**
\- Name: Kush Patel
\- Roll No: 25CS072
\- Course: B.Tech CSE