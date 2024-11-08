package models;
import java.util.ArrayList;
import java.util.Scanner;


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
                    Appointment.showDoctorUnavailability();
                    break;
                
                case 4:
                    sc.nextLine();
                    System.out.print("Enter the date and time for the appointment you want: ");
                    String date_time = sc.nextLine();
                    System.out.print("Enter doctor's name: ");
                    String doctor_name = sc.nextLine();
                    Appointment.scheduleAppointment(date_time, ID, doctor_name);

                    break;
                
                case 5:
                    sc.nextLine();
                    System.out.print("Enter the date and time of the appointment that you want to reschedule: ");
                    String old_date_time = sc.nextLine();
                    System.out.print("Enter the date and time of when would you like to reschedule to: ");
                    String new_date_time = sc.nextLine();

                    Appointment.rescheduleAppointment(ID, old_date_time, new_date_time);
                    break;
                
                case 6:
                    sc.nextLine();
                    System.out.print("Enter the date and time of the appointment that you want to cancel: ");
                    String cancel_date_time = sc.nextLine();
                    Appointment.cancelAppointment(ID, cancel_date_time);
                    
                case 7:
                    Appointment.showPatientAppointment(ID);
                    break;

                case 8:
                    Appointment.readApptOutcome(ID);
    
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


    
    public String getID() {
		return super.getID();
	}

    public void setPassword(String pass) {
		super.setPassword(pass);
	};
	
	public String getPassword() {
		return super.getPassword();
	}

    public MedicalRecordService getMedicalRecordService()
    {
        return this.recordService;
    }
}
