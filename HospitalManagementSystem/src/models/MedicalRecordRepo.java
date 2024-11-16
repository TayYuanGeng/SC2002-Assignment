package models;

import controllers.CSVUtilsController;
import interfaces.CSVUtilsInterface;

/**
 * Repository class for handling medical record data.
 * Provides methods to load, save, and update medical records from a CSV file.
 */
public class MedicalRecordRepo {
	
    /**
     * String to hold the filepath of the medical record
     */
    private String filepath;
    /** Interface for CSV utilities to handle file operations */
    static CSVUtilsInterface csvUtils = new CSVUtilsController();

    /**
     * Constructs a MedicalRecordRepo instance with the specified file path.
     *
     * @param filepath the path to the CSV file where medical records are stored.
     */
    public MedicalRecordRepo(String filepath)
    {
        this.filepath = filepath;
    }

    /**
     * Loads the medical record for a specific patient from the CSV file.
     *
     * @param patientID the unique identifier of the patient.
     * @return the medical record of the patient, or null if not found.
     */
    public MedicalRecord loadRecord(String patientID)
    {
        return csvUtils.getMedicalRecord(filepath, patientID);
    }

    /**
     * Saves or updates the contact information in the medical record of a patient.
     *
     * @param record the MedicalRecord object containing the updated contact details.
     */
    public void saveContactRecord(MedicalRecord record)
    {
        csvUtils.saveContactRecordCSV(filepath,record);
    }

    /**
     * Updates the diagnosis and treatment information of a patient's medical record.
     *
     * @param patientID the unique identifier of the patient.
     * @param diagnosis the diagnosis to update.
     * @param treatment the corresponding treatment to update.
     */
    public void updateDiagnosisandTreatment(String patientID, String diagnosis, String treatment)
    {
        csvUtils.updateDiagnosisandTreatmentCSV(filepath, patientID, diagnosis, treatment);
    }
}
