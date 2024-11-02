package models;

import interfaces.Outcome;
import java.time.LocalDate;

public class Appointment {
    private String patientID;
    private String doctorID;
    private AppointmentStatus appointmentStatus;
    private Outcome appOutcome;
    private LocalDate appointmentDate;

    public Appointment(String patientID, String doctorID, AppointmentStatus appointmentStatus, Outcome appOutcome, LocalDate appointmentDate) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentStatus = appointmentStatus;
        this.appOutcome = appOutcome;
        this.appointmentDate = appointmentDate;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        if (patientID == null || patientID.isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be null or empty");
        }
        this.patientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        if (doctorID == null || doctorID.isEmpty()) {
            throw new IllegalArgumentException("Doctor ID cannot be null or empty");
        }
        this.doctorID = doctorID;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        if (appOutcome != null) {
            appOutcome.setDateOfApp(appointmentDate);
        }
        this.appointmentDate = appointmentDate;
    }
}