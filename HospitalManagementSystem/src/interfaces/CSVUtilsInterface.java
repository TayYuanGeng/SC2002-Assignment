package interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.AppOutcome;
import models.Appointment;
import models.MedicalRecord;
import models.Medicine;
import models.Patient;
import models.ReplenishmentRequest;
import models.Staff;
import models.Unavailability;

public interface CSVUtilsInterface {
    void saveUserToCSV(String filePath, String username, String password, String role);

    void saveStaffToCSV(String filePath, Staff staff);

    void updateStaffInCSV(String filePath, Staff updatedStaff);

    void updatePatientInCSV(String filePath, Patient updatedPatient);

    ArrayList<Staff> StaffDataInit(String filePath, ArrayList<Staff> staffList);

    ArrayList<Medicine> MedicineDataInit(String filePath);

    ArrayList<Patient> PatientDataInit(String filePath, ArrayList<Patient> patientList);

    void removeStaffInCSV(String filePath, Staff removeStaff);

    void saveMedToCSV(String filePath, Medicine med);

    void removeMedInCSV(String filePath, Medicine removeMedicine);

    void updateMedInCSV(String filePath, Medicine updatedMedicine);

    ArrayList<ReplenishmentRequest> ReadReplenishRequestCSV(String filePath);

    boolean saveReplenishReqToCSV(String filePath, ReplenishmentRequest request);

    void updateRepReqInCSV(String filePath, ReplenishmentRequest request);

    List<Appointment> DataInitApptReq(String filePath);

    List<Unavailability> DataInitUnavail(String filePath);

    Map<String, String> DataInitPatient(String filePath);

    Map<String, String> DataInitStaff(String filePath);

    List<AppOutcome> DataInitApptOutcome(String filePath);

    void writeToCSV(String fileName, List<?> dataList);

    MedicalRecord getMedicalRecord(String filepath, String patientID);

    void saveContactRecordCSV(String filepath, MedicalRecord record);

    void updateDiagnosisandTreatmentCSV(String filepath, String patientID, String diagnosis, String treatment);

}
