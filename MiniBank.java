
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
            "Hostel Block A",
            "Changa",
            "388421"
        );

        Customer customer = new Customer(
            "Kush Patel",
            "kush@gmail.com",
            "9876543210",
            address
        );

        System.out.println("\n========== CUSTOMER DETAILS ==========");
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Mobile: " + customer.getMobile());
        System.out.println("Address: " + customer.getAddress().getLine());
        System.out.println("City: " + customer.getAddress().getCity());
        System.out.println("Pincode: " + customer.getAddress().getPincode());

        Customer clonedCustomer = customer.clone();

        System.out.println("\nCloned Customer ID: " + clonedCustomer.getCustomerId());
        System.out.println("Customer Clone Created Successfully");

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

        System.out.println("\n========== EQUALITY CHECK ==========");
        System.out.println("Account 1 equals Account 2: " + accounts[0].equals(accounts[1]));
        System.out.println("Account 1 equals itself: " + accounts[0].equals(accounts[0]));

        Object obj = accounts[0];

        System.out.println("\n========== INSTANCEOF CHECK ==========");
        System.out.println("Object is an Account: " + (obj instanceof Account));
        System.out.println("Object is a Customer: " + (obj instanceof Customer));

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
