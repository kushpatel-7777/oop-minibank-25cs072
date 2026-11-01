
package service;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.nio.file.Path;

import model.Account;

public class StatePersister {

    public static void save(Account[] accounts, Path file) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(file.toFile()))) {

            out.writeObject(accounts);
            System.out.println("Account data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving account data: " + e.getMessage());
        }
    }

    public static Account[] load(Path file) {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file.toFile()))) {

            Account[] accounts = (Account[]) in.readObject();
            System.out.println("Account data loaded successfully.");

            return accounts;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading account data: " + e.getMessage());
            return new Account[0];
        }
    }
}
