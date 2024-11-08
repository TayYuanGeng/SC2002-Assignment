package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import models.*;
import models.Appointment.AppointmentStatus;



public class AdministratorController {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Staff> staffList = new ArrayList<Staff>();
    static ArrayList<Appointment> apptList = new ArrayList<Appointment>();
    static ArrayList<Medicine> medList = new ArrayList<Medicine>();
    static ArrayList<ReplenishmentRequest> repReqList = new ArrayList<ReplenishmentRequest>();
    static int sortBy = 0;
    
    public static void main(Account loggedInUser) throws Exception {
            // You now have access to the logged-in user here
            System.out.println("Welcome, " + loggedInUser.getName());
            
            // Additional administrator logic
            AdminPage();
    }

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
                System.out.println("(4) Replement Requests");
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
    
    
    
    private static void manageStaff() {
    	int choice = 0;
        do{
            try {
            	staffList.clear();
            	readData("src\\data\\Staff_List.csv",1);
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
                    case 2:
                    	addStaffDisplay();
                        break;
                    case 3:
                    	removeStaffDisplay();
                    	break;
                    case 4:
                    	editStaffDisplay();
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
    
    private static void addStaffDisplay() {
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
                    	choice = sc.nextInt();
                    	
            	}
            	
            	if(choice ==1)
            		break;
            }
            catch (Exception e) {
                    System.out.println("Invalid input. Please enter the correct values ");
                    sc.next();
            	}
            	
            }while(true);
    	Administrator.addStaff(name,role,gender,age);
		System.out.println("Staff Successfully added");
    }
    
    private static void removeStaffDisplay() {
    	int choice =0;
    	System.out.println("Please enter the staff ID you would like to remove: ");
    	String removeId = sc.nextLine();
    	for(Staff staff:staffList) {
    		if(staff.getID().equals(removeId)) {
    			System.out.println(String.format("Would you like to remove %s %s from the Staff List",staff.getID(),staff.getName()));
    			System.out.println("(1) Yes (2) No");
    			choice = sc.nextInt();
    			sc.nextLine();
    			if(choice == 1) {
    				CSVUtils.removeStaffInCSV("src\\data\\Staff_List.csv",staff);
        			break;
    			}
    		}
    	}
    	
    }
    
