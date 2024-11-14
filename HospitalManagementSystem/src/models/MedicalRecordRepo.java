package models;

import controllers.CSVUtilsController;
import interfaces.CSVUtilsInterface;

public class MedicalRecordRepo {
    private String filepath;
    static CSVUtilsInterface csvUtils = new CSVUtilsController();

    public MedicalRecordRepo(String filepath)
    {
        this.filepath = filepath;
    }

    public MedicalRecord loadRecord(String patientID)
    {
        return csvUtils.getMedicalRecord(filepath, patientID);
    }


    public void saveContactRecord(MedicalRecord record)
    {
        csvUtils.saveContactRecordCSV(filepath,record);
    }

    public void updateDiagnosisandTreatment(String patientID, String diagnosis, String treatment)
    {
        csvUtils.updateDiagnosisandTreatmentCSV(filepath, patientID, diagnosis, treatment);
    }
}
