package HMS;

public class Administrator extends Staff {
	
	public Administrator() {
		super();
	}
	
	public Administrator(String uID,String pass , String r) {
		super(uID,pass,r);
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
