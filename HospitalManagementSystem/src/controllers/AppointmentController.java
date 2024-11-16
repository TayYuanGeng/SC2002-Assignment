package controllers;

import interfaces.CSVUtilsInterface;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import models.*;
import models.Appointment.AppointmentStatus;

/**
 * Controller class to manage appointments, doctor unavailability, and appointment outcomes.
 * This class provides methods for scheduling, responding to, and completing appointments,
 * as well as managing unavailability and outcomes.
 */
public class AppointmentController {
    static CSVUtilsInterface csvUtils = new CSVUtilsController();
    private static final String UNAVAIL_CSV_FILE = MainMenuController.CSV_FILE_PATH + "Unavailability.csv";
    private static final String APPTREQ_CSV_FILE = MainMenuController.CSV_FILE_PATH + "ApptRequest.csv";
    private static final String APPTOUTCOME_CSV_FILE = MainMenuController.CSV_FILE_PATH + "ApptOutcome.csv";
    private static Map<String, String> patientIdToNameMap = csvUtils.DataInitPatient(MainMenuController.CSV_FILE_PATH + "Patient_List.csv");
    private static Map<String, String> doctorIdToNameMap = csvUtils.DataInitStaff(MainMenuController.CSV_FILE_PATH + "Staff_List.csv");
    private static List<Appointment> appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
    private static List<Unavailability> unavailabilities = csvUtils.DataInitUnavail(MainMenuController.CSV_FILE_PATH + "Unavailability.csv");
    private static List<AppOutcome> appOutcomes = csvUtils.DataInitApptOutcome(MainMenuController.CSV_FILE_PATH + "ApptOutcome.csv");

    public static void main(String[] args){
        completeAppointment("D001", "P1002", "12-03-2025 13:00");
    }
    
