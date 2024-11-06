package models;

import java.util.ArrayList;
import java.util.List;

public class AppOutcome {
    private String dateTime;
    private String patientID;
    private String serviceType;
    private List<String> prescribedMedications = new ArrayList<>();
    private String consultationNotes;
    private String prescriptionStatus = "Pending";

    public AppOutcome(String dateTime, String patientID, String serviceType, List<String> prescribedMedications, String consultationNotes, String prescriptionStatus) {
        this.dateTime = dateTime;
        this.patientID = patientID;
        this.serviceType = serviceType;
        this.prescribedMedications = prescribedMedications;
        this.consultationNotes = consultationNotes;
        this.prescriptionStatus = prescriptionStatus;
    }

    // Getters and Setters
    public String getPatientID(){
        return patientID;
    }

    public void setPatientID(String patientID){
        this.patientID = patientID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public List<String> getPrescribedMedications() {
        return prescribedMedications;
    }

    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }

    public void setPrescriptionStatus() {
        this.prescriptionStatus = "Dispensed";
    }

    public void setconsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

}