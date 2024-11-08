package models;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Pharmacist extends Staff {
    private ArrayList<Medicine> medicineList = new ArrayList<Medicine>();

    // public static void main(String[] args) throws Exception {
    //     ArrayList<Medicine> medicineListTest = new ArrayList<Medicine>();

    //     String line;
    //     String csvSplitBy = ",";
    //     boolean isFirstLine = true;

    //     try (BufferedReader br = new BufferedReader(new FileReader("E:\\ZW Files\\OneDrive - Nanyang Technological University\\NTU Bachelor Computer Science\\Y2-S1\\Object Orientated Programming\\Assignment\\SC2002-Assignment\\HospitalManagementSystem\\src\\data\\Medicine_List.csv"))) {
    //         while ((line = br.readLine()) != null) {
    //             if(isFirstLine) {
    //                 isFirstLine = false;
    //                 continue;
    //             }
    //             // Use comma as separator
    //             String[] values = line.split(csvSplitBy);
    //             Medicine m = new Medicine(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
    //             medicineListTest.add(m);
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }

    //     for (Medicine m: medicineListTest)  {
    //         System.out.println(m.getName() + Integer.toString(m.getStockAmt()) + Integer.toString(m.getLowLvlStockAmt()));
    //     }
    // }

    public Pharmacist(){}

    public Pharmacist(String uID,String name, String pass, String r) {
		super(uID,name,pass,r);
        LoadMedicineFromCSV("E:\\ZW Files\\OneDrive - Nanyang Technological University\\NTU Bachelor Computer Science\\Y2-S1\\Object Orientated Programming\\Assignment\\SC2002-Assignment\\HospitalManagementSystem\\src\\data\\Medicine_List.csv");
	}

    // Other Methods
    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("Welcome to Pharmacist Menu");
        int choice = 0;
        do{
            try {
                System.out.println("========================================");
                System.out.println("(1) View Appointment Outcome Record ");
                System.out.println("(2) Update Prescription Status ");
                System.out.println("(3) View Medication Inventory");
                System.out.println("(4) Submit Replenishment Request");
                System.out.println("(5) Logout");

                System.out.println("========================================");
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
                        break;
                    case 4:
                        break;
                    case 5:
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
        } while(true);
    }

    public void ViewAppointmentOutcome(String patientID) {
        Appointment.readApptOutcome(patientID);
    }

    public void DispensePrescription(String patientID) {
        Appointment.setPrescriptionStatus(patientID);
    }

    public void ViewMedicalInventory() {
        if (medicineList.size() != 0) {
            // Header
            System.out.println("Name\t" + "Current Stock\t" + "Initial Stock" + "Low Level Stock");

            for (Medicine m: medicineList) {
                String currentStockAmt = Integer.toString(m.getStockAmt());
                String initialStock = Integer.toString(m.getInitialStockAmt());
                String lowLvlStock = Integer.toString(m.getLowLvlStockAmt());

                System.out.println(m.getName() + "\t" + currentStockAmt + "\t" + initialStock + "\t" + lowLvlStock + "\t");
            }
        }
        else {
            System.out.println("No medicine available!");
        }
    }

    public void SubmitReplenishmentRequest() {
        
    }
    
    // Helper Methods
    public void LoadMedicineFromCSV(String filePath) {
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
                Medicine m = new Medicine(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                medicineList.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}