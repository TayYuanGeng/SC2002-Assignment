package controllers;

import java.util.*;
import models.*;

// public class MainMenuController {
// 	static Scanner sc = new Scanner(System.in);
// 	static ArrayList<Staff> staffList = new ArrayList<Staff>();
//     static Account loggedInUser;
	
// 	public static void main(String[] args) throws Exception {
//         //StaffDataInit("data\\Staff_List.csv"); //Initialize Staff_List.csv into ArrayList
// 		WelcomePage();
// 	}
	
//     private static int Login(String username, String password, int userType){
//     	int result =0;
//         loggedInUser=null;
//     	switch(userType) {
//             case 1:
//     		for(Staff staff : staffList) {
//     			//System.out.println("Test: " + staff.getID()+ " ; " + staff.getName() + " ; " + staff.getPassword() + " ; " + staff.getRole() + " ; " + staff.getGender() + " ; " + staff.getAge());
//                 if(staff.getID().equals(username) &&  !password.equals("Password") && staff.getPassword().equals(PasswordUtilsController.hashPassword(password))){ //Not first-time users
//                     System.out.println("Login Success!");
//                     loggedInUser = staff; // Store logged in user here
//                 }
//     			else if(staff.getID().equals(username) && 
//                 PasswordUtilsController.hashPassword(staff.getPassword()).equals(PasswordUtilsController.hashPassword(password))) { //First-time users
//     				System.out.println("Login Success!");
//                     if(PasswordUtilsController.hashPassword(staff.getPassword()).equals(PasswordUtilsController.hashPassword("Password"))){
//                         System.out.println("First-Time login. Please change password.");
//                         boolean passChangedSuccess = false;
//                         while(!passChangedSuccess){
//                             System.out.println("Please enter new password:");
//                             String newPassword = sc.nextLine();
//                             System.out.println(PasswordUtilsController.hashPassword(newPassword));
//                             if(PasswordUtilsController.hashPassword(newPassword).equals(PasswordUtilsController.hashPassword("Password"))){
//                                 System.out.println("Invalid Password. Please try again!");
//                             }
//                             else{
//                                 System.out.println("Confirm new password:");
//                                 String cfmPassword = sc.nextLine();
//                                 if(PasswordUtilsController.hashPassword(newPassword).equals(PasswordUtilsController.hashPassword(cfmPassword))){
//                                     passChangedSuccess = true;
//                                     staff.setPassword(newPassword);
//                                     System.out.println(PasswordUtilsController.hashPassword(cfmPassword));
//                                     System.out.println("Password changed successfully!");
//                                     CSVUtils.updateStaffInCSV("data/Staff_List.csv", staff);
//                                     loggedInUser = staff; // Store logged in user here
//                                 }
//                                 else{
//                                     System.out.println("Password does not match. Please try again.");
//                                 }
//                             }
//                         }
//                     }
//                 }
//     		}
//             switch(loggedInUser.getRole()) {
//                 case "Administrator":
//                     result = 1;
//                     break;
//                 case "Doctor":
//                     result = 3;
//                     break;
//                 case "Pharmacist":
//                     result = 4;
//                     break;
//                 }
//     		break;
//             case 2:
//                 break;
//     	default:
//     		System.out.println("Invalid user type. Please enter '1' for staff or '2' for patient.");
//     	}
//     	return result;
//     }

//     public static void LoginPage() throws Exception{
//         String username;
//         String password;
//         int success;
//         int exitChoice=-1;
//         do{
//             System.out.println("========================================");
//             System.out.println("(1) Staff Login");
//             System.out.println("(2) Patient Login");
//             System.out.println("========================================");
//             int userType = sc.nextInt();
//             sc.nextLine();
//             System.out.println("========================================");
//             System.out.println("Please enter ID");
//             username = sc.nextLine();
//             System.out.println("Please enter password");
//             password = sc.nextLine();
//             success = Login(username, password,userType);
//             switch (success){
//                 case 0:
//                     System.out.println("Username or password is incorrect");
//                     System.out.println("========================================");
//                     System.out.println("(1) Try again");
//                     System.out.println("(2) Exit");
//                     exitChoice = sc.nextInt();
//                     sc.nextLine();
//                     break;
//                 case 1:
//                     //Administrator
//                     System.out.println("Administrator logged in");
//                     AdministratorController.main(loggedInUser);
//                     break;
//                 case 2:
//                     //Patient
//                     System.out.println("Patient logged in");
//                     break;
//                 case 3:
//                     //Doctor
//                     System.out.println("Doctor logged in");
//                     break;
//                 case 4:
//                     //Pharmacist
//                     System.out.println("Pharmacist logged in");
//                     break;
//                 default:
//                     WelcomePage();
//                     break;
//             }
//         }while(exitChoice != 2 && success<5);
        
//         if (exitChoice == 2)
//             WelcomePage();
//     }

//     public static void WelcomePage() throws Exception{
//         staffList = CSVUtils.StaffDataInit("data\\Staff_List.csv", staffList); //Initialize Staff_List.csv into ArrayList
//         System.out.println("========================================");
//         System.out.println("Welcome to Hospital Management System");
//         int choice;
//         do{
//             try {
//                 System.out.println("========================================");
//                 System.out.println("(1) Login");
//                 System.out.println("(2) Exit");
//                 System.out.println("========================================");
//                 choice = sc.nextInt();
//                 sc.nextLine();
//                 switch (choice) {
//                     case 1:
//                         LoginPage();
//                         break;
//                     case 2:
//                         System.out.println("Thank you for using HMS");
//                         System.exit(0);
//                     default:
//                         System.out.println("Invalid Input. Please enter an integer (1-3):");
//                         break;
//                 }
//             }
//             catch (Exception e) {
//             System.out.println("Invalid input. Please enter an integer: ");
//             sc.next();
//         }
//         }while(true);
//     }
  
