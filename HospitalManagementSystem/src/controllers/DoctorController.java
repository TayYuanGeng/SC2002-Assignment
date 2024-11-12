package controllers;

import java.util.Scanner;
import models.*;

public class DoctorController {
    public static void main(Account loggedInUser) throws Exception {
            // You now have access to the logged-in user here
            Doctor docter = new Doctor(loggedInUser.getID(), loggedInUser.getName(), loggedInUser.getPassword(), loggedInUser.getRole());
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
                            docter.ViewPatientMedicalRecord(sc.nextLine());
                            break;
                        case 2:
                            System.out.println("Enter PatientID: ");
                            String patientID2 = sc.nextLine();
                            System.out.println("Enter diagnosis: ");
                            String diagnosis = sc.nextLine();
                            System.out.println("Enter treatment: ");
                            String treatment = sc.nextLine();
                            docter.UpdateDiagnosisAndTreatment(patientID2, diagnosis, treatment);
                            break;
                        case 3:
                        docter.viewSchedule();
                            break;
                        case 4:
                            System.out.println("Enter Unavailable Date (dd-MM-YYYY HH:MM): ");
                            docter.setAvailability(sc.nextLine());
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
                            docter.acceptDeclineAppt(apptDate, patientID, responseBool);
                            break;
                        case 6:
                            docter.ShowUpcomingAppointments();
                            break;
                        case 7:
                            System.out.println("Enter PatientID: ");
                            String patientID1 = sc.nextLine();
                            System.out.println("Enter datetime of completion (dd-MM-YYYY HH:MM): ");
                            String completionDate = sc.nextLine();
                            docter.RecordAppointmentOutcome(patientID1, completionDate);
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
}
