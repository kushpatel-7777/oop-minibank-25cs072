
public class CommandParser {

    public static Command parse(String line) {

        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Command cannot be empty");
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid command format");
        }

        TransactionType type =
                TransactionType.valueOf(parts[0].toUpperCase());

        String accountNumber = parts[1];

        long amount = Long.parseLong(parts[2]);

        return new Command(type, accountNumber, amount);
    }
}
