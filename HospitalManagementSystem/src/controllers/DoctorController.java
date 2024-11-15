package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import interfaces.CSVUtilsInterface;
import models.*;

public class DoctorController {           
    static Doctor docter;
    static ArrayList<Patient> patients = new ArrayList<Patient>();
    static final String APPT_REQUEST_CSV_FILE = MainMenuController.CSV_FILE_PATH+"ApptRequest.csv";
    static final String PATIENT_CSV_FILE = MainMenuController.CSV_FILE_PATH+"Patient_List.csv";

    static CSVUtilsInterface csvUtils = new CSVUtilsController();

    public static void main(Account loggedInUser) throws Exception {
            // Get patient list from CSV
            patients = csvUtils.PatientDataInit(PATIENT_CSV_FILE, patients);

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
                            String patientID = sc.nextLine();
                            // Check if Patient is assigned to Doctor
                            Boolean isAssigned = csvUtils.CheckPatientApptDoctorCSV(APPT_REQUEST_CSV_FILE, patientID, docter.getID());
                            if (isAssigned) {
                                Patient patientRetrieved = GetPatient(patients, patientID);
                                ViewPatientMedicalRecord(patientRetrieved);
                            }
                            else {
                                Patient patientRetrieved = GetPatient(patients, patientID);
                                if (patientRetrieved == null) { // Cannot Find patient inn CSV
                                    System.out.println("Patient does not exist!");
                                }
                                else {
                                    System.out.println("Patient not assigned to you!");
                                }
                            }
                            break;
                        case 2:
                            System.out.println("Enter PatientID: ");
                            String patientID2 = sc.nextLine();
                            // Check if Patient is assigned to Doctor
                            Boolean isAssigned1 = csvUtils.CheckPatientApptDoctorCSV(APPT_REQUEST_CSV_FILE, patientID2, docter.getID());
                            if (isAssigned1) {
                                Patient patientRetrieved = GetPatient(patients, patientID2);
                                System.out.println("Enter diagnosis: ");
                                String diagnosis = sc.nextLine();
                                System.out.println("Enter treatment: ");
                                String treatment = sc.nextLine();
                                UpdateDiagnosisAndTreatment(patientRetrieved, diagnosis, treatment);
                            }
                            else {
                                Patient patientRetrieved = GetPatient(patients, patientID2);
                                if (patientRetrieved == null) { // Cannot Find patient inn CSV
                                    System.out.println("Patient does not exist!");
                                }
                                else {
                                    System.out.println("Patient not assigned to you!");
                                }
                            }
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
                            String patientID3 = sc.nextLine();
                            System.out.println("Type 'y' to accept, 'n' to decline appointment: ");
                            String response = sc.nextLine();
                            Boolean responseBool = true;
                            if (response.equals("n")) {
                                responseBool = false;
                            }
                            acceptDeclineAppt(apptDate, patientID3, responseBool);
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

    public static void ViewPatientMedicalRecord(Patient p) {
        p.getMedicalRecordService().getMedicalRecord(p.getID());
    }

    public static void UpdateDiagnosisAndTreatment(Patient p, String diagnosis, String treatment) {
        p.getMedicalRecordService().updateDiagnosisandTreatment(p.getID(), diagnosis, treatment);
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

    //Helper
    public static Patient GetPatient(ArrayList<Patient> patientList, String patientID) {
        for (Patient p: patientList) {
            if (patientID.equals(p.getID())) {
                return p;
            }
        }
        return null;
    }
}
