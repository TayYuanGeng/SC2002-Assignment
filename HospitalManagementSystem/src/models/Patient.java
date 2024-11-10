package models;
import java.util.ArrayList;
import java.util.Scanner;
import controllers.AppointmentController;

public class Patient extends Account {
    private MedicalRecordService recordService;

    public Patient(String uID, String pass, String name, String r, MedicalRecordService recordService)
    {
        super(uID,name,pass,r);    
        this.recordService = recordService;  
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

    public MedicalRecordService getMedicalRecordService()
    {
        return this.recordService;
    }
}
