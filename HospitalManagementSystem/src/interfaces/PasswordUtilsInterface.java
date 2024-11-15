package interfaces;

import models.Account;

/**
 * Interface defining utility methods for password management in the Hospital Management System.
 * Provides methods for password hashing, validation, and handling first-time login scenarios.
 */
public interface PasswordUtilsInterface {

    /**
     * Hashes a plain-text password using a secure algorithm.
     *
     * @param password The plain-text password to hash.
     * @return The hashed password as a hexadecimal string.
     */
    String hashPassword(String password);

    /**
     * Validates whether a new password meets security requirements.
     *
     * @param password The new password to validate.
     * @return {@code true} if the password is valid; {@code false} otherwise.
     */
    boolean isValidNewPassword(String password);

    /**
     * Validates a user's password by comparing it with the hashed version stored in their account.
     *
     * @param account  The user's account containing the stored password hash.
     * @param password The plain-text password to validate.
     * @return {@code true} if the password matches the stored hash; {@code false} otherwise.
     */
    boolean isValidPassword(Account account, String password);

    /**
     * Checks if the user is logging in for the first time.
     *
     * @param account  The user's account.
     * @param password The plain-text password entered by the user.
     * @return {@code true} if it is the first-time login; {@code false} otherwise.
     */
    boolean isFirstTimeLogin(Account account, String password);

    /**
     * Handles the first-time login process for a user, including password change.
     *
     * @param account The user's account.
     * @return The updated account with the new password set.
     */
    Account handleFirstTimeLogin(Account account);
}
