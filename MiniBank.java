
import java.util.Scanner;

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

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
            new Account("Kush Patel", 5000),
            new Account("Rahul Shah", 3000),
            new Account("Aarav Patel")
        };

        accounts[0].deposit(2000);
        accounts[0].withdraw(1000);

        accounts[1].deposit(1500);
        accounts[1].withdraw(500);

        accounts[2].deposit(4000);
        accounts[2].withdraw(1000);

        System.out.println("\n========== ACCOUNT DETAILS ==========");

        for (Account account : accounts) {
            System.out.println(account);
        }

        System.out.println("\n========== VALIDATION TESTS ==========");

        System.out.println("Valid Mobile: " +
                Validator.isValidMobile("9876543210"));
        System.out.println("Invalid Mobile: " +
                Validator.isValidMobile("123456"));

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

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid choice! Please try again.");
                continue;
            }

            String message = switch (option) {
                case OPEN_ACCOUNT -> "Open Account - to be implemented in a later lab.";
                case DEPOSIT -> "Deposit - to be implemented in a later lab.";
                case WITHDRAW -> "Withdraw - to be implemented in a later lab.";
                case TRANSFER -> "Transfer - to be implemented in a later lab.";
                case EXIT -> "Thank you for using MiniBank!";
            };

            System.out.println(message);

            if (option == MenuOption.EXIT) {
                break;
            }
        }

        sc.close();
    }
}
