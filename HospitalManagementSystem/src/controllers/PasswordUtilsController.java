package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;
import models.Account;
import models.Patient;
import models.Staff;

public class PasswordUtilsController {
    static Scanner sc = new Scanner(System.in);

    public static String hashPassword(String password) {
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

    public static boolean isValidNewPassword(String password) {
        if(PasswordUtilsController.hashPassword(password).equals(PasswordUtilsController.hashPassword("Password"))){
            return false;
        }
        String regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,16}$";
        return Pattern.matches(regex, password);
    }

    public static boolean isValidPassword(Account account, String password) {
        //System.out.println("Is valid pass: " + PasswordUtilsController.hashPassword(password).equals(account.getPassword()));
        return PasswordUtilsController.hashPassword(password).equals(account.getPassword());
    }

    public static boolean isFirstTimeLogin(Account account, String password) {
        //System.out.println("First time login" + (account.getPassword().equals("Password") && isValidPassword(account, password)));
        return account.getPassword().equals(PasswordUtilsController.hashPassword("Password"));
    }

    public static Account handleFirstTimeLogin(Account account) {
        Account loggedInUser = null;
        System.out.println("First-Time login. Please change password.");
        boolean passChanged = false;

        while (!passChanged) {
            System.out.println("Please enter new password:");
            String newPassword = sc.nextLine();

            if(!isValidNewPassword(newPassword)){
                System.out.println("""
                                   Invalid Password. Please try again!
                                   Password cannot be "Password".
                                   Password must be 8 to 16 characters long.
                                   Contains at least one uppercase letter.
                                   Contains at least one special character (such as !@#$%^&*, etc.).)""" //
                //
                );
                continue;
            }

            if (!PasswordUtilsController.hashPassword(newPassword).equals(account.getPassword())) {
                System.out.println("Confirm new password:");
                String confirmPassword = sc.nextLine();

                if (newPassword.equals(confirmPassword)) {
                    account.setPassword(newPassword);
                    System.out.println("Password changed successfully!");
                    
                    // Update in CSV based on account type
                    if (account instanceof Staff) {
                        CSVUtils.updateStaffInCSV("data\\Staff_List.csv", (Staff) account);
                    } else if (account instanceof Patient) {
                        CSVUtils.updatePatientInCSV("data\\Patient_List.csv", (Patient) account);
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
