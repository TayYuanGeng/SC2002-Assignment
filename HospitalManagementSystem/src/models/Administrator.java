package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



import controllers.AdministratorController;
import controllers.CSVUtilsController;
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
	
	public int setStockAmount() {
		return 0;
	}
	
	public int setLowLevelStockAmt() {
		return 0;
	}
	
	public int addStaffmember() {
		return 0;
	}
	
	public static int addStaff(String name, String role, String gender, int age) {
		
		String uid = generateID(role);
		Staff newStaff = new Staff(uid,name,passwordUtils.hashPassword("Password"),role);
		newStaff.setAge(age);
		newStaff.setGender(gender);
		csvUtils.saveStaffToCSV("src\\data\\Staff_List.csv", newStaff);
		return 1;
    }

	
	private static String generateID(String role) {
		readData("src\\data\\Staff_List.csv",1);
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
	
	private static void readData(String filePath,int listnum){
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
            	if(isFirstLine) {
            		isFirstLine = false;
            		continue;
            	}
                // Use comma as separator
                String[] values = line.split(csvSplitBy);
        		staffList.add(new Staff(values[0], values[1],values[2],values[3]));
        		staffList.get(staffList.size()-1).setGender(values[4]);
                staffList.get(staffList.size()-1).setAge(Integer.valueOf(values[5]));

                }
                
                System.out.println();
            }
         catch (IOException e) {
            e.printStackTrace();
        }
    }
}

