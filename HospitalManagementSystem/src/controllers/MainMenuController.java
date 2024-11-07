package controllers;

import models.*;
import java.util.*;
import java.io.*;

public class MainMenuController {
	static Scanner sc = new Scanner(System.in);
	static ArrayList<Staff> staffList = new ArrayList<Staff>();
    static Account loggedInUser;
	
	public static void main(String[] args) throws Exception {
        //DataInit("data\\Staff_List.csv"); //Initialize Staff_List.csv into ArrayList
		WelcomePage();
	}

        @SuppressWarnings({"CallToPrintStackTrace", "UnnecessaryTemporaryOnConversionFromString"})
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
                staffList.add(new Staff(values[0], values[1],values[2],values[3]));
                staffList.get(staffList.size()-1).setGender(values[4]);
                staffList.get(staffList.size()-1).setAge(Integer.valueOf(values[5]));
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    private static int Login(String username, String password, int userType){
    	int result =0;
        boolean loggedIn = false;
    	switch(userType) {
            case 1:
    		for(Staff staff : staffList) {
    			//System.out.println("Test: " + staff.getID() + " ; " + staff.getPassword() + " ; " + staff.getRole());
                if(staff.getID().equals(username) &&  !password.equals("Password")){ //Not first-time users
                    System.out.println("Login Success!");
                    loggedIn = true;
                }
    			else if(staff.getID().equals(username) && 
                PasswordUtilsController.hashPassword(staff.getPassword()).equals(PasswordUtilsController.hashPassword(password))) { //First-time users
    				System.out.println("Login Success!");
                    if(PasswordUtilsController.hashPassword(staff.getPassword()).equals(PasswordUtilsController.hashPassword("Password"))){
                        System.out.println("First-Time login. Please change password.");
                        boolean passChangedSuccess = false;
                        while(!passChangedSuccess){
                            System.out.println("Please enter new password:");
                            String newPassword = sc.nextLine();
                            System.out.println(PasswordUtilsController.hashPassword(newPassword));
                            if(PasswordUtilsController.hashPassword(newPassword).equals(PasswordUtilsController.hashPassword("Password"))){
                                System.out.println("Invalid Password. Please try again!");
                            }
                            else{
                                System.out.println("Confirm new password:");
                                String cfmPassword = sc.nextLine();
                                if(PasswordUtilsController.hashPassword(newPassword).equals(PasswordUtilsController.hashPassword(cfmPassword))){
                                    passChangedSuccess = true;
                                    staff.setPassword(newPassword);
                                    System.out.println(PasswordUtilsController.hashPassword(cfmPassword));
                                    System.out.println("Password changed successfully!");
                                    CSVUtils.updateStaffInCSV("data/Staff_List.csv", staff);
                                    loggedIn = true;
                                }
                                else{
                                    System.out.println("Password does not match. Please try again.");
                                }
                            }
                        }
                    }
                }
                if(loggedIn){
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
            System.out.println("(1) Staff Login");
            System.out.println("(2) Patient Login");
            System.out.println("========================================");
            int userType = sc.nextInt();
            sc.nextLine();
            System.out.println("========================================");
            System.out.println("Please enter ID");
            username = sc.nextLine();
            System.out.println("Please enter password");
            password = sc.nextLine();
            success = Login(username, password,userType);
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
        DataInit("data\\Staff_List.csv"); //Initialize Staff_List.csv into ArrayList
        System.out.println("========================================");
        System.out.println("Welcome to Hospital Management System");
        int choice;
        do{
            try {
                System.out.println("========================================");
                System.out.println("(1) Login");
                System.out.println("(2) Exit");
                System.out.println("========================================");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        LoginPage();
                        break;
                    case 2:
                        System.out.println("Thank you for using HMS");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Input. Please enter an integer (1-3):");
                        break;
                }
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer: ");
            sc.next();
        }
        }while(true);
    }
  
}


     
    

    