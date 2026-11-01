
package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {

    public static void generate(Path logsFolder, Path reportFile) {

        long totalDeposits = 0;
        long totalWithdrawals = 0;

        StringBuilder report = new StringBuilder();

        report.append("========== END OF DAY REPORT ==========\n");

        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(logsFolder)) {

            for (Path file : stream) {

                if (!Files.isRegularFile(file)) {
                    continue;
                }

                report.append("\nLog File: ")
                        .append(file.getFileName())
                        .append("\n");

                BasicFileAttributes attributes =
                        Files.readAttributes(file, BasicFileAttributes.class);

                report.append("File Size: ")
                        .append(attributes.size())
                        .append(" bytes\n");

                report.append("Last Modified: ")
                        .append(attributes.lastModifiedTime())
                        .append("\n");

                try (BufferedReader reader = Files.newBufferedReader(file)) {

                    String line;

                    while ((line = reader.readLine()) != null) {

                        String[] parts = line.trim().split("\\s+");

                        if (parts.length < 2) {
                            continue;
                        }

                        try {
                            long amount = Long.parseLong(parts[1]);

                            if (parts[0].equalsIgnoreCase("DEPOSIT")) {
                                totalDeposits += amount;

                            } else if (parts[0].equalsIgnoreCase("WITHDRAW")) {
                                totalWithdrawals += amount;
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid log entry: " + line);
                        }
                    }
                }
            }

            report.append("\nTotal Deposits: Rs. ")
                    .append(totalDeposits);

            report.append("\nTotal Withdrawals: Rs. ")
                    .append(totalWithdrawals);

            report.append("\n======================================\n");

            Files.writeString(reportFile, report.toString());

            System.out.println("End-of-day report generated successfully.");

        } catch (IOException e) {
            System.out.println("Report generation error: " + e.getMessage());
        }
    }
}
