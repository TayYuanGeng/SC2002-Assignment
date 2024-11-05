package controllers;

import models.*;
import java.util.*;
import java.io.*;

public class MainMenuController {
	static Scanner sc = new Scanner(System.in);
	static ArrayList<Staff> staffList = new ArrayList<Staff>();
    static Account loggedInUser;
	
	public static void main(String[] args) throws Exception {
        DataInit("SC2002-Assignment\\HospitalManagementSystem\\src\\data\\Staff_List.csv"); //Initialize Staff_List.csv into ArrayList
		WelcomePage();
	}

    private static void DataInit(String filePath){
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
                
                // Example: Print the values
                for (String value : values) {
                    System.out.print(value + " ");
                }
                staffList.add(new Staff(values[0], values[1],"Password",values[2]));
                
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    private static int Login(String username, String password){
        System.out.println("========================================");
        System.out.println("(1) Staff Login");
        System.out.println("(2) Patient Login");
        System.out.println("========================================");
    	int userType = sc.nextInt();
    	int result =0;
    	switch(userType) {
            case 1:
    		for(Staff staff : staffList) {
    			//System.out.println("Test: " + staff.getID() + " ; " + staff.getPassword() + " ; " + staff.getRole());
    			if(staff.getID().equals(username) && 
                PasswordUtilsController.hashPassword(staff.getPassword()).equals(PasswordUtilsController.hashPassword(password))) {
    				System.out.println("Success");
                    loggedInUser = staff; // Store logged in user here
    				switch(staff.getRole()) {
    				case "Administrator":
    					result = 1;
    					break;
    				case "Doctor":
    					result = 3;
    					break;
    				case "Pharmacist":
    					result = 4;
    					break;
    				}
    			}
    		}
    		break;
            case 2:
                break;
    	default:
    		System.out.println("Invalid user type. Please enter '1' for staff or '2' for patient.");
    	}
    	return result;
    }

    public static void LoginPage() throws Exception{
        String username;
        String password;
        int success;
        int exitChoice=-1;
        do{
            System.out.println("========================================");
            System.out.println("Please enter ID");
            username = sc.nextLine();
            System.out.println("Please enter password");
            password = sc.nextLine();
    
            success = Login(username, password);
            switch (success){
                case 0:
                    System.out.println("Username or password is incorrect");
                    System.out.println("========================================");
                    System.out.println("(1) Try again");
                    System.out.println("(2) Exit");
                    exitChoice = sc.nextInt();
                    sc.nextLine();
                    break;
                case 1:
                    //Administrator
                    System.out.println("Administrator logged in");
                    AdministratorController.main(loggedInUser);
                    break;
                case 2:
                    //Patient
                    System.out.println("Patient logged in");
                    break;
                case 3:
                    //Doctor
                    System.out.println("Doctor logged in");
                    break;
                case 4:
                    //Pharmacist
                    System.out.println("Pharmacist logged in");
                    break;
                default:
                    WelcomePage();
                    break;
            }
        }while(exitChoice != 2 && success<5);
        
        if (exitChoice == 2)
            WelcomePage();
    }

    public static void WelcomePage() throws Exception{
        System.out.println("========================================");
        System.out.println("Welcome to Hospital Management System");
        int choice = 0;
        do{
            try {
                System.out.println("========================================");
                System.out.println("(1) Login");
                System.out.println("(2) Exit");
                System.out.println("========================================");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 1) {
                    LoginPage();
                }
                else if (choice == 2){
                    System.out.println("Thank you for using HMS");
                    System.exit(0);
                }
                else{
                    System.out.println("Invalid Input. Please enter an integer (1-3):");
                }
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer: ");
            sc.next();
        }
        }while(true);
    }
  
}


     
    

    