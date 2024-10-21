package HMS;

import java.util.*;
import java.io.*;


public class mainMenu {
	static Scanner sc = new Scanner(System.in);
	static ArrayList<Staff> staffList = new ArrayList<Staff>();
	
	public static void main(String[] args) throws Exception {
		
        String csvFile = "src/HMS/Staff_List.csv";  // Replace with your file path
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
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
                staffList.add(new Staff(values[0],"Password",values[2]));
                
                
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
   
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
                    LoginUI();
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



    public static void LoginUI() throws Exception{
        String username;
        String password;
        int success;
        int exitChoice=-3;
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
                    mainMenu.WelcomePage();
                    break;
            }
        }while(exitChoice != 2 && success<5);
        
        if (exitChoice == 2)
            mainMenu.WelcomePage();
    }

    private static int Login(String username, String password){
    	System.out.println("Login as staff/patient?");
    	String userType = sc.nextLine().toLowerCase();
    	int result =0;
    	switch(userType) {
    	case "patient":
    		break;
    	case "staff":
    		for(Staff staff : staffList) {
    			//System.out.println("Test: " + staff.getID() + " ; " + staff.getPassword() + " ; " + staff.getRole());
    			if(staff.getID().equals(username) && staff.getPassword().equals(password)) {
    				System.out.println("Success");
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
    	default:
    		System.out.println("Invalid user type. Please enter 'staff' or 'patient'.");
    	}
    	return result;
    }
  
}


     
    

    