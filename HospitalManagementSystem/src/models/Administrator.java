package models;

public class Administrator extends Staff {
	
	public Administrator() {
		super();
	}
	
	public Administrator(String uID,String name, String pass , String r) {
		super(uID,name,pass,r);
	}
	
	public String getPassword() {
		return super.getPassword();
	}
	
	
	public void setPassword(String pass) {
		super.setPassword(pass);
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
