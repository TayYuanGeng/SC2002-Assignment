package HMS;

public class Administrator extends Account {
	
	public Administrator() {
		super();
	}
	
	public Administrator(String uID,String pass , String r) {
		super(uID,pass,r);
	}
	
	public String getPassword() {
		return this.password;
	}
	
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public void displayMenu() {
		
	}
	
	public int setStockAmount() {
		return 0;
	}
	
	public int setLowLevelStockAmt() {
		return 0;
	}
}
