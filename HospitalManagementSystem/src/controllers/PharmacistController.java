package controllers;

import interfaces.CSVUtilsInterface;

import java.util.ArrayList;
import java.util.Scanner;
import models.*;

public class PharmacistController {
    static CSVUtilsInterface csvUtils = new CSVUtilsController();
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
                        SubmitReplenishmentRequest(REPLENISH_REQUEST_CSV_FILE, new ReplenishmentRequest(sc.nextLine()));
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

    public static void ViewAppointmentOutcome(String patientID) {
        AppointmentController.readApptOutcome(patientID);
    }

    public static void DispensePrescription(String patientID) {
        AppointmentController.setPrescriptionStatus(patientID);
    }

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

    public static void SubmitReplenishmentRequest(String filepath, ReplenishmentRequest r) {
        if (csvUtils.saveReplenishReqToCSV(filepath, r)) {
            System.out.println("Replenish request submitted!");
        }
    }
}
