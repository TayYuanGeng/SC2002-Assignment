package models;

import java.util.Scanner;

public class Docter extends Staff {
    //private MedicalRecord medicalRecord;
    public Docter(){}

    public Docter(String uID,String name, String pass, String r) {
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
                        ViewPatientMedicalRecord();
                        break;
                    case 2:
                        UpdatePatientMedicalRecord();
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

    public boolean ViewPatientMedicalRecord() {
        return false;
    }

    public boolean UpdatePatientMedicalRecord() {
        return false;
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


    public void RecordAppointmentOutcome() {

    }


}
