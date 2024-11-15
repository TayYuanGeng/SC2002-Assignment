package models;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import controllers.AppointmentController;
import controllers.CSVUtilsController;
import interfaces.CSVUtilsInterface;

public class Pharmacist extends Staff {
    private ArrayList<Medicine> medicineList = new ArrayList<Medicine>();
    static CSVUtilsInterface csvUtils = new CSVUtilsController();

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
}