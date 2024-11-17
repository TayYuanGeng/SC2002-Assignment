package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import interfaces.CSVUtilsInterface;
import interfaces.PasswordUtilsInterface;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.*;
import models.Appointment.AppointmentStatus;
import controllers.PasswordUtilsController;



/**
 * This is the Administrator Controller controls all the logic behind the possible actions for Administrator Users
 */
public class AdministratorController {
	/** Interface for CSV utilities to handle file operations */
    static CSVUtilsInterface csvUtils = new CSVUtilsController();

    /** Scanner object for user input */
    static Scanner sc = new Scanner(System.in);

    /** List of staff members */
    static ArrayList<Staff> staffList = new ArrayList<>();

    /** List of appointments */
    static List<Appointment> apptList = new ArrayList<>();

    /** List of medicines */
    static ArrayList<Medicine> medList = new ArrayList<>();

    /** List of replenishment requests */
    static ArrayList<ReplenishmentRequest> repReqList = new ArrayList<>();

    /** Interface for password utilities */
    static PasswordUtilsInterface passwordUtils = new PasswordUtilsController();

    /** Variable that sets how the displays should be sorted */
    static int sortBy = 0;
    
    /**
     * Main page that displays as a user logs in that will display their name and route them to AdminPage()
     *
     * @param loggedInUser the logged-in user account
     * @throws Exception if an error occurs during execution
     */
    public static void main(Account loggedInUser) throws Exception {
            // You now have access to the logged-in user here
            System.out.println("Welcome, " + loggedInUser.getName());
            
            // Additional administrator logic
            AdminPage();
    }

