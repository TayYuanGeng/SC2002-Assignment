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
 * This is the Administrator Controller that handles all the logic for the
 * Administrator account's possible actions. It includes methods for managing
 * staff, appointments, inventory, and replenishment requests.
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

    /** Variable to store the sorting criteria */
    static int sortBy = 0;
    
    /**
     * Main page that prints the User's name and directs them to the AdminPage() function.
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
     * Displays the Administrator Menu and provides access to various functionalities.
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
     * Displays and manages staff-related actions such as adding, removing, or editing staff.
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
     * Displays the staff list with sorting based on the selected criteria.
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
     * Allows sorting of staff by different attributes such as ID, Name, Role, etc.
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
     * Displays a menu to add a new staff member. This method collects input
     * for the staff's name, role, gender, and age through a series of prompts
     * and validation steps. After confirming the details, it calls the `addStaff`
     * method to save the new staff member to the system.
     *
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
     * Adds a new staff member to the system. This method generates a unique ID for
     * the staff member based on their role, creates a `Staff` object with the provided
     * details, and saves it to the staff database (CSV file).
     *
     * @param name   the name of the staff member
     * @param role   the role of the staff member (e.g., "Doctor", "Pharmacist", "Administrator")
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
     * Generates a unique ID for a new staff member based on their role. The ID
     * is created by checking the existing staff list, finding the highest current
     * ID for the specified role, and incrementing it.

     *
     * @param role the role of the staff member (e.g., "Doctor", "Pharmacist", "Administrator")
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
     * Displays a prompt to remove a staff member from the system. This method asks
     * the user to enter a staff ID, verifies its existence in the staff list, and
     * confirms the removal action with the user. If confirmed, the staff member is
     * removed from the system (CSV file).
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
     * Allows the user to edit the details of an existing staff member. The method prompts
     * the user to enter the staff ID, verifies if the staff exists, and provides a menu
     * to edit various attributes (Name, Password, Role, Gender, or Age). After making changes,
     * the updated staff details are saved to the system (CSV file).

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
     * Displays a list of all appointments. This method retrieves the appointment
     * data from the CSV file, initializes the `apptList`, and prints the details
     * of each appointment in a formatted table. For completed appointments, the
     * appointment outcome is also displayed.

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
     * Manages the inventory of medicines. This method provides the user with a menu
     * to perform various inventory management tasks, including sorting the medicine list,
     * adding new medicines, removing existing ones, and editing medicine details.

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
     * Prompts the user to select an attribute for sorting the medicine list.
     * The user can choose from three options: Name, Stock Level, or Low Level Alert.
     * Once a valid input is provided, the selected sort criterion is stored in
     * the {@code sortBy} variable for subsequent operations.

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
     * Displays the list of medicines in the inventory, sorted based on the selected
     * attribute. The sorting criterion is determined by the {@code sortBy} variable,
     * which is set in the {@code sortMedsDisplay()} method.

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
     * Displays a menu to add a new medicine to the inventory. The method collects
     * details about the medicine such as its name, stock level, low-level alert threshold,
     * and current amount. The user is given the opportunity to confirm or edit the details
     * before saving the medicine to the inventory.
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
     * Displays a prompt to remove a medicine from the inventory. The user is asked to
     * provide the name of the medicine to be removed. If the medicine exists in the list,
     * the user is prompted to confirm the removal. If confirmed, the medicine is deleted
     * from the inventory CSV file.
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
     * Allows the user to edit the details of an existing medicine in the inventory.
     * The method prompts the user to enter the name of the medicine to edit, verifies
     * its existence in the list, and provides a menu of editable attributes:
     * Name, Stock Level, and Low Level Alert.
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
     * Manages the replenishment requests for medicines. This method retrieves the
     * replenishment request data and the medicine inventory data from their respective
     * CSV files, displays the current list of pending requests, and provides options
     * to approve requests or return to the Administrator menu.
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
     * Displays the list of replenishment requests along with relevant details from the
     * medicine inventory. For each replenishment request, the method cross-references
     * the medicine inventory to display the current stock amount and the low-level alert
     * threshold.
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
     * Displays and processes pending replenishment requests. For each pending request,
     * the method cross-references the medicine inventory and allows the user to approve,
     * deny, or postpone the request. Approved requests update the current stock amount
     * in the inventory, while denied requests update the request's status.
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

    