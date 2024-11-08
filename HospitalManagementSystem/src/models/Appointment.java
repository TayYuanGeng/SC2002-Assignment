package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private String patientID;
    private String doctorID;
    private AppointmentStatus appointmentStatus;
    private AppOutcome outcome;
    private String appointmentDateTime;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED,
    }

    // Appointment Constructor [USE THIS FOR PATIENT APPT]
    public Appointment(String dateTime, String doctorID, String patientID) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, formatter);
        this.appointmentDateTime = parsedDateTime.format(formatter);
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.appointmentStatus = AppointmentStatus.PENDING;
        //appointments.add(this);
    }

    // Appointment Constructor [THIS IS USED FOR EXTRACTING CSV]
    public Appointment(String dateTime, String doctorID, String patientID, AppointmentStatus appointmentStatus) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, formatter);
        this.appointmentDateTime = parsedDateTime.format(formatter);
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.appointmentStatus = appointmentStatus;
    }
    
    // Getters & Setters
    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus status) {
        this.appointmentStatus = status;
    }

    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(String dateTime){
        this.appointmentDateTime = dateTime;
    }

    public AppOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(AppOutcome outcome) {
        this.outcome = outcome;
    }
}