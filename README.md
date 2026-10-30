# MiniBank

A console-based banking application developed using Java as part of the Object-Oriented Programming practicals.

## Lab 1 – Menu Shell
- Java Records and Enums
- Scanner and switch expressions

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

## Project Structure

```text
oop-minibank-25cs072/
├── model/
├── util/
├── service/
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
javac -d out model/*.java util/*.java service/*.java
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