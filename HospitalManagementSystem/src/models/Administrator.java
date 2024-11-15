package models;

import java.util.ArrayList;

/**
 * Represents an Administrator account in the system. This class extends the {@link Staff} class,
 * inheriting its attributes and methods while specializing it for administrator-specific functionality.
 */
public class Administrator extends Staff {

    /** A list of all staff members managed by the administrator */
    static ArrayList<Staff> staffList = new ArrayList<>();

    /**
     * Default constructor. Initializes an Administrator account with default values.
     * <p>Inherits default initialization behavior from the {@link Staff} class.</p>
     */
    public Administrator() {
        super();
    }

    /**
     * Parameterized constructor. Initializes an Administrator account with the specified details.
     *
     * @param uID  the unique user ID for the administrator
     * @param name the name of the administrator
     * @param pass the password for the administrator (stored as plain text)
     * @param r    the role of the administrator
     */
    public Administrator(String uID, String name, String pass, String r) {
        super(uID, name, pass, r);
    }
}
