package models;

import java.time.LocalDate;

public class Appointment {
    private String patientID;
    private String doctorID;
    private String appStatus;
    private Outcome appOutcome;

    public Appointment(String patientID, String doctorID, Outcome appOutcome) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appOutcome = appOutcome;
    }

    public void setStatus(String status) {
        this.appStatus = status;
    }

    public void setAppointmentDate(LocalDate newDate) {
        appOutcome.setDateOfApp(newDate);
    }

    // Getters for other properties if needed
}