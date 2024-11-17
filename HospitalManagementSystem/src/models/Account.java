package models;

import controllers.PasswordUtilsController;
import interfaces.PasswordUtilsInterface;

/**
 * Represents an abstract Account in the system. This class is the
 * base class for different types of accounts (e.g., Administrator, Staff).
 * It provides common attributes and methods for managing account details
 * such as user ID, name, password, and role.
 */
public abstract class Account {
    /** The unique user ID of the account */
    private String userID;

    /** The name associated with the account */
    private String name;

    /** The hashed password for the account */
    private String password;

    /** The role of the account (e.g., "Administrator", "Staff") */
    private String role;

    /** Interface for handling password-related utilities */
    static PasswordUtilsInterface passwordUtils = new PasswordUtilsController();

    /**
     * Default constructor. Initializes the account with default values:
     * - Empty user ID
     * - Empty name
     * - Default hashed password ("password")
     * - Empty role
     */
    public Account() {
        userID = "";
        name = "";
        password = passwordUtils.hashPassword("password");
        role = "";
    }

    /**
     * Parameterized constructor. Initializes the account with the specified details.
     *
     * @param uID  the unique user ID for the account
     * @param name the name associated with the account
     * @param pass the password for the account 
     * @param r    the role of the account 
     */
    public Account(String uID, String name, String pass, String r) {
        this.userID = uID;
        this.name = name;
        this.password = pass;
        this.role = r;
    }

    /**
     * Abstract method to display the menu associated with the account type.
     */
    public abstract void displayMenu();

    /**
     * Gets the name of the account.
     *
     * @return the name associated with the account
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the account.
     *
     * @param name the new name for the account
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user ID of the account.
     *
     * @return the unique user ID of the account
     */
    public String getID() {
        return this.userID;
    }

    /**
     * Gets the role of the account.
     *
     * @return the role of the account (e.g., "Administrator")
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Sets the role of the account.
     *
     * @param role the new role for the account
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the hashed password of the account.
     *
     * @return the hashed password of the account
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password of the account. The provided password is hashed
     * before being stored.
     *
     * @param pass the new password for the account (plain text)
     */
    public void setPassword(String pass) {
        this.password = passwordUtils.hashPassword(pass);
    }
}
