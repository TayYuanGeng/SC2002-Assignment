package HMS;

import java.util.*;


public class mainMenu {
	
	public static void main(String[] args) throws Exception {
		WelcomePage();
	}
	
	
    public static void WelcomePage() throws Exception{
        System.out.println("========================================");
        System.out.println("Welcome to Hospital Management System");
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do{
            try {
                System.out.println("========================================");
                System.out.println("(1) Login");
                System.out.println("(2) Exit");
                System.out.println("========================================");
                choice = sc.nextInt();
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
        Scanner sc = new Scanner(System.in);
        String username;
        String password;
        int success;
        int exitChoice=-3;
        do{
            System.out.println("========================================");
            System.out.println("Please enter username");
            username = sc.next();
            System.out.println("Please enter password");
            password = sc.next();

            success = Login(username, password);
            switch (success){
                case 0:
                    System.out.println("Username or password is incorrect");
                    System.out.println("========================================");
                    System.out.println("(1) Try again");
                    System.out.println("(2) Exit");
                    exitChoice = sc.nextInt();
                    break;
                case 1:
                    //Administrator
                    break;
                case 2:
                	//Patient
                    break;
                case 3:
                	//Doctor
                    break;
                case 4:
                    //Pharmacist
                    break;
                default:
                    mainMenu.WelcomePage();
                    break;
            }
        }while(exitChoice != 2 && success<5);
        
        if (exitChoice == 2)
            mainMenu.WelcomePage();
    }

    /**
     * Checks if the user is an admin, if not check if user is customer, else user is guest. 
     * Proceeds to validate if password matches that of the username.
     * @param username entered username
     * @param password entered password
     * @return 0 if password is wrong, 1 if log in successfully as admin, 
     * 2 if log in successfully as customer, 3 if user is guest, and 4 if username is not found
     */
    private static int Login(String username, String password){
        //Check from CSV
    	//System.out.println("Logged in successfully");
    	Administrator admin = new Administrator("admin01","password","Admin");
    	
        //If does not exist
    	System.out.println("Sorry, this username does not exist");
    	
    	return 0;
    }
}


     
    

    