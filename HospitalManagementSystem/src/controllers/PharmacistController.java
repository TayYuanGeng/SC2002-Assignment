package controllers;

import interfaces.CSVUtilsInterface;

import java.util.ArrayList;
import java.util.Scanner;
import models.*;

/**
 * The PharmacistController class manages the interaction between a logged-in patient
 * and the system, comprising the logic for handling the following features available to the pharmacist:
 * 1. View Appointment Outcome
 * 2. Update Prescription Status
 * 3. View Medicine Inventory
 * 4. Submit Replenishment Request
 * 5. Logout
 */
public class PharmacistController {
	/** Interface for CSV utilities to handle file operations */
    static CSVUtilsInterface csvUtils = new CSVUtilsController();
     /**
     * Starts the pharmacist interface, displaying a welcome message and 
     * displaying the pharmacist menu.
     *
     * @param loggedInUser The account of the logged-in user (pharmacist).
     * @throws Exception If an error occurs while loading or interacting with pharmacist data.
     */
    public static void main(Account loggedInUser) throws Exception {
        final String REPLENISH_REQUEST_CSV_FILE = MainMenuController.CSV_FILE_PATH+"ReplenishRequest_List.csv";
        final String MEDICINE_CSV_FILE = MainMenuController.CSV_FILE_PATH+"Medicine_List.csv";


        // You now have access to the logged-in user here
        Pharmacist pharm = new Pharmacist(loggedInUser.getID(), loggedInUser.getName(), loggedInUser.getPassword(), loggedInUser.getRole());
        pharm.SetMedicineList(csvUtils.MedicineDataInit(MEDICINE_CSV_FILE));

        System.out.println("Welcome, " + loggedInUser.getName());
        Scanner sc = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("Welcome to Pharmacist Menu");
        int choice = 0;
        do{
            try {
                // Display Pharmacist menu
                pharm.displayMenu();

                // Handle Menu choice
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Enter PatientID: ");
                        ViewAppointmentOutcome(sc.nextLine());
                        break;
                    case 2:
                        System.out.println("Enter PatientID: ");
                        DispensePrescription(sc.nextLine());
                        break;
                    case 3:
                        ViewMedicalInventory(pharm.GetMedicineList());
                        break;
                    case 4:
                        System.out.println("Enter medicine name: ");
                        String medicineName = sc.nextLine();
                        // Validate medicine name
                        Boolean medFound = false;
                        for (Medicine m: pharm.GetMedicineList()) {
                            if (m.getName().equals(medicineName)) {
                                medFound = true;
                            }
                        }
                        if (medFound) {
                            SubmitReplenishmentRequest(REPLENISH_REQUEST_CSV_FILE, new ReplenishmentRequest(medicineName));
                        }
                        else {
                            System.out.println("No such medicine!");
                        }
                        break;
                    case 5:
                        MainMenuController.LoginPage();
                        break;
                    default:
                        System.out.println("Invalid Input. Please enter an integer (1-5):");
                        break;
                }
            }
            catch (Exception e) {
                System.out.println("Invalid Input. Please enter an integer (1-5):");
                sc.next();
            }
        } while(true);
    }

    /**
     * Calls method in AppointmentController to display appointment outcome of specific patient.
     *
     * @param patientID ID of Patient to view appointment outcome of.
     */
    public static void ViewAppointmentOutcome(String patientID) {
        AppointmentController.readApptOutcome(patientID);
    }

    /**
     * Calls method in AppointmentController to update prescription status to dispensed of specific patient.
     *
     * @param patientID ID of Patient to dispense medication.
     */
    public static void DispensePrescription(String patientID) {
        AppointmentController.setPrescriptionStatus(patientID);
    }

    /**
     * Displays Name, Current Stock, Initial Stock, Low Level Stock of Medicine available.
     *
     * @param medicineList List of medicine available.
     */
    public static void ViewMedicalInventory(ArrayList<Medicine> medicineList) {
        if (medicineList.size() != 0) {
            // Header
            System.out.println("Name\t" + "Current Stock\t" + "Initial Stock" + "Low Level Stock");

            for (Medicine m: medicineList) {
                String currentStockAmt = Integer.toString(m.getStockAmt());
                String initialStock = Integer.toString(m.getCurrentAmount());
                String lowLvlStock = Integer.toString(m.getLowLvlStockAmt());

                System.out.println(m.getName() + "\t" + currentStockAmt + "\t" + initialStock + "\t" + lowLvlStock + "\t");
            }
        }
        else {
            System.out.println("No medicine available!");
        }
    }

    /**
     * Calls method in csvUtils to save replenishment request to be submitted.
     *
     * @param filepath File path where replenish requests reside.
     * @param r Replenishment request to be submitted.
     */
    public static void SubmitReplenishmentRequest(String filepath, ReplenishmentRequest r) {
        if (csvUtils.saveReplenishReqToCSV(filepath, r)) {
            System.out.println("Replenish request submitted!");
        }
    }
}
