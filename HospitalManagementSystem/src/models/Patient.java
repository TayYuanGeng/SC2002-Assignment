package models;
import interfaces.*;

public class Patient extends Account {
    private String DOB;
    private String gender;
    private String bloodType;
    private String email;
    private int phoneNumber;
    private IMedicalRecordService recordService;

    public Patient(String uID, String pass, String name, String r, IMedicalRecordService recordService)
    {
        super(uID,name,pass,r);    
        this.recordService = recordService;
    }
    
	public void displayMenu(){
		System.out.println("===================================================");
		System.out.println("Welcome to patient Menu");
		System.out.println("===================================================");
		System.out.println(" (1) View Medical Record \n (2) Update Personal Information \n (3) View Available Appointment Slots \n (4) Schedule an Appointment \n (5) Reschedule an Appointment \n (6) Cancel an Appointment \n (7) View Scheduled Appointment \n (8) View Past Appoinment Outcome Records \n (9) Logout");
		System.out.println("===================================================");
	}

    public String GetName()
    {
        return super.getName();
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

    public IMedicalRecordService getMedicalRecordService()
    {
        return this.recordService;
    }

    public void setGender(String gender) {
		this.gender = gender;
	};
	
	public String getGender() {
		return this.gender;
	}

    public void setDOB(String dateOfBirth) {
		this.DOB = dateOfBirth;
	};
	
	public String getDOB() {
		return this.DOB;
	}

    public void setbloodType(String bloodType) {
		this.bloodType = bloodType;
	};
	
	public String getbloodType() {
		return this.bloodType;
	}

    public void setEmail(String email) {
		this.email = email;
	};
	
	public String getEmail() {
		return this.email;
	}

    public void setphoneNumber(int number) {
		this.phoneNumber = number;
	};
	
	public int getphoneNumber() {
		return this.phoneNumber;
	}
}
