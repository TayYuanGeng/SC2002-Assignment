package models;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import controllers.AppointmentController;
import controllers.CSVUtilsController;

public class Pharmacist extends Staff {
    private ArrayList<Medicine> medicineList = new ArrayList<Medicine>();

    public Pharmacist(){}

    public Pharmacist(String uID,String name, String pass, String r) {
		super(uID,name,pass,r);
	}

    public ArrayList<Medicine> GetMedicineList() {
        return medicineList;
    }

    public void SetMedicineList(ArrayList<Medicine> mList) {
        medicineList = mList;
    }

    // Other Methods
    public void displayMenu() {
        System.out.println("========================================");
        System.out.println("(1) View Appointment Outcome Record ");
        System.out.println("(2) Update Prescription Status ");
        System.out.println("(3) View Medication Inventory");
        System.out.println("(4) Submit Replenishment Request");
        System.out.println("(5) Logout");

        System.out.println("========================================");
    }

    public void ViewAppointmentOutcome(String patientID) {
        AppointmentController.readApptOutcome(patientID);
    }

    public void DispensePrescription(String patientID) {
        AppointmentController.setPrescriptionStatus(patientID);
    }

    public void ViewMedicalInventory() {
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

    public void SubmitReplenishmentRequest(String filepath, ReplenishmentRequest r) {
        if (CSVUtilsController.saveReplenishReqToCSV(filepath, r)) {
            System.out.println("Replenish request submitted!");
        }
    }
}