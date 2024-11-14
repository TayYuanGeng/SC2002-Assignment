package controllers;

import interfaces.CSVUtilsInterface;
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
                        pharm.ViewAppointmentOutcome(sc.nextLine());
                        break;
                    case 2:
                        System.out.println("Enter PatientID: ");
                        pharm.DispensePrescription(sc.nextLine());
                        break;
                    case 3:
                        pharm.ViewMedicalInventory();
                        break;
                    case 4:
                        System.out.println("Enter medicine name: ");
                        pharm.SubmitReplenishmentRequest(REPLENISH_REQUEST_CSV_FILE, new ReplenishmentRequest(sc.nextLine()));
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
}
