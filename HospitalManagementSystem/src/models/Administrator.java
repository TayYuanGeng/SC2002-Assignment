package models;

import java.util.ArrayList;


public class Administrator extends Staff {
	
	static ArrayList<Staff> staffList = new ArrayList<Staff>();
	
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
	
}

