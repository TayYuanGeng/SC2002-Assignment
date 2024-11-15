package interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.*;

/**
 * Interface defining methods for CSV data handling in the Hospital Management System.
 * Provides methods to manage data for staff, patients, medicines, appointments, and more.
 */
public interface CSVUtilsInterface {

    /**
     * Saves a user to the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param role     The role of the user (e.g., Staff, Patient).
     */
    void saveUserToCSV(String filePath, String username, String password, String role);

    /**
     * Saves a staff member's details to the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @param staff    The staff member object.
     */
    void saveStaffToCSV(String filePath, Staff staff);

    /**
     * Updates a staff member's details in the CSV file.
     *
     * @param filePath     The path to the CSV file.
     * @param updatedStaff The updated staff member object.
     */
    void updateStaffInCSV(String filePath, Staff updatedStaff);

    /**
     * Updates a patient's details in the CSV file.
     *
     * @param filePath        The path to the CSV file.
     * @param updatedPatient  The updated patient object.
     */
    void updatePatientInCSV(String filePath, Patient updatedPatient);

    /**
     * Initializes staff data from the CSV file.
     *
     * @param filePath  The path to the CSV file.
     * @param staffList The list of staff to be populated.
     * @return The initialized list of staff members.
     */
    ArrayList<Staff> StaffDataInit(String filePath, ArrayList<Staff> staffList);

    /**
     * Initializes medicine data from the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @return The initialized list of medicines.
     */
    ArrayList<Medicine> MedicineDataInit(String filePath);

    /**
     * Initializes patient data from the CSV file.
     *
     * @param filePath    The path to the CSV file.
     * @param patientList The list of patients to be populated.
     * @return The initialized list of patients.
     */
    ArrayList<Patient> PatientDataInit(String filePath, ArrayList<Patient> patientList);

    /**
     * Removes a staff member from the CSV file.
     *
     * @param filePath   The path to the CSV file.
     * @param removeStaff The staff member to remove.
     */
    void removeStaffInCSV(String filePath, Staff removeStaff);

    /**
     * Saves medicine details to the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @param med      The medicine object to save.
     */
    void saveMedToCSV(String filePath, Medicine med);

    /**
     * Removes medicine details from the CSV file.
     *
     * @param filePath       The path to the CSV file.
     * @param removeMedicine The medicine object to remove.
     */
    void removeMedInCSV(String filePath, Medicine removeMedicine);

    /**
     * Updates medicine details in the CSV file.
     *
     * @param filePath       The path to the CSV file.
     * @param updatedMedicine The updated medicine object.
     */
    void updateMedInCSV(String filePath, Medicine updatedMedicine);

    /**
     * Reads replenishment requests from the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @return A list of replenishment requests.
     */
    ArrayList<ReplenishmentRequest> ReadReplenishRequestCSV(String filePath);

    /**
     * Saves a replenishment request to the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @param request  The replenishment request to save.
     * @return {@code true} if the request was saved successfully; {@code false} otherwise.
     */
    boolean saveReplenishReqToCSV(String filePath, ReplenishmentRequest request);

    /**
     * Updates a replenishment request in the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @param request  The updated replenishment request.
     */
    void updateRepReqInCSV(String filePath, ReplenishmentRequest request);

    /**
     * Initializes appointment requests from the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @return A list of appointment requests.
     */
    List<Appointment> DataInitApptReq(String filePath);

    /**
     * Initializes unavailability data from the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @return A list of unavailability records.
     */
    List<Unavailability> DataInitUnavail(String filePath);

    /**
     * Initializes patient data from the CSV file into a map.
     *
     * @param filePath The path to the CSV file.
     * @return A map of patient IDs and their associated data.
     */
    Map<String, String> DataInitPatient(String filePath);

    /**
     * Initializes staff data from the CSV file into a map.
     *
     * @param filePath The path to the CSV file.
     * @return A map of staff IDs and their associated data.
     */
    Map<String, String> DataInitStaff(String filePath);

    /**
     * Initializes appointment outcomes from the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @return A list of appointment outcomes.
     */
    List<AppOutcome> DataInitApptOutcome(String filePath);

    /**
     * Writes data to the specified CSV file.
     *
     * @param fileName The path to the CSV file.
     * @param dataList The list of data to write.
     */
    void writeToCSV(String fileName, List<?> dataList);

    /**
     * Retrieves a medical record for a specific patient.
     *
     * @param filepath   The path to the medical records CSV file.
     * @param patientID  The ID of the patient.
     * @return The medical record of the specified patient.
     */
    MedicalRecord getMedicalRecord(String filepath, String patientID);

    /**
     * Saves a contact record to the CSV file.
     *
     * @param filepath The path to the contact records CSV file.
     * @param record   The medical record to save.
     */
    void saveContactRecordCSV(String filepath, MedicalRecord record);

    /**
     * Updates the diagnosis and treatment information in the CSV file for a patient.
     *
     * @param filepath   The path to the CSV file.
     * @param patientID  The ID of the patient.
     * @param diagnosis  The updated diagnosis information.
     * @param treatment  The updated treatment information.
     */
    void updateDiagnosisandTreatmentCSV(String filepath, String patientID, String diagnosis, String treatment);

    /**
     * Checks if a specific patient has an appointment with a specific doctor.
     *
     * @param filePath   The path to the appointments CSV file.
     * @param patientID  The ID of the patient.
     * @param doctorID   The ID of the doctor.
     * @return {@code true} if the patient has an appointment with the doctor; {@code false} otherwise.
     */
    Boolean CheckPatientApptDoctorCSV(String filePath, String patientID, String doctorID);
}