    /**
     * Displays the Administrator Menu that has actions an administrator can use
     *
     * @throws Exception if an error occurs during execution
     */
    public static void AdminPage() throws Exception{
        System.out.println("========================================");
        System.out.println("Welcome to Administrator Menu");
        int choice;
        do{
            try {
            	System.out.println("========================================");
                System.out.println("(1) Manage Staff");
                System.out.println("(2) View Appointments");
                System.out.println("(3) Inventory Management");
                System.out.println("(4) Replenishment Requests");
                System.out.println("(5) Sign out");
                System.out.println("(6) Exit Application");
                System.out.println("========================================");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        manageStaff();
                        break;
                    case 2:
                    	displayAppt();
                    	break;
                    case 3:
                        manageInv();
                        break;
                    case 4:
                    	manageRepReq();
                    	break;
                    case 5:
                    	MainMenuController.LoginPage();
                    	break;
                    case 6:
                        System.out.println("Thank you for using HMS");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Input. Please enter an integer (1-5):");
                        break;
                }
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer: ");
            sc.next();
        }
        }while(true);
    }
    
    
    
    /**
     * Displays the possible actions for staff management for Administrator
     */
    private static void manageStaff() {
    	int choice = 0;
        do{
            try {
            	//Clear Staff List
            	staffList.clear();
            	// populate the staff list
            	staffList = csvUtils.StaffDataInit(MainMenuController.CSV_FILE_PATH+"Staff_List.csv",staffList);
            	displayStaff();
                System.out.println("========================================");
                System.out.println("(1) Sort Staff List");
                System.out.println("(2) Add Staff");
                System.out.println("(3) Remove Staff");
                System.out.println("(4) Edit Staff");
                System.out.println("(5) Return to Admin Menu");
                System.out.println("========================================");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                    	//Display staff list sorted by choice
                        sortStaffDisplay();
                        break;
                    case 2: // add a new staff
                    	addStaffDisplay();
                        break;
                    case 3:// remove a current staff
                    	removeStaffDisplay();
                    	break;
                    case 4:// edit a current staff details
                    	editStaffDisplay();
                    	break;
                    case 5://return to default admin page
                    	AdminPage();
                    	break;
                    default:
                        System.out.println("Invalid Input. Please enter an integer (1-5):");
                        break;
                }
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer: ");
            sc.next();
        }
        }while(true);
        
    }

    /**
     * Displays the staff list based on the variable {@code sortBy}
     */
    private static void displayStaff() {
    	switch(sortBy) {
    		case 1:
    			Collections.sort(staffList, Comparator.comparing(staff->staff.getID().toLowerCase()));
    			break;
    		case 2:
    			Collections.sort(staffList, Comparator.comparing(staff->staff.getName().toLowerCase()));
    			break;
    		case 3:
    			Collections.sort(staffList, Comparator.comparing(staff->staff.getRole().toLowerCase()));
    			break;
    		case 4:
    			Collections.sort(staffList, Comparator.comparing(staff->staff.getGender().toLowerCase()));
    			break;
    		case 5:
    			Collections.sort(staffList, Comparator.comparing(staff->staff.getAge()));
    			break;
    		default:
    			break;
    	}
    	System.out.printf("%-8s%-20s%-20s%-10s%-5s\n","StaffID","Name","Role","Gender","Age");

    	for(Staff staff:staffList) {
        	System.out.printf("%-8s%-20s%-20s%-10s%-5s\n",staff.getID(),staff.getName(),staff.getRole(),staff.getGender(),staff.getAge());
        	
    	}
    }
    	
    /**
     * displays attributes and prompts the user for which attribute to sortby 
     */
    private static void sortStaffDisplay() {
    	do{
            try {
		    	System.out.println("Please select which attribute to sort by: ");
		    	System.out.println("(1) Staff ID");
		    	System.out.println("(2) Name");
		    	System.out.println("(3) Role");
		    	System.out.println("(4) Gender");
		    	System.out.println("(5) Age");
		    	int choice = sc.nextInt();
		    	if(choice > 0 && choice<6) {
		    		sortBy = choice;
		    		break;
		    	}
		    	System.out.println("Invalid input. Please enter a valid option ");
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid option ");
            sc.next();
        }
        }while(true);
    }
    
    
    /**
     * Displays a menu prompting the user for details of the new staff to add 
     * Confirming with the user at the end while still allowing them to 
     * go back and edit the details if needed
     * @throws Exception if the user provides invalid input or an error occurs during execution
     */
    private static void addStaffDisplay() throws Exception {
    	String name = "";
    	String role = "";
    	String gender = "";
    	int age=0;
    	int choice = 0;
    	do{
            try {
            	switch(choice) {
            		case 0:
                    	System.out.println("Please enter the Staff's name: ");
                    	name = sc.nextLine();
                    	System.out.println("Please choose the Staff's role: ");
            			System.out.println("(1) Doctor ");
            			System.out.println("(2) Pharmacist ");
            			System.out.println("(3) Administrator");
            			choice = sc.nextInt();
            			switch(choice) {
            				case 1:
            					role = "Doctor";
            					break;
            				case 2:
            					role = "Pharmacist";
            					break;
            				case 3:
            					role = "Administrator";
            					break;
            			}
            			sc.nextLine();
                    	System.out.println("Please enter the Staff's Gender: ");
                    	gender = sc.nextLine();
                    	System.out.println("Please enter the Staff's Age: ");
                    	age = sc.nextInt();
                    	choice= 99;
                    	sc.nextLine();
                    	
                    	break;
            		case 1:
            			break;
            		case 2:
            			System.out.println("Please enter the Staff's name: ");
                    	name = sc.nextLine();
                    	choice = 99;
                    	break;
            		case 3:
            			System.out.println("Please choose the Staff's role: ");
            			System.out.println("(1) Doctor ");
            			System.out.println("(2) Pharmacist ");
            			System.out.println("(3) Administrator");
            			choice = sc.nextInt();
            			switch(choice) {
            				case 1:
            					role = "Doctor";
            					break;
            				case 2:
            					role = "Pharmacist";
            					break;
            				case 3:
            					role = "Administrator";
            					break;
            			}
                    	sc.nextLine();
                    	choice= 99;
                    	break;
            		case 4:
            			System.out.println("Please enter the Staff's Gender: ");
                    	gender = sc.nextLine();
                    	choice = 99;
                    	break;
            		case 5:
            			System.out.println("Please enter the Staff's Age: ");
                    	age = sc.nextInt();
                    	sc.nextLine();
                    	choice = 99;
                    	break;
            		case 99:
            			System.out.println("Please confirm the Staff's details:");
                    	System.out.println(name + " " + role + " " + gender + " " + String.valueOf(age));
                    	System.out.println("(1) Confirm (2) Edit name (3) Edit Role (4) Edit Gender (5) Edit Age ");
                    	choice = sc.nextInt();
                    	sc.nextLine();
                    	break;
                    default:
                    	System.out.println("Invalid Option Please choose one of the options: ");
                    	
                    	
            	}
            	
            	if(choice ==1)
            		break;
            }
            catch (Exception e) {
                    System.out.println("Invalid input. Please enter the correct values ");
                    sc.next();
            	}
            	
            }while(true);
    	addStaff(name,role,gender,age);
		System.out.println("Staff Successfully added");
    }
    
    /**
     * Adds the staff member with the details provided in the method parameters into the csv file 
     * @param name   the name of the staff member
     * @param role   the role of the staff member 
     * @param gender the gender of the staff member
     * @param age    the age of the staff member
     * @return 1 if the staff member was successfully added
     */
    public static int addStaff(String name, String role, String gender, int age) {
		
		String uid = generateID(role);
		Staff newStaff = new Staff(uid,name,passwordUtils.hashPassword("Password"),role);
		newStaff.setAge(age);
		newStaff.setGender(gender);
		csvUtils.saveStaffToCSV(MainMenuController.CSV_FILE_PATH+"Staff_List.csv", newStaff);
		return 1;
    }

    
    /**
     *Generates a unique ID for staff member based on their role by checking the existing list for unused IDs, 
     *Pxxx for Pharmacists, Dxxx for Doctors and Axxx for Administrators
     *
     * @param role the role of the staff member 
     * @return a unique ID in the format corresponding to the role, or an empty string if the role is invalid
     */
    private static String generateID(String role) {
        // Initialize the staff list by reading from the CSV file
        staffList = csvUtils.StaffDataInit(MainMenuController.CSV_FILE_PATH + "Staff_List.csv", staffList);

        // Track the maximum ID for the given role
        int maxid = 0;

        // Iterate through the staff list to find the highest ID for the specified role
        for (Staff staff : staffList) {
            if (staff.getRole().equals(role)) {
                int uid = Integer.parseInt(staff.getID().substring(1)); // Extract numeric part of the ID
                System.out.println(uid); // Debug: Print the current ID
                if (uid > maxid) {
                    maxid = uid; // Update the maximum ID
                }
            }
        }

        // Generate the new ID based on the role and the maximum ID found
        switch (role) {
            case "Doctor":
                return String.format("D%03d", maxid + 1);
            case "Pharmacist":
                return String.format("P%03d", maxid + 1);
            case "Administrator":
                return String.format("A%03d", maxid + 1);
            default:
                System.out.println("Nothing Found"); // Debug: Invalid role
                return ""; // Return an empty string for invalid roles
        }
    }
    
    
    /**
     * Prompts the user for the staffID that the user wants to remove,
     * confirms with the user after and if confirmed, the staffID entered is removed from csv
     */
    private static void removeStaffDisplay() {
        int choice = 0;

        // Prompt the user to enter the ID of the staff member to remove
        System.out.println("Please enter the staff ID you would like to remove: ");
        String removeId = sc.nextLine();

        // Iterate through the staff list to find the staff member with the entered ID
        for (Staff staff : staffList) {
            if (staff.getID().equals(removeId)) {
                // Display the found staff member's details and confirm removal
                System.out.println(String.format("Would you like to remove %s %s from the Staff List", staff.getID(), staff.getName()));
                System.out.println("(1) Yes (2) No");
                choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    // Remove the staff member from the CSV file
                    csvUtils.removeStaffInCSV(MainMenuController.CSV_FILE_PATH + "Staff_List.csv", staff);
                    break;
                }
            }
        }
    }

    /**
     * Prompts the user for the StaffID of the staff they want to edit.
     * After the staffID they entered is found confirm with the user if that is the staff they want to edit.
	 * If confirmed prompt the user for which details they want to edit then confirm again before updating the staff in csv with the new Details.
     */
    private static void editStaffDisplay() {
    	int choice =0;
    	String input = "";
    	// Prompt the user to enter the ID of the staff they want to edit
    	System.out.println("Please enter the staff ID you would like to Edit: ");
    	String removeId = sc.nextLine();
    	// for every staff in the staff list
    	for(Staff staff:staffList) {
    		//if their id is the same as the one entered
    		if(staff.getID().equals(removeId)) {
    			do {
    				try {
    					//Prompt the user what they want to edit from the current staff information
    					System.out.println(String.format("What would you like to edit from %s %s",staff.getID(),staff.getName()));
    	    			System.out.println("(1) Name \n(2) Password \n(3) Role \n(4) Gender \n(5) Age \n(6) Don't edit");
    	    			choice = sc.nextInt();
    	    			sc.nextLine();
    					switch(choice) {
        					case 1:// User selected to edit name
        						System.out.println("Please enter the new Name:");
        						input = sc.nextLine();
        						staff.setName(input);
        						break;
        					case 2://User selected to edit password
        						System.out.println("Please enter the new Password:");
        						input = sc.nextLine();
        						staff.setPassword(input);
        						break;
        					case 3: //User selected to edit role
        						System.out.println("Please enter the new Role:");
        						input = sc.nextLine();
        						staff.setRole(input);
        						break;
        					case 4: //User selected to edit Gender
        						System.out.println("Please enter the new Gender:");
        						input = sc.nextLine();
        						staff.setGender(input);
        						break;
        					case 5: //User selected to edit Age
        						System.out.println("Please enter the new Age:");
        						int age = sc.nextInt();
        						sc.next();
        						staff.setAge(age);
        						break;
        					case 6:
        					default:
        						//If option selected not present inform user wrong option
        						System.out.println("Invalid input. Please enter a valid option");
    					}
    					if(choice>0 && choice <6) {
    						//If user chose to edit the details
    						//Update the staff details in the csv
    						csvUtils.updateStaffInCSV(MainMenuController.CSV_FILE_PATH+"Staff_List.csv",staff);
    						break;
    					}else if(choice == 6){
    						//if user chose not to edit leave the menu
    						break;
    					}
    				}catch (Exception e) {
        	            System.out.println("Invalid input. Please enter a valid option ");
        	            sc.next();
        	            }
    			}while(true);
    		}
    	}
    }
    
    /**
     * Displays all the appointments loaded from the CSV file,
     * if the appointment status is Completed print retrieve their apptoutcome and print it
     */
    private static void displayAppt() {
    	apptList = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH+"ApptRequest.csv");
    	System.out.printf("%-10s%-11s%-20s%-18s%-19s%n","DoctorID","PatientID","Appointment Status","Date and Time","Appointment Outcome"); 
    	
    	for (Appointment appt : apptList) { // for every appointment in appointment list

    		//print out Doctor and Patient ID , Appointment Status and the Appointment DateTime
    		System.out.printf("%-10s%-11s%-20s%-18s",appt.getDoctorID(), appt.getPatientID(), appt.getAppointmentStatus(), appt.getAppointmentDateTime()); 
    		// If Appointment Status is completed 
    		if(appt.getAppointmentStatus()==Appointment.AppointmentStatus.valueOf("COMPLETED"))
    			//print Appointment Outcome
    			System.out.printf("%-19s%n",appt.getOutcome());
    		else
    			//Print next line
    			System.out.println();
        }
    	
    }
    
    /**
     * Prints the list of actions the user can do with their Medicine inventory
	 * and prompt them for which action they want to take.
     *
     * @throws Exception if an error occurs during menu operations or invalid input is provided
     */
    private static void manageInv() throws Exception{
    	int choice = 0;
        do{
            try {
            	medList.clear();
            	medList = csvUtils.MedicineDataInit(MainMenuController.CSV_FILE_PATH+"Medicine_List.csv");
            	displayMedicine();
                System.out.println("========================================");
                System.out.println("(1) Sort Medicine List");
                System.out.println("(2) Add Medicine");
                System.out.println("(3) Remove Medicine");
                System.out.println("(4) Edit Medicine");
                System.out.println("(5) Return to Admin Menu");
                System.out.println("========================================");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                    	//Display staff list sorted by choice
                    	sortMedsDisplay();
                        break;
                    case 2:
                    	addMedDisplay();
                        break;
                    case 3:
                    	removeMedDisplay();
                    	break;
                    case 4:
                    	editMedDisplay();
                    	break;
                    case 5:
                    	AdminPage();
                    	break;
                    default:
                        System.out.println("Invalid Input. Please enter an integer (1-5):");
                        break;
                }
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer: ");
            sc.next();
        }
        }while(true);
    }
    
    /**
     * Prompt the user for which attribute they want to sort the Medicine List by
     * then updates the {@code sortBy} variable to display the Medicine according to their selection.
     */
    private static void sortMedsDisplay() {
    	do{
            try {
            	//Display ways of sorting the medicine
		    	System.out.println("Please select which attribute to sort by: ");
		    	System.out.println("(1) Name");
		    	System.out.println("(2) Stock Level");
		    	System.out.println("(3) Low Level Alert");
		    	
		    	int choice = sc.nextInt();
		    	if(choice > 0 && choice<4) {//If user selected options listed update the display list option
		    		sortBy = choice;
		    		break;
		    	}
		    	System.out.println("Invalid input. Please enter a valid option "); // else print invalid option
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid option ");// if error request for another use input
            sc.next();
        }
        }while(true);
    }
    
    
    /**
     * Prints the Medicine List based on the {@code sortBy}.
     * Providing the Name, Stock to Level, Low Level Alert and Current amount of the medicines

     */
    private static void displayMedicine() {
    	//based on the sortBy variable determine which way to sort the medicine list medList
    	switch(sortBy) {
    		case 1: // sort by name
    			Collections.sort(medList, Comparator.comparing(medicine->medicine.getName().toLowerCase()));
    			break;
    		case 2: // sort by Current amount of medicine
    			Collections.sort(medList, Comparator.comparing(medicine->medicine.getCurrentAmount()));
    			break;
    		case 3: // sort by their low level stock amount
    			Collections.sort(medList, Comparator.comparing(medicine->medicine.getLowLvlStockAmt()));
    			break;
    		default:
    			break;
    	}
    	//Print the header for the lists
    	System.out.printf("%-20s%-15s%-20s%-20s\n","Name","Stock Level","Low Level Alert","Current Amount");
    	// for every medicine in medlist print out their name, amount to stock to, low level alert and current amount
    	for(Medicine medicine:medList) {
        	System.out.printf("%-20s%-15s%-20s%-20s\n",medicine.getName(),medicine.getStockAmt(),medicine.getLowLvlStockAmt(),medicine.getCurrentAmount());
        	
    	}
    }
    
    /**
     * Display menu prompting the user to enter the attributes of the medicine and confirming with them after.
     * Providing them an option to edit any of the attributes before adding the medicine into the CSV.
     */
    private static void addMedDisplay() {
    	String name = "";
    	int stockAmt = 0;
    	int lowlvl =0;
    	int currentAmt = 0;
    	int choice = 0;
    	do{
            try {
            	//based on the user's choice, request input for different Medicine's attribute
            	switch(choice) {
            		case 0:// starting case, input all details
                    	System.out.println("Please enter the Medicine's name: ");
                    	name = sc.nextLine();
                    	System.out.println("Please enter the Medicine's Level to stock to: ");
                    	stockAmt = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Please enter the Medicine's Low Level Alert: ");
                    	lowlvl = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Please enter the Medicine's Current Amount: ");
                    	currentAmt = sc.nextInt();
                    	sc.nextLine();
                    	choice= 99; // set choice to done case
                    	break;
            		case 1: // dont want to edit so break
            			break;
            			
            		case 2:// want to edit medicine name 
            			System.out.println("Please enter the Medicine's name: ");
                    	name = sc.nextLine();
                    	choice = 99; // set choice to done case
                    	break;
                    	
            		case 3://want to edit stock level the amount to stock to
            			System.out.println("Please enter the Medicine's Level to stock to: ");
                    	stockAmt = sc.nextInt();
                    	sc.nextLine();
                    	choice= 99;// set choice to done case
                    	break;
                    	
            		case 4://want to edit Low level alert
            			System.out.println("Please enter the Medicine's Low Level Alert: ");
                    	lowlvl = sc.nextInt();
                    	sc.nextLine();
                    	choice = 99;// set choice to done case
                    	break;
                    	
            		case 5:// want to edit current amount
            			System.out.println("Please enter the Medicine's Current Amount: ");
            			currentAmt = sc.nextInt();
                    	sc.nextLine();
                    	choice= 99;// set choice to done case
                    	break;
                    	
            		case 99://Done case prompt user for confirmation or if they want to re-edit everything
            			System.out.println("Please confirm the Medicine's details:");
                    	System.out.println(name + " " + String.valueOf(stockAmt) + " " + String.valueOf(lowlvl)+ " " + String.valueOf(currentAmt));
                    	System.out.println("(1) Confirm (2) Edit name (3) Edit Stock Level (4) Edit Low Level Alert (5) Edit Current Amount");
                    	choice = sc.nextInt();
                    	sc.nextLine();
                    	break;
                    default:// default case invalid option try again
                    	System.out.println("Invalid Option Please choose one of the options: ");
                    	sc.nextLine();
                    	
            	}
            	
            	if(choice ==1)
            		break; // if User confirmed break the while loop
            }
            catch (Exception e) {
                    System.out.println("Invalid input. Please enter the correct values ");
                    sc.next();
            	}
            	
            }while(true);
    	//create new medicine obj called med
    	Medicine med = new Medicine(name,stockAmt,lowlvl,currentAmt);
    	// add the new medicine object to the CSV
    	csvUtils.saveMedToCSV(MainMenuController.CSV_FILE_PATH+"Medicine_List.csv", med);
		System.out.println("Medicine Successfully added");
    }
    
    /**
     * Prompts the user for the name of the medicine they want to remove.
     * From the Medicine List csv find the medicine, if found confirm with the user if they want to remove it.
     * Remove the Medicine if the user confirmed it.
     */
    private static void removeMedDisplay() {
    	int choice =0;
    	//Prompt user for the name of medicine to remove
    	System.out.println("Please enter the Medicine Name you would like to remove: ");
    	String removeName = sc.nextLine();
    	//For every medicine in medicine List
    	for(Medicine med:medList) {
    		//if the medicine name the user entered is found 
    		if(med.getName().equals(removeName)) {
    			//confirm the medicine with the user
    			System.out.println(String.format("Would you like to remove %s %s from the Medicine List",med.getName(),med.getCurrentAmount()));
    			System.out.println("(1) Yes (2) No");
    			choice = sc.nextInt();
    			sc.nextLine();
    			if(choice == 1) {
    				//if user is sure remove the medicine
    				csvUtils.removeMedInCSV(MainMenuController.CSV_FILE_PATH+"Medicine_List.csv",med);
        			break;
    			}
    		}
    	}
    	
    }
    
    /**
     * Prompts the user to enter the name of the Medicine they want to edit.
     * For every medicine in the list if the medicine is found prompt the user which attribute they would like to edit
     * Update the csv with the new Details of the medicine edited.
     */
    private static void editMedDisplay() {
    	int choice =0;
    	String input = "";
    	//Prompt user for name of medicine that they want to edit
    	System.out.println("Please enter the Medicine name you would like to edit: ");
    	String removeName = sc.nextLine();
    	// for every medicine in the medicine list
    	for(Medicine med:medList) {
    		//if the name the user entered is found
    		if(med.getName().equals(removeName)) {
    			do {
    				try {
    					//Prompt the user for which details they want to edit
    					System.out.println(String.format("What would you like to edit from %s %s",med.getName(),med.getStockAmt()));
    	    			System.out.println("(1) Name \n(2) Stock Level \n(3) Low Level Alert \n(4) Don't edit");
    	    			choice = sc.nextInt();
    	    			sc.nextLine();
    	    			
    					switch(choice) {
        					case 1: // User selected to edit medicine name
        						System.out.println("Please enter the new Name:");
        						input = sc.nextLine();
        						med.setName(input);
        						break;
        					case 2:// User selected to edit medicine stock to level
        						System.out.println("Please enter the new Level to stock to:");
        						int stockAmt = sc.nextInt();
        						sc.nextLine();
        						med.setStockAmt(stockAmt);
        						break;
        					case 3:// user selected to edit Low Level Alert
        						System.out.println("Please enter the new Low Level Alert:");
        						int lowstockAmt = sc.nextInt();
        						sc.nextLine();
        						med.setLowLvlStockAmt(lowstockAmt);
        						break;
        					case 4:
        						break;
        					default:
        						System.out.println("Invalid input. Please enter a valid option");
    					}
    					if(choice>0 && choice <4) {
    						//if user updated any of the details update the medicine csv file
    						csvUtils.updateMedInCSV(MainMenuController.CSV_FILE_PATH+"Medicine_List.csv",med);
    						break;
    					}else if(choice == 4){
    						// if they dont want to edit, leave the menu
    						break;
    					}
    				}catch (Exception e) {
        	            System.out.println("Invalid input. Please enter a valid option ");
        	            sc.next();
        	            }
    			}while(true);
    		}
    	}
    }
    

    /**
     * Prompts the user if they would like to approve any replenishment requests.
     * Redirects them to the approve page if they would like to or back to the main menu
     *
     * @throws Exception if an error occurs during menu execution or invalid input is provided.
     */
    private static void manageRepReq() throws Exception {
    	int choice = 0;
        do{
            try {
            	repReqList.clear();
            	medList.clear();
            	repReqList = csvUtils.ReadReplenishRequestCSV(MainMenuController.CSV_FILE_PATH+"ReplenishRequest_List.csv");
            	medList = csvUtils.MedicineDataInit(MainMenuController.CSV_FILE_PATH+"Medicine_List.csv");
            	//display the replenishment requests
            	displayRepReq();
                System.out.println("========================================");
                System.out.println("(1) Approve Requests");
                System.out.println("(2) Return to Admin Menu");
                System.out.println("========================================");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                    	//Display pending request list sorted by choice
                        approveReqDisplay();
                        break;
                    case 2:
                    	// leave back to admin page
                    	AdminPage();
                    	break;
                    default:
                        System.out.println("Invalid Input. Please enter an integer (1-2):");
                        break;
                }
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer: ");
            sc.next();
        }
        }while(true);
    }
    
    /**
     * Displays the list of replenishment requests.
     * Providing the ID of request, Medicine Name, Status of request, current amount of Medicine and Low Level Alert of Medicine.
     */
    private static void displayRepReq() {
    	//print the headers for details of replenishment request
    	System.out.printf("%-5s%-20s%-15s%-16s%-18s\n","ID","Medicine Name","Status", "Current Amount" , "Low Level Alert");
    	//for every replenishment request in the list
    	for(ReplenishmentRequest req:repReqList) {
    		//for every medicine in medicinelist
    		for(Medicine med:medList) {
    			//if the medicine name is equal to the replenishment request name
    			if(med.getName().equals(req.getName())) {
    				//display the replenishment request id, medicine name, replenishment status, current amount of the medicine, and the low level stock amount
    				System.out.printf("%-5s%-20s%-15s%-16s%-18s\n",req.getRequestID(),req.getName(),req.getReplenishmentStatus(),med.getCurrentAmount(),med.getLowLvlStockAmt());
    			}
    		}
        	
    	}
    }
    
    /**
     * Displays the List of replenishment requests that have status PENDING.
     * Prompt the user if they would like to Approve, Deny or ignore the request for now.
     * If approved update replenishment request and Medicine csv file with the new stock value and change the replenishment request status to COMPLETED.
     * If denied/cancelled change the replenishment request status to CANCELLED and update CSV
     */
    private static void approveReqDisplay() {
    	int choice = 0;
    	//Print out the headers for the request
    	System.out.printf("%-5s%-20s%-15s%-16s%-18s%-18s\n","ID","Medicine Name","Status", "Current Amount" , "Low Level Alert", "Total after Replenishment");
    	//for every request in replenishment request list
    	for(ReplenishmentRequest req:repReqList) {
    		//for every medicine in medicinelist
    		for(Medicine med:medList) {
    			//if the request name is the same as the medicine name and the replenishment request status is PENDING
    			if(med.getName().equals(req.getName()) && req.getReplenishmentStatus().equals(ReplenishmentRequest.ReplenishmentStatus.PENDING)) {
    				//Prompt the user if they want to approve, deny or decide later for the request
    				System.out.printf("%-5s%-20s%-15s%-16s%-18s%-18s\n",req.getRequestID(),req.getName(),req.getReplenishmentStatus(),med.getCurrentAmount(),med.getLowLvlStockAmt(),med.getStockAmt());
    				System.out.println("(1) Approve (2) Deny/Cancel (3) Maybe Later");
    				choice = sc.nextInt();
    				sc.nextLine();
    				switch(choice) {
    					case 1: //if user approves the request
    						// set replenishment status to completed
    						req.setReplenishmentStatus(ReplenishmentRequest.ReplenishmentStatus.COMPLETED);
    						//set the current amount to the amount to stock level
    						med.setCurrentAmt(med.getStockAmt());
    						//update the replenishment request in csv
    						csvUtils.updateRepReqInCSV(MainMenuController.CSV_FILE_PATH+"ReplenishRequest_List.csv", req);
    						//update the medicine in csv
    						csvUtils.updateMedInCSV(MainMenuController.CSV_FILE_PATH+"Medicine_List.csv", med);
    						System.out.println("Stock for " + med.getName()+" has been replenished to "+ med.getCurrentAmount() + "/" + med.getStockAmt());
    						break;
    					case 2:
    						//user chose to cancel replenishment request
    						//set request to CANCELLED
    						req.setReplenishmentStatus(ReplenishmentRequest.ReplenishmentStatus.CANCELLED);
    						//update replenishment request status in csv
    						csvUtils.updateRepReqInCSV(MainMenuController.CSV_FILE_PATH+"ReplenishRequest_List.csv", req);
    						System.out.println("Replenishment Request for "+ med.getName() +" has been denied");
    						break;
    					case 3:
    						//user chose to decide next time so continue on with the list
    						continue;
    						
    				}
    				
    			}
    		}
        	
    	}
    }

}

    