package models;

public class Patient extends Account {
    private String DOB;
    private String gender;
    private String bloodType;
    private String email;
    private int phoneNumber;
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
