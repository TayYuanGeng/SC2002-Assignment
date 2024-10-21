package HMS;

public abstract class Account {
	private String userID;
	private String password;
	private String role;
	
	public Account() {
		userID ="";
		password = "password";
		role = "";
	}
	
	public Account(String uID,String pass,String r) {
		this.userID = uID;
		this.password = pass;
		this.role = r;
	}
	
	public void displayMenu() {}
	
	public String getID() {
		return this.userID;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setPassword(String pass) {}
	
	public String getPassword() {
		return this.password;
	}
}