    /**
     * Updates the doctor's unavailability for a specified date and time.
     *
     * @param dateTime the date and time of unavailability in the format "dd-MM-yyyy HH:mm"
     * @param doctorID the ID of the doctor whose unavailability is being updated
     */
    public static void updateDoctorUnavailability(String dateTime, String doctorID) {
        unavailabilities = csvUtils.DataInitUnavail(MainMenuController.CSV_FILE_PATH + "Unavailability.csv");
        for (Unavailability unavail : unavailabilities){
            if (unavail.getDateTime().equals(dateTime) && unavail.getDoctorID().equals(doctorID)){
                System.out.println("Duplicate records!");
                return;
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(UNAVAIL_CSV_FILE), StandardOpenOption.APPEND)) {
            writer.write("\n" + dateTime +  "," + doctorID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the doctor's unavailability based on filters for doctor and/or date.
     * Optionally, shows available time slots for a specific date and doctor.
     *
     * @param doctorFilter   filter to match a specific doctor's name (case-insensitive)
     * @param dateFilter     filter to match a specific date in the format "dd-MM-yyyy"
     * @param checkingAvail  true if showing available slots; false if showing unavailable slots
     */
    public static void showDoctorUnavailability(String doctorFilter, String dateFilter, boolean checkingAvail) {
        unavailabilities = csvUtils.DataInitUnavail(MainMenuController.CSV_FILE_PATH + "Unavailability.csv");
        if (doctorFilter.isEmpty() && !dateFilter.isEmpty()) {
            System.out.println("Doctors not available on the given date: ");
        } else if (!doctorFilter.isEmpty() && dateFilter.isEmpty()) {
            System.out.println("Time slots of the given doctor not available: ");
        } else if (!doctorFilter.isEmpty() && !dateFilter.isEmpty() && checkingAvail == false) {
            System.out.println("Time slots of the given doctor on the given date not available: ");
        } else if (checkingAvail == true) {
            System.out.println("Showing available time slots on " + dateFilter + " for Doctor " + doctorFilter + ":");
        } else {
            System.out.println("Showing all unavailable time slots: ");
        }

        if (checkingAvail == true) {
            String[] allTimeSlots = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};
            boolean hasAvailableSlots = false;
    
            // Iterate through all possible time slots and check for availability
            for (String timeSlot : allTimeSlots) {
                String dateTime = dateFilter + " " + timeSlot;
                boolean isUnavailable = false;
    
                // Check if this time slot is unavailable for the specified doctor
                for (Unavailability unavail : unavailabilities) {
                    if (unavail.getDateTime().equals(dateTime) && doctorIdToNameMap.get(unavail.getDoctorID()).contains(doctorFilter)) {
                        isUnavailable = true;
                        break;
                    }
                }
    
                // If not unavailable, then it's an available time slot
                if (!isUnavailable) {
                    System.out.println(dateTime);
                    hasAvailableSlots = true;
                }
            }
    
            if (!hasAvailableSlots) {
                System.out.println("No available time slots for the given doctor on the given date.");
            }
        }
        else {
            boolean hasUnavailability = false;
            for (Unavailability unavail : unavailabilities) {
                boolean matchesDoctor = doctorFilter.isEmpty() || doctorIdToNameMap.get(unavail.getDoctorID()).contains(doctorFilter);
                boolean matchesDate = dateFilter.isEmpty() || unavail.getDateTime().contains(dateFilter);
    
                // Apply filters
                if (matchesDoctor && matchesDate) {
                    System.out.println(unavail.getDateTime() + ", Doctor Name: " + doctorIdToNameMap.get(unavail.getDoctorID()));
                    hasUnavailability = true;
                }
            }
            if (!hasUnavailability) {
                System.out.println("No unavailable time slots found.");
            }
        }
    }

    /**
     * Displays the schedule of unavailable time slots for a specific doctor.
     *
     * @param doctorID the ID of the doctor whose schedule is being shown
     */
    public static void showDoctorSchedule(String doctorID){
        System.out.println("Time slots you are not available: ");
        boolean hasUnavailability = false;
        
        unavailabilities = csvUtils.DataInitUnavail(MainMenuController.CSV_FILE_PATH + "Unavailability.csv");

        for (Unavailability unavail : unavailabilities) {
            if (unavail.getDoctorID().equals(doctorID)) {
                System.out.println(unavail.getDateTime());
                hasUnavailability = true;
            }
        }
        if (!hasUnavailability) {
            System.out.println("No unavailable time slots found.");
        }
    }

    /**
     * Retrieves and displays appointment requests for a specified doctor.
     * Only appointments with a status of PENDING are shown.
     *
     * @param doctorID the ID of the doctor for whom appointment requests are being retrieved
     * @return a list of pending appointments for the doctor
     */
    public static List<Appointment> showAppointmentRequests(String doctorID) {
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        System.out.println("Pending bookings from patient:");
        List<Appointment> pendingAppointments = new ArrayList<>();
        boolean hasPendingAppointments = false;
        for (Appointment appt: appointments){ 
            if (appt.getDoctorID().equals(doctorID) && appt.getAppointmentStatus() == AppointmentStatus.PENDING) {
                String patientName = patientIdToNameMap.getOrDefault(appt.getPatientID(), "Unknown Patient");
                System.out.println("DateTime Slot: " + appt.getAppointmentDateTime() + ", Patient Name: " + patientName);
                pendingAppointments.add(appt);
                hasPendingAppointments = true;
            }
        }
        if (!hasPendingAppointments) {
            System.out.println("No appointment request at the moment.");
        }
        return pendingAppointments;
    }

    /**
     * Responds to an appointment request by accepting or declining it.
     *
     * @param doctorID  the ID of the doctor responding to the request
     * @param patientID the ID of the patient making the request
     * @param dateTime  the requested date and time of the appointment in "dd-MM-yyyy HH:mm"
     * @param accept    true to accept the request, false to decline
     */
    public static void respondToRequest(String doctorID, String patientID, String dateTime, boolean accept) {
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        unavailabilities = csvUtils.DataInitUnavail(MainMenuController.CSV_FILE_PATH + "Unavailability.csv");
        // Iterate through the appointments to find the matching request
        for (Appointment appt : appointments) {
            if (appt.getDoctorID().equals(doctorID) && appt.getPatientID().equals(patientID) && appt.getAppointmentDateTime().equals(dateTime)) {
                
                if (appt.getAppointmentStatus() == AppointmentStatus.CONFIRMED && accept == true){
                    System.out.println("Appointment already accepted!");
                    return;
                }
                else if (appt.getAppointmentStatus() == AppointmentStatus.CANCELLED && accept == false){
                    System.out.println("Appointment already cancelled!");
                    return;
                }
                else {
                    // Update the appointment status
                    appt.setAppointmentStatus(accept ? AppointmentStatus.CONFIRMED : AppointmentStatus.CANCELLED);
                    
                    // If the appointment is accepted, update the unavailability list
                    if (accept) {
                        unavailabilities.add(new Unavailability(dateTime, doctorID));
                        csvUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);
                        csvUtils.writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
                        System.out.println("Appointment Accepted!");
                    } else {
                        csvUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);
                        System.out.println("Appointment Declined!");
                    }
                    return;
                }
            }
        }

        for (Unavailability unavail : unavailabilities) {
            if (unavail.getDateTime().equals(dateTime) && unavail.getDoctorID().equals(doctorID)) {
                System.out.println("Unavailable at that time!");
                return;
            }
        }
        System.out.println("No matching appointment found.");
    }

