package models;

import java.util.Scanner;

public class Pharmacist extends Staff {
    private Patient[] patientList = new Patient[100];
    private int patientListIndex = 0;

    public Pharmacist(){}

    public Pharmacist(String uID,String name, String pass, String r) {
		super(uID,name,pass,r);
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
                        break;
                    case 2:
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

    public void UpdatePresciptionStatus(String patientID) {
        Appointment.setPrescriptionStatus(patientID);
    }

    public void ViewMedicalInventory() {

    }

    public void SubmitReplenishmentRequest() {
        
    }

}