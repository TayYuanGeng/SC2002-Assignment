package controllers;

import java.util.Scanner;
import models.*;

public class DoctorController {
    static Patient[] patientList = new Patient[100];
    static int patientListIndex = 0;            
    static Doctor docter;

    public static void main(Account loggedInUser) throws Exception {
            // You now have access to the logged-in user here
            docter = new Doctor(loggedInUser.getID(), loggedInUser.getName(), loggedInUser.getPassword(), loggedInUser.getRole());
            System.out.println("Welcome, " + loggedInUser.getName());

            Scanner sc = new Scanner(System.in);
            System.out.println("========================================");
            System.out.println("Welcome to Docter Menu");
            int choice = 0;
            do{
                try {
                    // Display menu
                    docter.displayMenu();

                    // Handle Menu choice
                    choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1:
                            System.out.println("Enter ID of patient: ");
                            ViewPatientMedicalRecord(sc.nextLine());
                            break;
                        case 2:
                            System.out.println("Enter PatientID: ");
                            String patientID2 = sc.nextLine();
                            System.out.println("Enter diagnosis: ");
                            String diagnosis = sc.nextLine();
                            System.out.println("Enter treatment: ");
                            String treatment = sc.nextLine();
                            UpdateDiagnosisAndTreatment(patientID2, diagnosis, treatment);
                            break;
                        case 3:
                        viewSchedule();
                            break;
                        case 4:
                            System.out.println("Enter Unavailable Date (dd-MM-YYYY HH:MM): ");
                            setAvailability(sc.nextLine());
                            break;
                        case 5:
                            System.out.println("Enter Appointment Date (dd-MM-YYYY HH:MM): ");
                            String apptDate = sc.nextLine();
                            System.out.println("Enter PatientID: ");
                            String patientID = sc.nextLine();
                            System.out.println("Type 'y' to accept, 'n' to decline appointment: ");
                            String response = sc.nextLine();
                            Boolean responseBool = true;
                            if (response.equals('n')) {
                                responseBool = false;
                            }
                            acceptDeclineAppt(apptDate, patientID, responseBool);
                            break;
                        case 6:
                            ShowUpcomingAppointments();
                            break;
                        case 7:
                            System.out.println("Enter PatientID: ");
                            String patientID1 = sc.nextLine();
                            System.out.println("Enter datetime of completion (dd-MM-YYYY HH:MM): ");
                            String completionDate = sc.nextLine();
                            RecordAppointmentOutcome(patientID1, completionDate);
                            break;
                        case 8:
                            MainMenuController.LoginPage();
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

    public static void ViewPatientMedicalRecord(String patientID) {
        Patient p = GetPatient(patientID);
        if (p == null) { // Patient not found
            System.out.println("Patient not found!");
        }
        else {
            p.getMedicalRecordService().getMedicalRecord(patientID);
        }
    }

    // Update Medical Record
    public static void UpdateDiagnosisAndTreatment(String patientID, String diagnosis, String treatment) {
        Patient p = GetPatient(patientID);
        if (p == null) { // Patient not found
            System.out.println("Patient not found!");
        }
        else {
            p.getMedicalRecordService().updateDiagnosisandTreatment(patientID, diagnosis, treatment);
        }
    }

    public static void viewSchedule() {
        AppointmentController.showDoctorSchedule(docter.getID());
    }

    public static void setAvailability(String date) {
        AppointmentController.updateDoctorUnavailability(date, docter.getID());
    }

    public static void acceptDeclineAppt(String date, String patientID, boolean response) {
        AppointmentController.respondToRequest(docter.getID(), patientID, date, response);    
    }

    public static void ShowUpcomingAppointments() {
        AppointmentController.showUpcomingAppointment(docter.getID());
    }


    public static void RecordAppointmentOutcome(String patientID, String date) {
        AppointmentController.completeAppointment(docter.getID(), patientID, date);
    }

    // Helper Methods
    public static Boolean AssignPatient(Patient patient) {
        if (patientListIndex == patientList.length) {
            return false;
        }
        else {
            patientList[patientListIndex] = patient;
            return true;
        }
    }

    public static Patient GetPatient(String patientID) {
        if (patientListIndex == 0) {
            return null;
        }   
        else {
            for (int i = 0; i < patientListIndex; i++) {
                if (patientID == patientList[i].getID()) { // Found Patient
                    return patientList[i];
                }
            }
            return null;
        }
    }
}
