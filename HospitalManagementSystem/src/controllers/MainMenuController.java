package controllers;

import interfaces.CSVUtilsInterface;
import interfaces.PasswordUtilsInterface;
import java.util.*;
import models.*;

/**
 * Main controller for the Hospital Management System (HMS).
 * Handles user login, role-based navigation, and system initialization.
 */
public class MainMenuController {

    /**
     * Scanner instance for user input.
     */
    static Scanner sc = new Scanner(System.in);

    /**
     * List of staff users loaded from the CSV file.
     */
    static ArrayList<Staff> staffList = new ArrayList<>();

    /**
     * List of patient users loaded from the CSV file.
     */
    static ArrayList<Patient> patientList = new ArrayList<>();

    /**
     * Currently logged-in user.
     */
    static Account loggedInUser;

    /**
     * Password utility instance for password management.
     */
    static PasswordUtilsInterface passwordUtils = new PasswordUtilsController();

    /**
     * CSV utility instance for handling data operations.
     */
    public static CSVUtilsInterface csvUtils = new CSVUtilsController();

    /**
     * Path to the CSV files for staff and patient data.
     * Update this to the appropriate path for your environment.
     */
    public static final String CSV_FILE_PATH = "data\\";

    /**
     * Main entry point for the application.
     * Displays the welcome page and initializes the system.
     *
     * @param args Command-line arguments (not used).
     * @throws Exception If an error occurs during system execution.
     */
    public static void main(String[] args) throws Exception {
        WelcomePage();
    }

    /**
     * Handles the login process for staff or patients.
     * Verifies user credentials and updates the logged-in user.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param userType The type of user (1 for Staff, 2 for Patient).
     * @throws Exception If an error occurs during login or password handling.
     */
    private static void login(String username, String password, int userType) throws Exception {
        loggedInUser = null;

        switch (userType) {
            case 1: // Staff login
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
            case 2: // Patient login
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

    /**
     * Displays the login page and handles user input for login.
     *
     * @throws Exception If an error occurs during login or navigation.
     */
    public static void LoginPage() throws Exception {
        int exitChoice = -1;

        do {
            System.out.println("========================================");
            System.out.println("(1) Staff Login");
            System.out.println("(2) Patient Login");
            System.out.println("(3) Back");
            System.out.println("========================================");
            System.out.print("Please select an option: ");

            String username = null;
            String password = null;
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

            if (loggedInUser == null && username != null && password != null) {
                System.out.println("Incorrect login. (1) Try again or (2) Exit:");
                exitChoice = sc.nextInt();
                sc.nextLine(); // Clear buffer
            } else if (loggedInUser != null) {
                navigateByRole(loggedInUser.getRole());
            }
        } while (exitChoice != 2 && loggedInUser == null);

        if (exitChoice == 2) {
            WelcomePage();
        }
    }

    /**
     * Displays the welcome page and initializes data for the system.
     *
     * @throws Exception If an error occurs during data initialization.
     */
    public static void WelcomePage() throws Exception {
        staffList = csvUtils.StaffDataInit(CSV_FILE_PATH + "Staff_List.csv", staffList);
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

    /**
     * Navigates the user to the appropriate role-based menu.
     *
     * @param role The role of the logged-in user.
     * @return A numeric code representing the user's role.
     * @throws Exception If an error occurs during role navigation.
     */
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
