package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an appointment in the hospital management system.
 */
public class Appointment {
    private String patientID;
    private String doctorID;
    private AppointmentStatus appointmentStatus;
    private AppOutcome outcome;
    private String appointmentDateTime;
    /**
     * Formatter for date and time in the format "dd-MM-yyyy HH:mm".
     */
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Enum representing the status of an appointment.
     */
    public enum AppointmentStatus {
        PENDING, /**< Appointment is pending confirmation. */
        CONFIRMED, /**< Appointment is confirmed. */
        CANCELLED, /**< Appointment is cancelled. */
        COMPLETED, /**< Appointment is completed. */
    }

    /**
     * Constructs a new appointment for a patient.
     *
     * @param dateTime The date and time of the appointment, in "dd-MM-yyyy HH:mm" format.
     * @param doctorID The doctor's ID.
     * @param patientID The patient's ID.
     */
    public Appointment(String dateTime, String doctorID, String patientID) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, formatter);
        this.appointmentDateTime = parsedDateTime.format(formatter);
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.appointmentStatus = AppointmentStatus.PENDING;
    }

    /**
     * Constructs an appointment from CSV data.
     *
     * @param dateTime The date and time of the appointment, in "dd-MM-yyyy HH:mm" format.
     * @param doctorID The doctor's ID.
     * @param patientID The patient's ID.
     * @param appointmentStatus The status of the appointment.
     */
    public Appointment(String dateTime, String doctorID, String patientID, AppointmentStatus appointmentStatus) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, formatter);
        this.appointmentDateTime = parsedDateTime.format(formatter);
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.appointmentStatus = appointmentStatus;
    }
    
    /**
     * Gets the patient ID.
     *
     * @return The patient's ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Gets the doctor ID.
     *
     * @return The doctor's ID.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Gets the appointment status.
     *
     * @return The status of the appointment.
     */
    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    /**
     * Sets the appointment status.
     *
     * @param status The new status of the appointment.
     */
    public void setAppointmentStatus(AppointmentStatus status) {
        this.appointmentStatus = status;
    }

    /**
     * Gets the appointment date and time.
     *
     * @return The appointment date and time in "dd-MM-yyyy HH:mm" format.
     */
    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    /**
     * Sets the appointment date and time.
     *
     * @param dateTime The new date and time of the appointment in "dd-MM-yyyy HH:mm" format.
     */
    public void setAppointmentDateTime(String dateTime){
        this.appointmentDateTime = dateTime;
    }

    /**
     * Gets the outcome of the appointment.
     *
     * @return The appointment outcome.
     */
    public AppOutcome getOutcome() {
        return outcome;
    }

    /**
     * Sets the outcome of the appointment.
     *
     * @param outcome The new appointment outcome.
     */
    public void setOutcome(AppOutcome outcome) {
        this.outcome = outcome;
    }
}