package models;

import interfaces.IMedicalRecordService;

public class MedicalRecordService implements IMedicalRecordService {
    // used to read/write to csv
    private MedicalRecordRepo recordRepo;

    public MedicalRecordService(MedicalRecordRepo recordRepo)
    {
        this.recordRepo = recordRepo;
    }

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
