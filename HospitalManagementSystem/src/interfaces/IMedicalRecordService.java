package interfaces;

/**
 * Interface for managing medical records.
 * Defines methods for retrieving and updating patient medical record information.
 */
public interface IMedicalRecordService {

    /**
     * Retrieves the medical record for the specified patient.
     *
     * @param patientID the unique identifier of the patient.
     *                  Must not be null or empty.
     */
    void getMedicalRecord(String patientID);

    /**
     * Updates the contact details of a patient.
     *
     * @param patientID the unique identifier of the patient.
     * @param new_email the new email address of the patient.
     * @param new_phone the new phone number of the patient.
     */
    void updateContact(String patientID, String new_email, String new_phone);

    /**
     * Updates the diagnosis and treatment information for a patient.
     *
     * @param patientID the unique identifier of the patient.
     * @param diagnosis the updated diagnosis for the patient.
     * @param treatment the updated treatment plan for the patient.
     */
    void updateDiagnosisandTreatment(String patientID, String diagnosis, String treatment);
}

