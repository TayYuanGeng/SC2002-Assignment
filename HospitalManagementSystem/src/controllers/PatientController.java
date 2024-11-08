package controllers;

import java.util.Scanner;
import models.*;


public class PatientController {
    static Scanner sc = new Scanner(System.in);
    public static void main(Account loggedInUser) throws Exception
    {
        System.out.println("Welcome, " + loggedInUser.getName());
        PatientPage(loggedInUser);
    }

    public static void PatientPage(Account loggedInUser) throws Exception
    {
        MedicalRecordRepo recordRepo = new MedicalRecordRepo("SC2002-Assignment\\\\HospitalManagementSystem\\\\src\\\\data\\\\Patient_List.csv");
        MedicalRecordService recordService = new MedicalRecordService(recordRepo);
        int choice;
        String ID = loggedInUser.getID();
        Scanner sc = new Scanner(System.in);
        do
        {
            System.out.println("===================================================");
            System.out.println("Welcome to patient Menu");
            System.out.println("===================================================");
            System.out.println(" (1) View Medical Record \n (2) Update Personal Information \n (3) View Available Appointment Slots \n (4) Schedule an Appointment \n (5) Reschedule an Appointment \n (6) Cancel an Appointment \n (7) View Scheduled Appointment \n (8) View Past Appoinment Outcome Records \n (9) Logout");
            System.out.println("===================================================");
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
                    break;
                
                case 3:
                    AppointmentController.showDoctorUnavailability();
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
