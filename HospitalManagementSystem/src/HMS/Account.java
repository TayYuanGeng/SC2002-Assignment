package HMS;

public abstract class Account {
	private String userID;
	protected String password;
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
	
	public void setPassword(String pass) {}
	
	public abstract String getPassword();
}
