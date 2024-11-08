package models;

import java.util.Scanner;

public class Doctor extends Staff {
    private Patient[] patientList = new Patient[100];
    private int patientListIndex = 0;

    public Doctor(){}

    public Doctor(String uID,String name, String pass, String r) {
		super(uID,name,pass,r);
	}

    // Other Methods
    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("Welcome to Docter Menu");
        int choice = 0;
        do{
            try {
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
        Appointment.showDoctorSchedule(getID());
    }

    public void setAvailability(String date) {
        Appointment.updateDoctorUnavailability(date, getID());
    }

    public void acceptDeclineAppt(String date, String patientID, boolean response) {
        Appointment.respondToRequest(getID(), patientID, date, response);    
    }

    public void ShowUpcomingAppointments() {
        Appointment.showUpcomingAppointment(getID());
    }


    public void RecordAppointmentOutcome(String patientID, String date) {
        Appointment.completeAppointment(getID(), patientID, date);
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