    /**
     * Marks an appointment as completed and records its outcome, including service type,
     * consultation notes, and prescribed medications.
     *
     * @param doctorID  the ID of the doctor completing the appointment
     * @param patientID the ID of the patient for whom the appointment is being completed
     * @param dateTime  the date and time of the appointment in "dd-MM-yyyy HH:mm"
     */
    public static void completeAppointment(String doctorID, String patientID, String dateTime){
        // Find the appointment to complete
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        Appointment appointmentToComplete = null;
        
        for (Appointment appt : appointments) {
            if (appt.getDoctorID().equals(doctorID) && appt.getPatientID().equals(patientID) 
                    && appt.getAppointmentDateTime().equals(dateTime)) {
                        if (appt.getAppointmentStatus().equals(AppointmentStatus.COMPLETED)){
                            System.out.println("Appointment has been marked completed!");
                            return;
                        }
                appointmentToComplete = appt;
                break;
            }
        }
        
        // Check if the appointment exists
        if (appointmentToComplete == null) {
            System.out.println("Appointment not found.");
            return;
        }

        // Gather details for the outcome
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter type of service provided (e.g., consultation, X-ray): ");
        String serviceType = scanner.nextLine();

        System.out.println("Enter consultation notes: ");
        String notes = scanner.nextLine();

        // Add prescribed medications
        List<String> medications = new ArrayList<>();
        String medication;
        System.out.println("Enter medication name (or type 'done' to finish): ");
        do {
            medication = scanner.nextLine();
            if (!medication.equalsIgnoreCase("done")) {
                medications.add(medication);
            }
        } while (!medication.equalsIgnoreCase("done"));
        String prescriptionStatus = "Pending";
        appOutcomes.add(new AppOutcome(dateTime, patientID, serviceType, medications, notes, prescriptionStatus));

        // Update the status to COMPLETED
        appointmentToComplete.setAppointmentStatus(AppointmentStatus.COMPLETED);

        // Save to CSV or database if needed
        csvUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);  // Updating appointments CSV with "COMPLETED" status
        csvUtils.writeToCSV(APPTOUTCOME_CSV_FILE, appOutcomes);
        // Write the outcome data to CSV
        // try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPTOUTCOME_CSV_FILE, true))) {
        //     bw.write("\n" + outcome.getDateTime() + "," + patientID + "," + outcome.getServiceType() + "," + 
        //     String.join(" ", outcome.getPrescribedMedications()) + "," + outcome.getConsultationNotes() + "," + prescriptionStatus);
        // } catch (IOException e) {
        //     e.printStackTrace();
        //     System.out.println("Error writing to CSV file.");
        // }
        System.out.println("Appointment marked as completed and outcome recorded.");
    }

    /**
     * Reads and displays appointment outcomes for a specified patient.
     *
     * @param patientID the ID of the patient whose appointment outcomes are being retrieved
     */
    public static void readApptOutcome(String patientID) {
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;
    
        try (BufferedReader br = new BufferedReader(new FileReader(APPTOUTCOME_CSV_FILE))) {
            // Read the header
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header line
                    continue;
                }
    
                // Split the line by commas
                String[] values = line.split(csvSplitBy);
    
                // Check if this appointment belongs to the given patient
                String patientColumn = values[1];
    
                if (patientColumn.equals(patientID)) {
                    // Extract appointment outcome details
                    String dateTime = values[0];
                    String serviceType = values[2];
                    String medications = values[3];
                    String notes = values[4];
                    String status = values[5];
    
                    // Print the appointment details for the patient
                    System.out.println("Appointment Date/Time: " + dateTime);
                    System.out.println("Service Type: " + serviceType);
                    System.out.println("Medications: " + String.join(", ", medications).replace(" ", ", "));
                    System.out.println("Consultation Notes: " + notes);
                    System.out.println("Prescription Status: " + status);
                    System.out.println("-------------------------------");
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from CSV file.");
        }
        System.out.println("No Appointment Outcome yet!");
    }    

    /**
     * Displays upcoming appointments for a specific doctor.
     * 
     * @param doctorID The unique identifier of the doctor.
     */
    public static void showUpcomingAppointment(String doctorID){
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        boolean hasUpcomingAppt = false;
        System.out.println("Upcoming appointments:");
        for (Appointment appt : appointments){
            if (appt.getDoctorID().equals(doctorID) && appt.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                System.out.println("Time Slot: " + appt.getAppointmentDateTime() + ", Patient Name: " + patientIdToNameMap.get(appt.getPatientID()));
                hasUpcomingAppt = true;
            }
        }
        if (!hasUpcomingAppt){
            System.out.println("No appointments at the moment.");
        }
    }

    /**
     * Cancels a confirmed appointment request for a specific doctor at a given time.
     * 
     * @param doctorID The unique identifier of the doctor.
     * @param dateTime The time of the appointment to cancel.
     */
    public static void cancelAppointmentRequests(String doctorID, String dateTime) {
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        for (Appointment appt : appointments) {
            if (appt.getDoctorID().equals(doctorID) && appt.getAppointmentDateTime().equals(dateTime) && appt.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)){
                appt.setAppointmentStatus(AppointmentStatus.CANCELLED);
                unavailabilities.removeIf(unavail -> unavail.getDoctorID().equals(appt.getDoctorID()) 
                                            && unavail.getDateTime().equals(appt.getAppointmentDateTime()));
                break;
            }
        }
        csvUtils.writeToCSV(UNAVAIL_CSV_FILE, appointments);
        csvUtils.writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
    }

    /**
     * Checks if a specific time slot is available for booking for a given doctor.
     * 
     * @param dateTime The desired appointment time.
     * @param doctorID The unique identifier of the doctor.
     * @return True if the slot is available, false otherwise.
     */
    public static boolean isSlotAvailable(String dateTime, String doctorID) {
        unavailabilities = csvUtils.DataInitUnavail(MainMenuController.CSV_FILE_PATH + "Unavailability.csv");
        for (Unavailability unavail : unavailabilities) {
            if (unavail.getDoctorID().equals(doctorID) && unavail.getDateTime().equals(dateTime)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Schedules a new appointment for a patient with a specified doctor.
     * 
     * @param dateTime  The desired appointment time.
     * @param patientID The unique identifier of the patient.
     * @param doctorName The name of the doctor.
     */
    public static void scheduleAppointment(String dateTime, String patientID, String doctorName){
        String doctorID = doctorIdToNameMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(doctorName))
                .map(entry -> entry.getKey())
                .findFirst()
                .orElse(null);

        if (doctorID == null) {
            System.out.println("Doctor name not found.");
            return;
        }
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        for (Appointment appt : appointments) {
            if (appt.getAppointmentDateTime().equals(dateTime) && appt.getPatientID().equals(patientID)) {
                System.out.println("You already have an appointment at this time.");
                return;
            }
        }

        if(isSlotAvailable(dateTime, doctorID)){
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(APPTREQ_CSV_FILE), StandardOpenOption.APPEND)) {
                writer.write("\n" + dateTime + "," + doctorID + ',' + patientID + ',' + AppointmentStatus.PENDING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(UNAVAIL_CSV_FILE), StandardOpenOption.APPEND)) {
                writer.write("\n" + dateTime + "," + doctorID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Appointment Scheduled!");
            return;
        } else {
            System.out.println("Choose another time slot!");
        }
    }

    /**
     * Reschedules an existing appointment to a new time.
     * 
     * @param patientID The unique identifier of the patient.
     * @param dateTime  The current appointment time.
     * @param newDateTime The new appointment time.
     */
    public static void rescheduleAppointment(String patientID, String dateTime, String newDateTime) {
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        unavailabilities = csvUtils.DataInitUnavail(MainMenuController.CSV_FILE_PATH + "Unavailability.csv");
        Appointment appointmentToUpdate = appointments.stream()
            .filter(appt -> appt.getPatientID().equals(patientID) && appt.getAppointmentDateTime().equals(dateTime))
            .findFirst()
            .orElse(null);

        // Check if the patient has an existing appointment and get the doctorID
        if (appointmentToUpdate == null) {
            System.out.println("No existing appointment found for the specified time.");
            return;
        }

        final String doctorID = appointmentToUpdate.getDoctorID();

        // Check if the patient already has an appointment at the new dateTime
        if (appointments.stream().allMatch(appt -> appt.getAppointmentDateTime().equals(newDateTime) && appt.getPatientID().equals(patientID))) {
            System.out.println("You already have an appointment at this time.");
            return;
        }

        // Check if the new time slot is available for the doctor
        if (isSlotAvailable(newDateTime, doctorID)) {
            // Update appointment time
            appointmentToUpdate.setAppointmentDateTime(newDateTime);
    
            // Update unavailability: remove old slot and add new slot
            unavailabilities.removeIf(unavail -> unavail.getDateTime().equals(dateTime) && unavail.getDoctorID().equals(doctorID));
            unavailabilities.add(new Unavailability(newDateTime, doctorID));
    
            // Write updated appointments and unavailabilities to CSV
            csvUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);
            csvUtils.writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
    
            System.out.println("Appointment rescheduled successfully!");
        } else {
            System.out.println("Doctor's time slot is unavailable!");
        }
    }
    
    /**
     * Cancels an existing appointment for a patient at a specific time.
     * 
     * @param patientID The unique identifier of the patient.
     * @param dateTime  The time of the appointment to cancel.
     */
    public static void cancelAppointment(String patientID, String dateTime){
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        // Find the appointment to cancel
        Appointment appointmentToCancel = appointments.stream()
        .filter(appt -> appt.getPatientID().equals(patientID) && appt.getAppointmentDateTime().equals(dateTime))
        .findFirst()
        .orElse(null);

        // Check if the appointment exists
        if (appointmentToCancel == null) {
        System.out.println("No appointment found for the specified time and patient.");
        return;
        }

        // Get doctor ID from the appointment
        String doctorID = appointmentToCancel.getDoctorID();

        // Remove the appointment from the list
        appointments.remove(appointmentToCancel);

        // Update unavailability: remove the unavailability associated with this appointment
        unavailabilities.removeIf(unavail -> unavail.getDateTime().equals(dateTime) && unavail.getDoctorID().equals(doctorID));

        // Write updated appointments and unavailabilities to CSV
        csvUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);
        csvUtils.writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);

        System.out.println("Appointment cancelled successfully! The time slot has been made available.");
    }

    /**
     * Displays all upcoming appointments for a specific patient.
     * 
     * @param patientID The unique identifier of the patient.
     */
    public static void showPatientAppointment(String patientID){
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        boolean hasAppt = false;
        System.out.println("Upcoming appointments:");
        for (Appointment appt : appointments){
            if (appt.getPatientID().equals(patientID)) {
                System.out.println("Time Slot: " + appt.getAppointmentDateTime() + ", Doctor Name: " + doctorIdToNameMap.get(appt.getDoctorID()) + ", Status: " + appt.getAppointmentStatus());
                hasAppt = true;
            }
        }
        if (!hasAppt){
            System.out.println("No appointments at the moment.");
        }
    }
    
    /**
     * Updates the prescription status of medications for a patient whose appointment is completed.
     * 
     * @param patientID The unique identifier of the patient.
     */
    public static void setPrescriptionStatus(String patientID){
        appointments = csvUtils.DataInitApptReq(MainMenuController.CSV_FILE_PATH + "ApptRequest.csv");
        appOutcomes = csvUtils.DataInitApptOutcome(MainMenuController.CSV_FILE_PATH + "ApptOutcome.csv");
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID) && appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
                for (AppOutcome appOutcome : appOutcomes){
                    if (appointment.getAppointmentDateTime().equals(appOutcome.getDateTime())){
                        if (appOutcome.getPrescriptionStatus().equals("Dispensed")){
                            System.out.println("Medications already dispensed!");
                            return;
                        }
                        appOutcome.setPrescriptionStatus();
                    }
                }
                csvUtils.writeToCSV(APPTOUTCOME_CSV_FILE, appOutcomes);
                System.out.println("Medications dispensed!");
                return;
            }
        }
        System.out.println("No medications pending dispensed!");
    }
}