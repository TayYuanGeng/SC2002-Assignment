package models;

import controllers.PasswordUtilsController;

public abstract class Account {
	private String userID;
	private String name;
	private String password;
	private String role;
	
	public Account() {
		userID ="";
		name = "";
		password = PasswordUtilsController.hashPassword("password");
		role = "";
	}
	
	public Account(String uID,String name,String pass,String r) {
		this.userID = uID;
		this.name = name;
		this.password = PasswordUtilsController.hashPassword(pass);
		this.role = r;
	}
	
	public void displayMenu() {}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getID() {
		return this.userID;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setPassword(String pass) {
		this.password = PasswordUtilsController.hashPassword(pass);
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getPassword() {
		return this.password;
	}
}
