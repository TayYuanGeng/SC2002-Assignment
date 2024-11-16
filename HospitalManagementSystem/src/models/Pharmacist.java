package models;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import controllers.AppointmentController;
import controllers.CSVUtilsController;
import interfaces.CSVUtilsInterface;

/**
 * Pharmacist class which inherits from Staff class.
 */
public class Pharmacist extends Staff {
    private ArrayList<Medicine> medicineList = new ArrayList<Medicine>();
    static CSVUtilsInterface csvUtils = new CSVUtilsController();
    
    /**
     * Default constructor
     */
    public Pharmacist(){}

    /**
     * Constructor
     *
     * @param uID The ID of the pharmacist.
     * @param name The name of the pharmacist.
     * @param pass The password for the pharmacist's account.
     * @param r The role assigned to the pharmacist.
     */
    public Pharmacist(String uID,String name, String pass, String r) {
		super(uID,name,pass,r);
	}

    /**
     * Retrieves medicine list.
     * 
     * @return List of medicine available.
     */
    public ArrayList<Medicine> GetMedicineList() {
        return medicineList;
    }
    
    /**
     * Sets medicine list.
     * 
     * @param mList List of medicine available.
     */
    public void SetMedicineList(ArrayList<Medicine> mList) {
        medicineList = mList;
    }

    /**
     * Pharmacist menu.
     */
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