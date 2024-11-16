package models;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a patient's medical record, containing personal details and
 * a history of diagnoses and treatments.
 */
public class MedicalRecord {
    /**
     * The unique identifier for the patient.
     */
    private String patientID;

    /**
     * The name of the patient.
     */
    private String name;

    /**
     * The date of birth of the patient in the format YYYY-MM-DD.
     */
    private String dateofBirth;

    /**
     * The gender of the patient (e.g., Male, Female, Other).
     */
    private String gender;

    /**
     * The email address of the patient.
     */
    private String email;

    /**
     * The phone number of the patient.
     */
    private String phone;

    /**
     * The blood type of the patient (e.g., A+, B-, O+).
     */
    private String bloodType;

    /**
     * A map containing diagnoses as keys and their corresponding treatments as values.
     */
    private Map<String, String> diagnosisAndTreatment;
    

    /**
     * Constructs a new MedicalRecord with the specified details.
     *
     * @param patientID              the unique identifier for the patient.
     * @param name                   the name of the patient.
     * @param dateofBirth            the patient's date of birth.
     * @param gender                 the gender of the patient.
     * @param email                  the patient's email address.
     * @param phone                  the patient's phone number.
     * @param bloodtype              the patient's blood type.
     * @param diagnosisAndTreatment  a map containing past diagnoses as keys
     *                                and their corresponding treatments as values.
     */
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

    /**
     * Returns the unique identifier of the patient.
     *
     * @return the patient ID.
     */
    public String getPatientId()
    {
        return patientID;
    }

    /**
     * Updates the patient's email address.
     *
     * @param email the new email address to set.
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Updates the patient's phone number.
     *
     * @param phone the new phone number to set.
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * Retrieves the map of past diagnoses and treatments.
     *
     * @return a map where keys are diagnoses and values are corresponding treatments.
     */
    public Map getDiagnosisandTreatment()
    {
        return diagnosisAndTreatment;
    }

    /**
     * Returns the patient's email address.
     *
     * @return the email address of the patient.
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Returns the patient's phone number.
     *
     * @return the phone number of the patient.
     */
    public String getPhone()
    {
        return phone;
    }
    
    /**
     * Displays the complete medical record details to the console.
     * Includes patient personal information and past diagnoses and treatments.
     */
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
    