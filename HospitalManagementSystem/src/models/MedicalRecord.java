package models;
import java.util.Map;
import java.util.HashMap;

public class MedicalRecord {
    private String patientID;
    private String name;
    private String dateofBirth;
    private String gender;
    private String email;
    private String phone;
    private String bloodType;
    private Map<String,String> diagnosisAndTreatment;
    
    public MedicalRecord(String patientID, String name, String dateofBirth, String gender, String email, String phone, String bloodtype, HashMap<String,String> diagnosisAndTreatment)
    {
        this.patientID = patientID;
        this.name = name;
        this.dateofBirth = dateofBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.bloodType = bloodtype;
        this.diagnosisAndTreatment = diagnosisAndTreatment;
    }
    public String getPatientId()
    {
        return patientID;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Map getDiagnosisandTreatment()
    {
        return diagnosisAndTreatment;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void ViewRecord()
    {
        System.out.println("===============================");
        System.out.println("Medical Record Details");
        System.out.println("===============================");
        System.out.println("ID: " + patientID);
        System.out.println("Name: " + name);
        System.out.println("Date of Birth:" + dateofBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phone);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("------------------------------");
        System.out.println("Past Diagnosis and Treatments");
        System.out.println("------------------------------");
        for (Map.Entry<String,String> entry : diagnosisAndTreatment.entrySet()) 
        {
            System.out.println("Diagnosis: " + entry.getKey() + " | " + "Treatment: " + entry.getValue());
        }
    }

    




    
}
    