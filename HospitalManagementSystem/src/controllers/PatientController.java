package controllers;

import java.util.Scanner;
import models.*;
import interfaces.*;

/**
 * The PatientController class manages the interaction between a logged-in patient
 * and the system, allowing the patient to perform various actions such as viewing
 * medical records, updating contact details, scheduling/rescheduling/canceling appointments,
 * and checking doctor availability.
 */
public class PatientController {
    static Scanner sc = new Scanner(System.in);

    /**
     * Main method to start the patient interface, displaying a welcome message and 
     * launching the patient page.
     *
     * @param loggedInUser the account of the logged-in user (patient).
     * @throws Exception if an error occurs while loading or interacting with patient data.
     */
    public static void main(Account loggedInUser) throws Exception
    {
        System.out.println("Welcome, " + loggedInUser.getName());
        PatientPage(loggedInUser);
    }


    /**
     * Displays the patient menu and processes the patient's choice of action.
     * The actions include viewing/updating medical records, managing appointments,
     * and interacting with doctor availability.
     *
     * @param loggedInUser the account of the logged-in user (patient).
     * @throws Exception if an error occurs while performing any patient-related actions.
     */
    public static void PatientPage(Account loggedInUser) throws Exception
    {
        MedicalRecordRepo recordRepo = new MedicalRecordRepo(MainMenuController.CSV_FILE_PATH+"Patient_List.csv");
        IMedicalRecordService recordService = new MedicalRecordService(recordRepo);
        int choice;
        String ID = loggedInUser.getID();
        Scanner sc = new Scanner(System.in);
        Patient patient = new Patient(loggedInUser.getID(), loggedInUser.getPassword(),loggedInUser.getName(), loggedInUser.getRole(), recordService);

        do
        {
            patient.displayMenu();
            choice = sc.nextInt();
            switch(choice)
            {
                case 1:
                    recordService.getMedicalRecord(ID);
                    break;
                
                case 2:
                    sc.nextLine();
                    System.out.print("Enter new email: ");
                    String new_email = sc.nextLine();
                    System.out.print("Enter new phone number: ");
                    String new_phone = sc.nextLine();
                    recordService.updateContact(ID, new_email, new_phone);
                    System.out.println("Contact Details Updated!");
                    break;
                
                case 3:
                    sc.nextLine();
                    System.out.println("Enter filter option to check doctors availability/unavailability: ");
                    System.out.println("1. Filter by Doctor Name (Unavailability)");
                    System.out.println("2. Filter by Date (Unavailability)");
                    System.out.println("3. Filter by Both Doctor Name and Date (Unavailability)");
                    System.out.println("4. Show All Unavailable Slots");
                    System.out.println("5. Show Available Slots of a Doctor and Date");
                    System.out.print("Choose an option: ");
                    int option = sc.nextInt();
                    sc.nextLine();
                    boolean checkingAvail = false;

                    if (option == 5){
                        checkingAvail = true;
                    }
        
                    String doctorFilter = "";
                    String dateFilter = "";

                    // Gather filters based on user choice
                    if (option == 1 || option == 3 || option == 5) {
                        System.out.print("Enter doctor name: ");
                        doctorFilter = sc.nextLine();
                    }
                    if (option == 2 || option == 3 || option == 5) {
                        System.out.print("Enter date (e.g., 12-03-2025): ");
                        dateFilter = sc.nextLine();
                    }
                    AppointmentController.showDoctorUnavailability(doctorFilter, dateFilter, checkingAvail);
                    break;
                
                case 4:
                    sc.nextLine();
                    System.out.print("Enter the date and time for the appointment you want: ");
                    String date_time = sc.nextLine();
                    System.out.print("Enter doctor's name: ");
                    String doctor_name = sc.nextLine();
                    AppointmentController.scheduleAppointment(date_time, ID, doctor_name);

                    break;
                
                case 5:
                    sc.nextLine();
                    System.out.print("Enter the date and time of the appointment that you want to reschedule: ");
                    String old_date_time = sc.nextLine();
                    System.out.print("Enter the date and time of when would you like to reschedule to: ");
                    String new_date_time = sc.nextLine();

                    AppointmentController.rescheduleAppointment(ID, old_date_time, new_date_time);
                    break;
                
                case 6:
                    sc.nextLine();
                    System.out.print("Enter the date and time of the appointment that you want to cancel: ");
                    String cancel_date_time = sc.nextLine();
                    AppointmentController.cancelAppointment(ID, cancel_date_time);
                    
                case 7:
                    AppointmentController.showPatientAppointment(ID);
                    break;

                case 8:
                    AppointmentController.readApptOutcome(ID);
                    break;
    
                case 9:
                    MainMenuController.LoginPage();
                    break;
                
                default:
                    System.out.println("Invalid Option");
            }
        }
        while(choice != 9);    
    }
}
