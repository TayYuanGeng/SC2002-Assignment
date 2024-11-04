package models;

import interfaces.Outcome;
import java.time.LocalDate;

public class AppOutcome implements Outcome {
    private LocalDate dateOfApp;
    private String serviceType;
    private String prescriptionStatus = "pending";
    private String[] medicationNames;
    private String consultationNotes;

    public AppOutcome(LocalDate dateOfApp, String serviceType, String[] medications, String notes) {
        this.dateOfApp = dateOfApp;
        this.serviceType = serviceType;
        this.medicationNames = medications;
        this.consultationNotes = notes;
    }

    public LocalDate getDateOfApp() {
        return dateOfApp;
    }

    @Override
    public void setDateOfApp(LocalDate date) {
        this.dateOfApp = date;
    }

    public String getServiceType() {
        return serviceType;
    }

    @Override
    public void setServiceType(String service) {
        this.serviceType = service;
    }

    public String[] getMedications() {
        return medicationNames;
    }

    @Override
    public void setMedications(String[] medicines) {
        this.medicationNames = medicines;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    @Override
    public void setConsultationNotes(String notes) {
        this.consultationNotes = notes;
    }

    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }

    public void setPrescriptionStatus(String status) {
        this.prescriptionStatus = status;
    }
}