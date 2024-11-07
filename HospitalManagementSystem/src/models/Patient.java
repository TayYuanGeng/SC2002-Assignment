package models;
import java.util.Scanner;
import java.util.ArrayList;
import models.Appointment;


public class Patient extends Account {
    private MedicalRecordService recordService;
    private ArrayList<Appointment> appointments;

    public Patient(String uID, String pass, String name, String r, MedicalRecordService recordService)
    {
        super(uID,name,pass,r);    
        this.recordService = recordService;  
    }
        
    

    public void displayMenu()
    {
        int choice;
        String ID = getID();
        Scanner sc = new Scanner(System.in);
        do
        {
            System.out.println("===================================================");
            System.out.println("Welcome to patient Menu");
            System.out.println("===================================================");
            System.out.println(" (1) View Medical Record \n (2) Update Personal Information \n (3) View Available Appointment Slots \n (4) Schedule an Appointment \n (5) Reschedule an Appointment \n (6) Cancel an Appointment \n (7) View Schedule Appointment \n (8) View Past Appoinment Outcome Records \n (9) Logout");
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
                
                // case 3:
                //     viewAvailableAppointment();
                //     break;
                
                // case 4:
                //     scheduleAppointment();
                //     break;
                
                // case 5:
                //     RescheduleAppointment();
                //     break;
                
                // case 6:
                //     // free up the slot
                //     CancelAppoinment(index);
                
                // case 7:
                //     if(appointments.isEmpty)
                //     {
                //         System.out.println("There are no scheduled appointments currently");
                //     }
                //     for(int i=0; i<appointments.size(); i++)
                //     {
                        
                //     }
                //     break;
                // case 8:
                //     for(Appointment appointment : appointments)
                //     {
                //         if(appointment.getAppointmentStatus == COMPLETED)
                //         {
                //             // show appt
                //         }
                //     }
                //     break;
    
                case 9:
                    break;
                
                default:
                    System.out.println("Invalid Option");
            }
        }
        while(choice != 9);    
        

    }

    public void CancelAppoinment(int index)
    {
        if(index >= 0 && index< appointments.size())
        {
            appointments.remove(index);
            System.out.println("Appoinment Cancelled");
        }
        else
        {
            System.out.println("No appointments scheduled");
        }
        
    }

    // public void updatePersonalInfo()
    // {

    // }
}
