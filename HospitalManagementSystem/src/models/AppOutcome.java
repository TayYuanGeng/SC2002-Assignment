package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the outcome of an appointment.
 */
public class AppOutcome {
    private String dateTime;
    private String patientID;
    private String serviceType;
    private List<String> prescribedMedications = new ArrayList<>();
    private String consultationNotes;
    private String prescriptionStatus = "Pending";

    /**
     * Constructs an AppOutcome object.
     *
     * @param dateTime             The date and time of the appointment outcome.
     * @param patientID            The ID of the patient associated with the appointment.
     * @param serviceType          The type of service provided during the appointment.
     * @param prescribedMedications A list of prescribed medications.
     * @param consultationNotes    The consultation notes from the appointment.
     * @param prescriptionStatus   The status of the prescription, defaulting to "Pending".
     */
    public AppOutcome(String dateTime, String patientID, String serviceType, List<String> prescribedMedications, String consultationNotes, String prescriptionStatus) {
        this.dateTime = dateTime;
        this.patientID = patientID;
        this.serviceType = serviceType;
        this.prescribedMedications = prescribedMedications;
        this.consultationNotes = consultationNotes;
        this.prescriptionStatus = prescriptionStatus;
    }

    /**
     * Gets the patient ID.
     *
     * @return The ID of the patient.
     */
    public String getPatientID(){
        return patientID;
    }

    /**
     * Sets the patient ID.
     *
     * @param patientID The new patient ID.
     */
    public void setPatientID(String patientID){
        this.patientID = patientID;
    }

    /**
     * Gets the date and time of the appointment outcome.
     *
     * @return The date and time as a string.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Gets the type of service provided during the appointment.
     *
     * @return The service type as a string.
     */
    public String getServiceType() {
        return serviceType;
    }
    
    /**
     * Gets the consultation notes from the appointment.
     *
     * @return The consultation notes.
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Gets the list of prescribed medications.
     *
     * @return A list of prescribed medication names.
     */
    public List<String> getPrescribedMedications() {
        return prescribedMedications;
    }

    /**
     * Gets the status of the prescription.
     *
     * @return The prescription status.
     */
    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }

    /**
     * Updates the prescription status to "Dispensed".
     */
    public void setPrescriptionStatus() {
        this.prescriptionStatus = "Dispensed";
    }

    /**
     * Sets the consultation notes for the appointment outcome.
     *
     * @param consultationNotes The new consultation notes.
     */
    public void setconsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

}