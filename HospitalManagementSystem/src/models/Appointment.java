package models;

import interfaces.Outcome;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Appointment {
    private String patientID;
    private String doctorID;
    private AppointmentStatus appointmentStatus;
    private Outcome appOutcome;
    private LocalDateTime appointmentDateTime;
    private static List<Appointment> appointments = new ArrayList<>();
    private static Map<String, List<LocalDateTime>> doctorAvailability = new HashMap<>(); // Availability per doctor

    public Appointment(String patientID, String doctorID, AppointmentStatus appointmentStatus, Outcome appOutcome, LocalDateTime appointmentDateTime) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentStatus = appointmentStatus;
        this.appOutcome = appOutcome;
        this.appointmentDateTime = appointmentDateTime;
        appointments.add(this);
    }

    // Allows doctor to set or update availability for specific dates and times
    public static void setAvailability(String doctorID, LocalDate date, List<LocalTime> availableTimes) {
        List<LocalDateTime> availableSlots = new ArrayList<>();
        for (LocalTime time : availableTimes) {
            availableSlots.add(LocalDateTime.of(date, time));
        }
        doctorAvailability.put(doctorID, availableSlots);
    }

    // Method to print out a doctor's availability
    public static void printDoctorAvailability(String doctorID) {
        List<LocalDateTime> availableSlots = doctorAvailability.get(doctorID);
        if (availableSlots == null || availableSlots.isEmpty()) {
            System.out.println("No available slots for Doctor ID: " + doctorID);
        } else {
            System.out.println("Available slots for Doctor ID: " + doctorID);
            for (LocalDateTime slot : availableSlots) {
                System.out.println(" - " + slot.toLocalDate() + " at " + slot.toLocalTime());
            }
        }
    }

    // Test Case 3: View Available Appointment Slots
    public static List<LocalDateTime> viewAvailableSlots(LocalDate date, String doctorID) {
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        
        while (startTime.isBefore(endTime)) {
            LocalDateTime slot = LocalDateTime.of(date, startTime);
            if (isSlotAvailable(slot, doctorID) && isDoctorAvailable(slot, doctorID)) {
                availableSlots.add(slot);
            }
            startTime = startTime.plusHours(1);
        }
        return availableSlots;
    }

    public static void printAvailableSlotsForPatient(LocalDate date, String doctorID) {
        List<LocalDateTime> availableSlots = viewAvailableSlots(date, doctorID);
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for Doctor ID: " + doctorID + " on " + date);
        } else {
            System.out.println("Available slots for Doctor ID: " + doctorID + " on " + date + ":");
            for (LocalDateTime slot : availableSlots) {
                System.out.println(" - " + slot.toLocalTime());
            }
        }
    }

    // Checks if the slot is available based on existing appointments
    private static boolean isSlotAvailable(LocalDateTime slot, String doctorID) {
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID) && appointment.getAppointmentDateTime().equals(slot) && appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                return false;
            }
        }
        return true;
    }

    // Checks if the doctor has marked the slot as available
    private static boolean isDoctorAvailable(LocalDateTime slot, String doctorID) {
        List<LocalDateTime> doctorSlots = doctorAvailability.get(doctorID);
        return doctorSlots != null && doctorSlots.contains(slot);
    }

    // Test Case 4: Schedule an Appointment
    public static Appointment scheduleAppointment(String patientID, String doctorID, LocalDateTime dateTime) {
        if (!isSlotAvailable(dateTime, doctorID) || !isDoctorAvailable(dateTime, doctorID)) {
            throw new IllegalArgumentException("Time slot is already booked or not available.");
        }
        Appointment newAppointment = new Appointment(patientID, doctorID, AppointmentStatus.CONFIRMED, null, dateTime);
        return newAppointment;
    }

    // Test Case 5: Reschedule an Appointment
    public void rescheduleAppointment(LocalDateTime newDateTime) {
        if (!isSlotAvailable(newDateTime, doctorID) || !isDoctorAvailable(newDateTime, doctorID)) {
            throw new IllegalArgumentException("New time slot is already booked or not available.");
        }
        this.appointmentStatus = AppointmentStatus.CANCELED; // Cancel the current appointment
        new Appointment(patientID, doctorID, AppointmentStatus.CONFIRMED, this.appOutcome, newDateTime);
    }

    // Test Case 6: Cancel an Appointment
    public void cancelAppointment() {
        this.appointmentStatus = AppointmentStatus.CANCELED;
    }

    // Test Case 7: View Scheduled Appointments
    public static List<Appointment> viewScheduledAppointments(String patientID) {
        List<Appointment> scheduledAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID) && appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                scheduledAppointments.add(appointment);
            }
        }
        return scheduledAppointments;
    }

    // Test Case 8: View Past Appointment Outcome Records
    public static List<Outcome> viewPastAppointments(String patientID) {
        List<Outcome> pastOutcomes = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID) && appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED && appointment.getAppOutcome() != null) {
                pastOutcomes.add(appointment.getAppOutcome());
            }
        }
        return pastOutcomes;
    }

    // Test Case 21: View All Appointments for Administrator
    public static List<Appointment> viewAllAppointments() {
        return new ArrayList<>(appointments);
    }

    // Getters and Setters
    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public Outcome getAppOutcome() {
        return appOutcome;
    }
}