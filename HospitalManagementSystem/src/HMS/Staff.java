package HMS;

public class Staff extends Account{
	private String staffID;
	private String password;
	private String role;
	
	public Staff() {
		super();
	}
	
	public Staff(String staffID, String pass, String role) {
		super(staffID, pass, role);
	}
	
	public void displayMenu() {};
	
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
