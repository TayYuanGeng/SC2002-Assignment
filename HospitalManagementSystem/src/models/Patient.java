package models;
import interfaces.*;

/**
 * Represents a patient account in the system. Inherits from the Account class and provides
 * functionality for managing personal information and interacting with medical records.
 */
public class Patient extends Account {
    private String DOB;
    private String gender;
    private String bloodType;
    private String email;
    private int phoneNumber;
    private IMedicalRecordService recordService;

	/**
     * Constructs a Patient object with the specified parameters.
     *
     * @param uID the unique identifier for the patient.
     * @param pass the password for the patient account.
     * @param name the name of the patient.
     * @param r the role of the user (patient).
     * @param recordService the medical record service for interacting with the patient's medical records.
     */
    public Patient(String uID, String pass, String name, String r, IMedicalRecordService recordService)
    {
        super(uID,name,pass,r);    
        this.recordService = recordService;
    }
    
    /**
     * Displays the menu options available for the patient.
     * These options include viewing medical records, scheduling appointments, and more.
     */
	public void displayMenu(){
		System.out.println("===================================================");
		System.out.println("Welcome to patient Menu");
		System.out.println("===================================================");
		System.out.println(" (1) View Medical Record \n (2) Update Personal Information \n (3) View Available Appointment Slots \n (4) Schedule an Appointment \n (5) Reschedule an Appointment \n (6) Cancel an Appointment \n (7) View Scheduled Appointment \n (8) View Past Appoinment Outcome Records \n (9) Logout");
		System.out.println("===================================================");
	}

	/**
     * Retrieves the name of the patient from the parent Account class.
     *
     * @return the name of the patient.
     */
    public String GetName()
    {
        return super.getName();
    }
    
	/**
     * Retrieves the ID of the patient from the parent Account class.
     *
     * @return the unique ID of the patient.
     */
    public String getID() {
		return super.getID();
	}

	/**
     * Sets a new password for the patient account.
     *
     * @param pass the new password for the patient account.
     */
    public void setPassword(String pass) {
		super.setPassword(pass);
	};
	
	/**
     * Retrieves the password of the patient account.
     *
     * @return the password of the patient account.
     */
	public String getPassword() {
		return super.getPassword();
	}

    /**
     * Retrieves the medical record service associated with the patient.
     *
     * @return the IMedicalRecordService instance for interacting with medical records.
     */
    public IMedicalRecordService getMedicalRecordService()
    {
        return this.recordService;
    }

	/**
     * Sets the gender of the patient.
     *
     * @param gender the gender of the patient.
     */
    public void setGender(String gender) {
		this.gender = gender;
	};

	/**
     * Retrieves the gender of the patient.
     *
     * @return the gender of the patient.
     */
	public String getGender() {
		return this.gender;
	}

    /**
     * Sets the date of birth for the patient.
     *
     * @param dateOfBirth the date of birth of the patient.
     */
    public void setDOB(String dateOfBirth) {
		this.DOB = dateOfBirth;
	};

	/**
     * Retrieves the date of birth of the patient.
     *
     * @return the date of birth of the patient.
     */
	public String getDOB() {
		return this.DOB;
	}

  
    /**
     * Sets the blood type of the patient.
     *
     * @param bloodType the blood type of the patient.
     */
    public void setbloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Retrieves the blood type of the patient.
     *
     * @return the blood type of the patient.
     */
    public String getbloodType() {
        return this.bloodType;
    }

    /**
     * Sets the email address of the patient.
     *
     * @param email the email address of the patient.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the email address of the patient.
     *
     * @return the email address of the patient.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the phone number of the patient.
     *
     * @param number the phone number of the patient.
     */
    public void setphoneNumber(int number) {
        this.phoneNumber = number;
    }

    /**
     * Retrieves the phone number of the patient.
     *
     * @return the phone number of the patient.
     */
    public int getphoneNumber() {
        return this.phoneNumber;
    }
}
