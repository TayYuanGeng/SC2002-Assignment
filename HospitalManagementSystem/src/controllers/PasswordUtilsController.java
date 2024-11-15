package controllers;

import interfaces.CSVUtilsInterface;
import interfaces.PasswordUtilsInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;
import models.Account;
import models.Patient;
import models.Staff;

/**
 * Handles password-related functionality, including hashing, validation, and first-time login handling.
 * Implements the {@link PasswordUtilsInterface}.
 */
public class PasswordUtilsController implements PasswordUtilsInterface {

    /**
     * Scanner instance for user input.
     */
    static Scanner sc = new Scanner(System.in);

    /**
     * Singleton instance of {@link PasswordUtilsInterface}.
     */
    static PasswordUtilsInterface passwordUtils = new PasswordUtilsController();

    /**
     * Singleton instance of {@link CSVUtilsInterface}.
     */
    static CSVUtilsInterface csvUtils = new CSVUtilsController();

    /**
     * Hashes the given password using SHA-256 algorithm.
     *
     * @param password The plain text password to be hashed.
     * @return The hashed password as a hexadecimal string.
     * @throws RuntimeException If the hashing algorithm is not available.
     */
    @Override
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates if the provided password meets the system's password policy.
     * 
     * The password must:
     * - Be 8 to 16 characters long.
     * - Contain at least one uppercase letter.
     * - Contain at least one special character (e.g., !@#$%^&*).
     * - Not match the default password "Password".
     *
     * @param password The password to be validated.
     * @return {@code true} if the password is valid; {@code false} otherwise.
     */
    @Override
    public boolean isValidNewPassword(String password) {
        if (passwordUtils.hashPassword(password).equals(passwordUtils.hashPassword("Password"))) {
            return false;
        }
        String regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,16}$";
        return Pattern.matches(regex, password);
    }

    /**
     * Validates a password against the given account's stored password.
     *
     * @param account  The account whose password is being validated.
     * @param password The plain text password to be validated.
     * @return {@code true} if the password matches; {@code false} otherwise.
     */
    @Override
    public boolean isValidPassword(Account account, String password) {
        return passwordUtils.hashPassword(password).equals(account.getPassword());
    }

    /**
     * Checks if the account is logging in for the first time.
     * 
     * An account is considered to be logging in for the first time if its password
     * matches the default hashed password.
     *
     * @param account  The account to check.
     * @param password The plain text password entered during login.
     * @return {@code true} if it is the first login; {@code false} otherwise.
     */
    @Override
    public boolean isFirstTimeLogin(Account account, String password) {
        return account.getPassword().equals(passwordUtils.hashPassword("Password"));
    }

    /**
     * Handles the first-time login process, prompting the user to change their default password.
     * Ensures the new password meets the system's policy and updates the account details in the CSV file.
     *
     * @param account The account of the user logging in for the first time.
     * @return The updated account after a successful password change.
     */
    @Override
    public Account handleFirstTimeLogin(Account account) {
        Account loggedInUser = null;
        System.out.println("First-Time login. Please change password.");
        boolean passChanged = false;

        while (!passChanged) {
            System.out.println("Please enter new password:");
            String newPassword = sc.nextLine();

            if (!isValidNewPassword(newPassword)) {
                System.out.println("""
                                   Invalid Password. Please try again!
                                   Password cannot be "Password".
                                   Password must be 8 to 16 characters long.
                                   Contains at least one uppercase letter.
                                   Contains at least one special character (such as !@#$%^&*, etc.)"""
                );
                continue;
            }

            if (!passwordUtils.hashPassword(newPassword).equals(account.getPassword())) {
                System.out.println("Confirm new password:");
                String confirmPassword = sc.nextLine();

                if (newPassword.equals(confirmPassword)) {
                    account.setPassword(newPassword);
                    System.out.println("Password changed successfully!");

                    // Update in CSV based on account type
                    if (account instanceof Staff) {
                        csvUtils.updateStaffInCSV(MainMenuController.CSV_FILE_PATH + "Staff_List.csv", (Staff) account);
                    } else if (account instanceof Patient) {
                        csvUtils.updatePatientInCSV(MainMenuController.CSV_FILE_PATH + "Patient_List.csv", (Patient) account);
                    }

                    passChanged = true;
                    loggedInUser = account;
                } else {
                    System.out.println("Passwords do not match. Try again.");
                }
            } else {
                System.out.println("New password cannot be the default password. Try again.");
            }
        }
        return loggedInUser;
    }
}