// }

public class MainMenuController {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Staff> staffList = new ArrayList<>();
    static ArrayList<Patient> patientList = new ArrayList<>();
    static Account loggedInUser;

    public static void main(String[] args) throws Exception {
        WelcomePage();
    }

    private static void login(String username, String password, int userType) throws Exception {
        loggedInUser = null;

        switch (userType) {
            case 1:
                // Staff login
                for (Staff staff : staffList) {
                    if (staff.getID().equals(username)) {
                        if (isFirstTimeLogin(staff, password) && isValidPassword(staff, password)) {
                            handleFirstTimeLogin(staff);
                            loggedInUser = staff;
                        } else if (isValidPassword(staff, password)) {
                            System.out.println("Login Success!");
                            loggedInUser = staff;
                        }
                        break;
                    }
                }
                break;
            case 2:
                // Patient login
                for (Patient patient : patientList) {
                    System.out.println(patient.getID());
                    if (patient.getID().equals(username)) {
                        if (isFirstTimeLogin(patient, password) && isValidPassword(patient, password)) {
                            handleFirstTimeLogin(patient);
                            loggedInUser = patient;
                        } else if (isValidPassword(patient, password)) {
                            System.out.println("Login Success!");
                            loggedInUser = patient;
                        }
                        break;
                    }
                }   
                break;
            default:
                System.out.println("Invalid user type. Please enter '1' for Staff or '2' for Patient.");
                break;
        }
    }

    public static void LoginPage() throws Exception {
        int exitChoice = -1;
        int success;

        do {
            System.out.println("========================================");
            System.out.println("(1) Staff Login");
            System.out.println("(2) Patient Login");
            System.out.println("========================================");

            int userType = sc.nextInt();
            sc.nextLine(); // Clear the buffer

            System.out.println("Please enter ID:");
            String username = sc.nextLine();
            System.out.println("Please enter password:");
            String password = sc.nextLine();

            login(username, password, userType);
            if (loggedInUser == null) {
                System.out.println("Incorrect login. (1) Try again or (2) Exit:");
                exitChoice = sc.nextInt();
                sc.nextLine(); // Clear buffer
            } else{
                navigateByRole(loggedInUser.getRole());
            }
        } while (exitChoice != 2 && loggedInUser==null);
        
        if (exitChoice == 2) {
            WelcomePage();
        }
    }

    public static void WelcomePage() throws Exception {
        staffList = CSVUtils.StaffDataInit("SC2002-Assignment/HospitalManagementSystem/src/data/Staff_List.csv", staffList);
        patientList = CSVUtils.PatientDataInit("SC2002-Assignment/HospitalManagementSystem/src/data/Patient_List.csv", patientList);
        System.out.println("========================================");
        System.out.println("Welcome to Hospital Management System");

        int choice;
        do {
            System.out.println("(1) Login\n(2) Exit");
            choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    LoginPage();
                    break;
                case 2:
                    System.out.println("Thank you for using HMS");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input. Please enter 1 or 2.");
                    break;
            }
        } while (true);
    }

    private static boolean isValidPassword(Account account, String password) {
        if(account.getPassword().equals("Password")){
            return account.getPassword().equals(password);
        }
        return PasswordUtilsController.hashPassword(password).equals(account.getPassword());
    }

    private static boolean isFirstTimeLogin(Account account, String password) {
        //System.out.println("First time login" + (account.getPassword().equals("Password") && isValidPassword(account, password)));
        return account.getPassword().equals("Password");
    }

    private static void handleFirstTimeLogin(Account account) {
        System.out.println("First-Time login. Please change password.");
        boolean passChanged = false;

        while (!passChanged) {
            System.out.println("Please enter new password:");
            String newPassword = sc.nextLine();

            if(PasswordUtilsController.hashPassword(newPassword).equals(PasswordUtilsController.hashPassword("Password"))){
                System.out.println("Invalid Password. Please try again!");
                continue;
            }

            if (!PasswordUtilsController.hashPassword(newPassword).equals(account.getPassword())) {
                System.out.println("Confirm new password:");
                String confirmPassword = sc.nextLine();

                if (newPassword.equals(confirmPassword)) {
                    account.setPassword(newPassword);
                    System.out.println("Password changed successfully!");
                    
                    // Update in CSV based on account type
                    if (account instanceof Staff) {
                        CSVUtils.updateStaffInCSV("src/data/Staff_List.csv", (Staff) account);
                    } else if (account instanceof Patient) {
                        //CSVUtils.updatePatientInCSV("data/Patient_List.csv", (Patient) account);
                    }

                    passChanged = true;
                    loggedInUser = account;
                } else {
                    System.out.println("Passwords do not match. Try again.");
                }
            } else {
                System.out.println("New password cannot be the default password. Try again.");
            }
        }
    }

    private static int navigateByRole(String role) throws Exception {
        switch (role) {
            case "Administrator":
                System.out.println("Administrator logged in");
                AdministratorController.main(loggedInUser);
                return 1;
            case "Patient":
                System.out.println("Patient logged in");
                PatientController.main(loggedInUser);
                return 2;
            case "Doctor":
                System.out.println("Doctor logged in");
                DoctorController.main(loggedInUser);
                return 3;
            case "Pharmacist":
                System.out.println("Pharmacist logged in");
                PharmacistController.main(loggedInUser);
                return 4;
            default:
                System.out.println("Unknown role");
                return 0;
        }
    }
}
