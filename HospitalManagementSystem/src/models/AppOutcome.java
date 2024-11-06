package models;

public class AppOutcome {
    private String patientID;
    private String doctorID;
    private String consultationNotes;
    private String prescriptionStatus = "Pending";

    public AppOutcome(String patientID, String doctorID, String consultationNotes) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.consultationNotes = consultationNotes;
    }

    // Getters and Setters
    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }

    public void setconsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public void setPrescriptionStatus() {
        this.prescriptionStatus = "Dispensed";
    }
}