package models;


import java.util.ArrayList;
import java.util.Scanner;

import controllers.CSVUtilsController;
import controllers.MainMenuController;
import controllers.PasswordUtilsController;
import interfaces.CSVUtilsInterface;
import interfaces.PasswordUtilsInterface;

import java.net.PasswordAuthentication;



public class Administrator extends Staff {
	static PasswordUtilsInterface passwordUtils = new PasswordUtilsController();
	static CSVUtilsInterface csvUtils = new CSVUtilsController();
	static ArrayList<Staff> staffList = new ArrayList<Staff>();
	static Scanner sc = new Scanner(System.in);
	
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
	
	
	public static int addStaff(String name, String role, String gender, int age) {
		
		String uid = generateID(role);
		Staff newStaff = new Staff(uid,name,passwordUtils.hashPassword("Password"),role);
		newStaff.setAge(age);
		newStaff.setGender(gender);
		csvUtils.saveStaffToCSV(MainMenuController.CSV_FILE_PATH+"Staff_List.csv", newStaff);
		return 1;
    }

	
	private static String generateID(String role) {
		//readData("src\\data\\Staff_List.csv",1);
		staffList = csvUtils.StaffDataInit(MainMenuController.CSV_FILE_PATH+"Staff_List.csv",staffList);
		int maxid = 0;
		for(Staff staff : staffList) {
			if(staff.getRole().equals(role)) {
				int uid = Integer.parseInt(staff.getID().substring(1));
				System.out.println(uid);
				if(uid>maxid) {
					maxid = uid;
				}
			}
		}
		switch(role) {
			case "Doctor":
				return String.format("D%03d", maxid+1);
			case "Pharmacist":
				return String.format("P%03d", maxid+1);
			case "Administrator":
				return String.format("A%03d", maxid+1);
			default:
				System.out.println("Nothing Found");
				return "";
		}
	}
	
	
}

