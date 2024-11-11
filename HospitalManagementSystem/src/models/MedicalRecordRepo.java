package models;


import controllers.CSVUtils;

public class MedicalRecordRepo {
    private String filepath;

    public MedicalRecordRepo(String filepath)
    {
        this.filepath = filepath;
    }

    public MedicalRecord loadRecord(String patientID)
    {
        return CSVUtils.Read_Patientcsv(filepath, patientID);
    }


    public void saveContactRecord(MedicalRecord record)
    {
        CSVUtils.saveContactRecordCSV(filepath,record);
    }

    public void updateDiagnosisandTreatment(String patientID, String diagnosis, String treatment)
    {
        CSVUtils.updateDiagnosisandTreatmentCSV(filepath, patientID, diagnosis, treatment);
    }
}