    private static void editStaffDisplay() {
    	int choice =0;
    	String input = "";
    	System.out.println("Please enter the staff ID you would like to Edit: ");
    	String removeId = sc.nextLine();
    	for(Staff staff:staffList) {
    		if(staff.getID().equals(removeId)) {
    			do {
    				try {
    					System.out.println(String.format("What would you like to edit from %s %s",staff.getID(),staff.getName()));
    	    			System.out.println("(1) Name \n(2) Password \n(3) Role \n(4) Gender \n(5) Age \n(6) Don't edit");
    	    			choice = sc.nextInt();
    	    			sc.nextLine();
    					switch(choice) {
        					case 1:
        						System.out.println("Please enter the new Name:");
        						input = sc.nextLine();
        						staff.setName(input);
        						break;
        					case 2:
        						System.out.println("Please enter the new Password:");
        						input = sc.nextLine();
        						staff.setPassword(input);
        						break;
        					case 3:
        						System.out.println("Please enter the new Role:");
        						input = sc.nextLine();
        						staff.setRole(input);
        						break;
        					case 4:
        						System.out.println("Please enter the new Gender:");
        						input = sc.nextLine();
        						staff.setGender(input);
        						break;
        					case 5:
        						System.out.println("Please enter the new Age:");
        						int age = sc.nextInt();
        						sc.next();
        						staff.setAge(age);
        						break;
        					case 6:
        					default:
        						System.out.println("Invalid input. Please enter a valid option");
    					}
    					if(choice>0 && choice <6) {
    						CSVUtils.updateStaffInCSV("src\\data\\Staff_List.csv",staff);
    						break;
    					}else if(choice == 6){
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
    
    private static void displayAppt() {
    	readData("src\\data\\ApptRequest.csv",2); //retrieve appointment details from csv
    	System.out.printf("%-10s%-11s%-20s%-18s%-19s%n","DoctorID","PatientID","Appointment Status","Date and Time","Appointment Outcome"); 
    	
    	for (Object obj : apptList) { // for every appointment in appointment list
    		Appointment appt = (Appointment) obj;
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
    
    private static void manageInv() {
    	int choice = 0;
        do{
            try {
            	medList.clear();
            	readData("src\\data\\Medicine_List.csv",3);
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
    
    private static void sortMedsDisplay() {
    	do{
            try {
		    	System.out.println("Please select which attribute to sort by: ");
		    	System.out.println("(1) Name");
		    	System.out.println("(2) Stock Level");
		    	System.out.println("(3) Low Level Alert");

		    	int choice = sc.nextInt();
		    	if(choice > 0 && choice<4) {
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
    
    private static void displayMedicine() {
    	switch(sortBy) {
    		case 1:
    			Collections.sort(medList, Comparator.comparing(medicine->medicine.getName().toLowerCase()));
    			break;
    		case 2:
    			Collections.sort(medList, Comparator.comparing(medicine->medicine.getCurrentAmount()));
    			break;
    		case 3:
    			Collections.sort(medList, Comparator.comparing(medicine->medicine.getLowLvlStockAmt()));
    			break;
    		default:
    			break;
    	}
    	System.out.printf("%-20s%-15s%-20s\n","Name","Stock Level","Low Level Alert");

    	for(Medicine medicine:medList) {
        	System.out.printf("%-20s%-15s%-20s\n",medicine.getName(),medicine.getStockAmt(),medicine.getLowLvlStockAmt());
        	
    	}
    }
    
    private static void addMedDisplay() {
    	String name = "";
    	int stockAmt = 0;
    	int lowlvl =0;
    	int currentAmt = 0;
    	int choice = 0;
    	do{
            try {
            	switch(choice) {
            		case 0:
                    	System.out.println("Please enter the Medicine's name: ");
                    	name = sc.nextLine();
                    	System.out.println("Please enter the Medicine's Stock Level: ");
                    	stockAmt = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Please enter the Medicine's Low Level Alert: ");
                    	lowlvl = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Please enter the Medicine's Current Amount: ");
                    	lowlvl = sc.nextInt();
                    	sc.nextLine();
                    	choice= 99;
                    	break;
            		case 1:
            			break;
            		case 2:
            			System.out.println("Please enter the Medicine's name: ");
                    	name = sc.nextLine();
                    	choice = 99;
                    	break;
            		case 3:
            			System.out.println("Please enter the Medicine's Stock Level: ");
                    	stockAmt = sc.nextInt();
                    	sc.nextLine();
                    	choice= 99;
                    	break;
            		case 4:
            			System.out.println("Please enter the Medicine's Low Level Alert: ");
                    	lowlvl = sc.nextInt();
                    	sc.nextLine();
                    	choice = 99;
                    	break;
            		case 5:
            			System.out.println("Please enter the Medicine's Current Amount: ");
                    	lowlvl = sc.nextInt();
                    	sc.nextLine();
                    	choice= 99;
                    	break;
            		case 99:
            			System.out.println("Please confirm the Medicine's details:");
                    	System.out.println(name + " " + String.valueOf(stockAmt) + " " + String.valueOf(lowlvl)+ " " + String.valueOf(currentAmt));
                    	System.out.println("(1) Confirm (2) Edit name (3) Edit Stock Level (4) Edit Low Level Alert (5) Edit Current Amount");
                    	choice = sc.nextInt();
                    	sc.nextLine();
                    	break;
                    default:
                    	System.out.println("Invalid Option Please choose one of the options: ");
                    	choice = sc.nextInt();
                    	
            	}
            	
            	if(choice ==1)
            		break;
            }
            catch (Exception e) {
                    System.out.println("Invalid input. Please enter the correct values ");
                    sc.next();
            	}
            	
            }while(true);
    	Medicine med = new Medicine(name,stockAmt,lowlvl,currentAmt);
    	CSVUtils.saveMedToCSV("src\\data\\Medicine_List.csv", med);
		System.out.println("Medicine Successfully added");
    }
    
    private static void removeMedDisplay() {
    	int choice =0;
    	System.out.println("Please enter the Medicine Name you would like to remove: ");
    	String removeName = sc.nextLine();
    	for(Medicine med:medList) {
    		if(med.getName().equals(removeName)) {
    			System.out.println(String.format("Would you like to remove %s %s from the Medicine List",med.getName(),med.getCurrentAmount()));
    			System.out.println("(1) Yes (2) No");
    			choice = sc.nextInt();
    			sc.nextLine();
    			if(choice == 1) {
    				CSVUtils.removeMedInCSV("src\\data\\Medicine_List.csv",med);
        			break;
    			}
    		}
    	}
    	
    }
    
    private static void editMedDisplay() {
    	int choice =0;
    	String input = "";
    	System.out.println("Please enter the Medicine name you would like to edit: ");
    	String removeName = sc.nextLine();
    	for(Medicine med:medList) {
    		if(med.getName().equals(removeName)) {
    			do {
    				try {
    					System.out.println(String.format("What would you like to edit from %s %s",med.getName(),med.getStockAmt()));
    	    			System.out.println("(1) Name \n(2) Stock Level \n(3) Low Level Alert \n(4) Don't edit");
    	    			choice = sc.nextInt();
    	    			sc.nextLine();
    					switch(choice) {
        					case 1:
        						System.out.println("Please enter the new Name:");
        						input = sc.nextLine();
        						med.setName(input);
        						break;
        					case 2:
        						System.out.println("Please enter the new Stock Level:");
        						int stockAmt = sc.nextInt();
        						sc.nextLine();
        						med.setStockAmt(stockAmt);
        						break;
        					case 3:
        						System.out.println("Please enter the new Role:");
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
    						CSVUtils.updateMedInCSV("src\\data\\Medicine_List.csv",med);
    						break;
    					}else if(choice == 4){
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
    
    private static void manageRepReq() {
    	int choice = 0;
        do{
            try {
            	repReqList.clear();
            	readData("src\\data\\ReplenishRequest_List.csv",1);
            	displayStaff();
                System.out.println("========================================");
                System.out.println("(1) Sort Staff List");
                System.out.println("(2) Add Request");
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
                    case 2:
                    	addStaffDisplay();
                        break;
                    case 3:
                    	removeStaffDisplay();
                    	break;
                    case 4:
                    	editStaffDisplay();
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
                
                switch(listnum) {
                	case 1:

                		staffList.add(new Staff(values[0], values[1],values[2],values[3]));
                		staffList.get(staffList.size()-1).setGender(values[4]);
                        staffList.get(staffList.size()-1).setAge(Integer.valueOf(values[5]));
                		break;
                	case 2:
                		apptList.add(new Appointment(values[0], values[1],values[2],Appointment.AppointmentStatus.valueOf(values[3])));
                		break;
                	case 3:
                		medList.add(new Medicine(values[0],Integer.valueOf(values[1]), Integer.valueOf(values[2]),Integer.valueOf(values[3])));
                		break;
                	case 4:
                		repReqList.add(new ReplenishmentRequest(Integer.valueOf(values[0]),values[1],ReplenishmentRequest.ReplenishmentStatus.valueOf(values[2])));
                }
                
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  

}

    