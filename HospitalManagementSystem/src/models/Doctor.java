package models;

import java.util.Scanner;

import controllers.AppointmentController;

public class Doctor extends Staff {
    private Patient[] patientList = new Patient[100];
    private int patientListIndex = 0;

    public Doctor(){}

    public Doctor(String uID,String name, String pass, String r) {
		super(uID,name,pass,r);
	}

    // Other Methods
    public void displayMenu() {
        System.out.println("========================================");
        System.out.println("(1) View Patient Medical Records");
        System.out.println("(2) Update Patient Medical Records");
        System.out.println("(3) View Personal Schedule");
        System.out.println("(4) Set Availability for Appointments");
        System.out.println("(5) Accept or Decline Appointment Requests");
        System.out.println("(6) View Upcoming Appointments");
        System.out.println("(7) Record Appointment Outcome ");
        System.out.println("(8) Logout");

        System.out.println("========================================");
    }

    public void ViewPatientMedicalRecord(String patientID) {
        Patient p = GetPatient(patientID);
        if (p == null) { // Patient not found
            System.out.println("Patient not found!");
        }
        else {
            p.getMedicalRecordService().getMedicalRecord(patientID);
        }
    }

    // Update Medical Record
    public void UpdateDiagnosisAndTreatment(String patientID, String diagnosis, String treatment) {
        Patient p = GetPatient(patientID);
        if (p == null) { // Patient not found
            System.out.println("Patient not found!");
        }
        else {
            p.getMedicalRecordService().updateDiagnosisandTreatment(patientID, diagnosis, treatment);
        }
    }

    public void viewSchedule() {
        AppointmentController.showDoctorSchedule(getID());
    }

    public void setAvailability(String date) {
        AppointmentController.updateDoctorUnavailability(date, getID());
    }

    public void acceptDeclineAppt(String date, String patientID, boolean response) {
        AppointmentController.respondToRequest(getID(), patientID, date, response);    
    }

    public void ShowUpcomingAppointments() {
        AppointmentController.showUpcomingAppointment(getID());
    }


    public void RecordAppointmentOutcome(String patientID, String date) {
        AppointmentController.completeAppointment(getID(), patientID, date);
    }

    // Helper Methods
    public Boolean AssignPatient(Patient patient) {
        if (patientListIndex == patientList.length) {
            return false;
        }
        else {
            patientList[patientListIndex] = patient;
            return true;
        }
    }

    public Patient GetPatient(String patientID) {
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
