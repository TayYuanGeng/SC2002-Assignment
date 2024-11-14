package controllers;

import interfaces.CSVUtilsInterface;
import interfaces.PasswordUtilsInterface;
import java.util.*;
import models.*;

public class MainMenuController {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Staff> staffList = new ArrayList<>();
    static ArrayList<Patient> patientList = new ArrayList<>();
    static Account loggedInUser;
    static PasswordUtilsInterface passwordUtils = new PasswordUtilsController();
    public static CSVUtilsInterface csvUtils = new CSVUtilsController();
    public static final String CSV_FILE_PATH = "data\\"; //Edit this to your file path so you can run the code smoothly

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
                        if (passwordUtils.isFirstTimeLogin(staff, password) && passwordUtils.isValidPassword(staff, password)) {
                            loggedInUser = passwordUtils.handleFirstTimeLogin(staff);
                        } else if (passwordUtils.isValidPassword(staff, password)) {
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
                    if (patient.getID().equals(username)) {
                        if (passwordUtils.isFirstTimeLogin(patient, password) && passwordUtils.isValidPassword(patient, password)) {
                            loggedInUser = passwordUtils.handleFirstTimeLogin(patient);
                        } else if (passwordUtils.isValidPassword(patient, password)) {
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

        do {
            System.out.println("========================================");
            System.out.println("(1) Staff Login");
            System.out.println("(2) Patient Login");
            System.out.println("(3) Back");
            System.out.println("========================================");

            System.out.print("Please select an option: ");

            String username=null;
            String password=null;
            if (sc.hasNextInt()) {
                int userType = sc.nextInt();
                sc.nextLine(); // Clear the buffer

                switch (userType) {
                    case 1:
                    case 2:
                        System.out.println("Please enter ID:");
                        username = sc.nextLine();
                        System.out.println("Please enter password:");
                        password = sc.nextLine();
                        login(username, password, userType);
                        break;
                    case 3:
                        WelcomePage();
                        break;
                    default:
                        System.out.println("Invalid Input. Please enter 1 or 2.");
                        break;
                }
            } else {
                System.out.println("Invalid Input. Please enter a number (1 or 2).");
                sc.next(); // Clear the invalid input from the scanner buffer
            }

            //System.out.println(PasswordUtilsController.hashPassword(password));
            if (loggedInUser == null && username != null && password != null) {
                System.out.println("Incorrect login. (1) Try again or (2) Exit:");
                exitChoice = sc.nextInt();
                sc.nextLine(); // Clear buffer
            } else if(loggedInUser != null){
                navigateByRole(loggedInUser.getRole());
            }
        } while (exitChoice != 2 && loggedInUser==null);
        
        if (exitChoice == 2) {
            WelcomePage();
        }
    }

    public static void WelcomePage() throws Exception {
        // Absolute path "SC2002-Assignment/HospitalManagementSystem/src/data/Staff_List.csv"
        //"SC2002-Assignment/HospitalManagementSystem/src/data/Patient_List.csv"
        staffList = csvUtils.StaffDataInit(CSV_FILE_PATH +"Staff_List.csv", staffList);
        patientList = csvUtils.PatientDataInit(CSV_FILE_PATH + "Patient_List.csv", patientList);
        System.out.println("========================================");
        System.out.println("Welcome to Hospital Management System");

        int choice;
        do {
            System.out.println("(1) Login\n(2) Exit");
            System.out.print("Please select an option: ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // Clear the buffer

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
            } else {
                System.out.println("Invalid Input. Please enter a number (1 or 2).");
                sc.next(); // Clear the invalid input from the scanner buffer
            }
        } while (true);
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
