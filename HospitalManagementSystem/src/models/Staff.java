package models;


/**
 * Represents a staff member in the hospital management system.
 * Extends the {@link Account} class to include additional details such as gender and age.
 */
public class Staff extends Account{
	/** The gender of the staff member. */
	private String gender;
	/** The age of the staff member. */
	private int age;
	
	/**
     * Default constructor for the Staff class.
     * Initializes a staff member with default values.
     */
	public Staff() {
		super();
	}
	
	/**
     * Constructs a Staff object with the specified account details.
     *
     * @param staffID The ID of the staff member.
     * @param name    The name of the staff member.
     * @param pass    The password for the staff member's account.
     * @param role    The role assigned to the staff member.
     */
	public Staff(String staffID, String name, String pass, String role) {
		super(staffID, name, pass, role);
	}
	
	/**
     * Displays the menu for the staff member.
     * This method is currently unimplemented.
	 * Will be implemented by subclasses.
     */
	public void displayMenu() {};

	/**
     * Gets the name of the staff member.
     *
     * @return The name of the staff member.
     */
	public String getName(){
		return super.getName();
	}
	
	/**
     * Gets the ID of the staff member.
     *
     * @return The ID of the staff member.
     */
	public String getID() {
		return super.getID();
	}
	
	/**
     * Gets the role of the staff member.
     *
     * @return The role of the staff member.
     */
	public String getRole() {
		return super.getRole();
	}

	/**
     * Sets the role of the staff member.
     *
     * @param role The new role for the staff member.
     */
	public void setRole(String role) {
		super.setRole(role);
	}
	
	/**
     * Gets the password of the staff member's account.
     *
     * @return The password of the staff member.
     */
	public String getPassword() {
		return super.getPassword();
	}

	/**
     * Sets the password for the staff member's account.
     *
     * @param pass The new password for the staff member.
     */
	public void setPassword(String pass) {
		super.setPassword(pass);
	};

	/**
     * Gets the gender of the staff member.
     *
     * @return The gender of the staff member.
     */
	public String getGender() {
		return this.gender;
	}

	/**
     * Sets the gender of the staff member.
     *
     * @param gender The gender of the staff member.
     */
	public void setGender(String gender) {
		this.gender = gender;
	};

	/**
     * Gets the age of the staff member.
     *
     * @return The age of the staff member.
     */
	public int getAge() {
		return this.age;
	}

	/**
     * Sets the age of the staff member.
     *
     * @param age The age of the staff member.
     */
	public void setAge(int age) {
		this.age = age;
	};
	
}
