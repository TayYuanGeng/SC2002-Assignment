package interfaces;

public interface IMedicalRecordService {
    void getMedicalRecord(String patientID);
    void updateContact(String patientID, String new_email, String new_phone);
    void updateDiagnosisandTreatment(String patientID, String diagnosis, String treatment);
}

