package models;

public class Staff extends Account{
	private String staffID;
	private String name;
	private String password;
	private String role;
	private String gender;
	private int age;
	
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
	
	public void setPassword(String pass) {
		super.setPassword(pass);
	};
	
	public String getPassword() {
		return super.getPassword();
	}
	
	public void setRole(String role) {
		super.setRole(role);
	}

	public void setGender(String gender) {
		this.gender = gender;
	};
	
	public String getGender() {
		return this.gender;
	}

	public void setAge(int age) {
		this.age = age;
	};
	
	public int getAge() {
		return this.age;
	}
}
