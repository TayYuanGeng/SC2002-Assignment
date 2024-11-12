package models;

import controllers.CSVUtilsController;

public class MedicalRecordRepo {
    private String filepath;

    public MedicalRecordRepo(String filepath)
    {
        this.filepath = filepath;
    }

    public MedicalRecord loadRecord(String patientID)
    {
        return CSVUtilsController.Read_Patientcsv(filepath, patientID);
    }


    public void saveContactRecord(MedicalRecord record)
    {
        CSVUtilsController.saveContactRecordCSV(filepath,record);
    }

    public void updateDiagnosisandTreatment(String patientID, String diagnosis, String treatment)
    {
        CSVUtilsController.updateDiagnosisandTreatmentCSV(filepath, patientID, diagnosis, treatment);
    }
}
