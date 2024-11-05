package models;

public class Staff extends Account{
	private String staffID;
	private String name;
	private String password;
	private String role;
	
	public Staff() {
		super();
	}
	
	public Staff(String staffID, String name, String pass, String role) {
		super(staffID, name, pass, role);
	}
	
	public void displayMenu() {};

	public String getName(){
		return super.getName();
	}
	
	public String getID() {
		return super.getID();
	}
	
	public String getRole() {
		return super.getRole();
	}
	
	public void setPassword() {};
	
	public String getPassword() {
		return super.getPassword();
	}
}
