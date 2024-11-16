package models;

import interfaces.IMedicalRecordService;

/**
 * Service class responsible for handling medical record operations,
 * including retrieving, updating contact information, and updating diagnoses and treatments.
 * Implements the IMedicalRecordService interface.
 */
public class MedicalRecordService implements IMedicalRecordService {
    
    /**
     *Medical Record Repo file
     */
    private MedicalRecordRepo recordRepo;

    /**
     * Constructs a MedicalRecordService with the specified MedicalRecordRepo.
     *
     * @param recordRepo the MedicalRecordRepo instance used to access and manipulate medical records.
     */
    public MedicalRecordService(MedicalRecordRepo recordRepo)
    {
        this.recordRepo = recordRepo;
    }

    /**
     * Retrieves and displays the medical record for the specified patient.
     * If the record is not found, a message is displayed to the console.
     *
     * @param patientID the unique identifier of the patient.
     *                  Must not be null or empty.
     */
    @Override
    public void getMedicalRecord(String patientID)
    {
        MedicalRecord record = recordRepo.loadRecord(patientID);
        if(record != null)
        {
            record.ViewRecord();
        }
        else
        {
            System.out.println("Record not found!");
        }
    }

    /**
     * Updates the contact information (email and phone) of the specified patient's medical record.
     * If the record is not found, a message is displayed to the console.
     *
     * @param patientID the unique identifier of the patient.
     *                  Must not be null or empty.
     * @param new_email the new email address for the patient.
     * @param new_phone the new phone number for the patient.
     */
    @Override
    public void updateContact(String patientID, String new_email, String new_phone)
    {
        MedicalRecord record = recordRepo.loadRecord(patientID);
        if(record != null)
        {
            record.setEmail(new_email);
            record.setPhone(new_phone);
            recordRepo.saveContactRecord(record);
        }
        else
        {
            System.out.println("Record not found!");
        }
    }
    
    /**
     * Updates the diagnosis and treatment information for the specified patient.
     * If the record is not found, a message is displayed to the console.
     *
     * @param patientID the unique identifier of the patient.
     *                  Must not be null or empty.
     * @param diagnosis the new diagnosis to be added to the patient's record.
     * @param treatment the corresponding treatment for the new diagnosis.
     */
    @Override
    public void updateDiagnosisandTreatment(String patientID, String diagnosis, String treatment)
    {
        MedicalRecord record = recordRepo.loadRecord(patientID);
        if(record != null)
        {
            record.getDiagnosisandTreatment().put(diagnosis,treatment);
            recordRepo.updateDiagnosisandTreatment(patientID,diagnosis,treatment);
            System.out.println("Diagnosis and Treatment updated");
        }
        else
        {
            System.out.println("Record cannot be found");
        }
    }
}
