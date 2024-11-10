package controllers;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import models.*;
import models.Appointment.AppointmentStatus;

public class AppointmentController {
    private String patientID;
    private String doctorID;
    private AppointmentStatus appointmentStatus;
    private AppOutcome outcome;
    private String appointmentDateTime;
    private static final String STAFF_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/Staff_List.csv";
    private static final String PATIENT_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/Patient_List.csv";
    private static final String UNAVAIL_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/Unavailability.csv";
    private static final String APPTREQ_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/ApptRequest.csv";
    private static final String APPTOUTCOME_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/ApptOutcome.csv";
    private static Map<String, String> patientIdToNameMap = CSVUtils.DataInitPatient(PATIENT_CSV_FILE);
    private static Map<String, String> doctorIdToNameMap = CSVUtils.DataInitStaff(STAFF_CSV_FILE);
    private static List<Appointment> appointments = CSVUtils.DataInitApptReq(APPTREQ_CSV_FILE);
    private static List<Unavailability> unavailabilities = CSVUtils.DataInitUnavail(UNAVAIL_CSV_FILE);
    private static List<AppOutcome> appOutcomes = CSVUtils.DataInitApptOutcome(APPTOUTCOME_CSV_FILE);

    public static void main(String[] args){
        completeAppointment("D001", "P1002", "12-03-2025 13:00");
    }
    
    // Update doctor unavailability in CSV (Doctor)
    public static void updateDoctorUnavailability(String dateTime, String doctorID) {
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

    // Show doctor unavailability in CSV (Patient)
    public static void showDoctorUnavailability() {
        System.out.println("Time slots of doctors not available: ");
        boolean hasUnavailability = false;
        
        for (Unavailability unavail : unavailabilities) {
            System.out.println(unavail.getDateTime() + ", Doctor Name: " + doctorIdToNameMap.get(unavail.getDoctorID()));
            hasUnavailability = true;
        }
        if (!hasUnavailability) {
            System.out.println("No unavailable time slots found.");
        }
    }

    // Show doctor unavailability in CSV (Doctor)
    public static void showDoctorSchedule(String doctorID){
        System.out.println("Time slots you are not available: ");
        boolean hasUnavailability = false;
        
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

    // Show appointment requests from CSV (Doctor)
    public static List<Appointment> showAppointmentRequests(String doctorID) {
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

    // Accepts or declines a request (Doctor)
    public static void respondToRequest(String doctorID, String patientID, String dateTime, boolean accept) {
        // Iterate through the appointments to find the matching request
        for (Appointment appt : appointments) {
            for (Unavailability unavail : unavailabilities) {
                if (unavail.getDateTime().equals(dateTime) && unavail.getDoctorID().equals(doctorID)) {
                    System.out.println("Unavailable at that time!");
                    return;
                }
            }
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
                        CSVUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);
                        CSVUtils.writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
                        System.out.println("Appointment Accepted!");
                    } else {
                        CSVUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);
                        System.out.println("Appointment Declined!");
                    }
                    return;
                }
            }
        }
        System.out.println("No matching appointment found.");
    }

    // Mark appointment as completed and update outcome (Doctor)
    public static void completeAppointment(String doctorID, String patientID, String dateTime){
        // Find the appointment to complete
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
        CSVUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);  // Updating appointments CSV with "COMPLETED" status
        CSVUtils.writeToCSV(APPTOUTCOME_CSV_FILE, appOutcomes);
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

    // Read appointment outcome
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

    // Show upcoming appointment from CSV (Doctor)
    public static void showUpcomingAppointment(String doctorID){
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

    // Cancel appointment requests in CSV (Doctor)
    public static void cancelAppointmentRequests(String doctorID, String dateTime) {
        for (Appointment appt : appointments) {
            if (appt.getDoctorID().equals(doctorID) && appt.getAppointmentDateTime().equals(dateTime) && appt.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)){
                appt.setAppointmentStatus(AppointmentStatus.CANCELLED);
                unavailabilities.removeIf(unavail -> unavail.getDoctorID().equals(appt.getDoctorID()) 
                                            && unavail.getDateTime().equals(appt.getAppointmentDateTime()));
                break;
            }
        }
        CSVUtils.writeToCSV(UNAVAIL_CSV_FILE, appointments);
        CSVUtils.writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
    }

    // Check if slot is available for booking
    public static boolean isSlotAvailable(String dateTime, String doctorID) {
        for (Unavailability unavail : unavailabilities) {
            if (unavail.getDoctorID().equals(doctorID) && unavail.getDateTime().equals(dateTime)) {
                return false;
            }
        }
        return true;
    }

    // Schedule Appointment (Patient)
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

    // Reschedule Appointment (Patient)
    public static void rescheduleAppointment(String patientID, String dateTime, String newDateTime) {
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
            CSVUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);
            CSVUtils.writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
    
            System.out.println("Appointment rescheduled successfully!");
        } else {
            System.out.println("Doctor's time slot is unavailable!");
        }
    }
    
    // Cancel Appointment (Patient)
    public static void cancelAppointment(String patientID, String dateTime){
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
        CSVUtils.writeToCSV(APPTREQ_CSV_FILE, appointments);
        CSVUtils.writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);

        System.out.println("Appointment cancelled successfully! The time slot has been made available.");
    }

    // Show appointment status (Patient)
    public static void showPatientAppointment(String patientID){
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
    // Update medication status dispensed (Pharmacist)
    public static void setPrescriptionStatus(String patientID){
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
                CSVUtils.writeToCSV(APPTOUTCOME_CSV_FILE, appOutcomes);
                System.out.println("Medications dispensed!");
                return;
            }
        }
        System.out.println("No medications pending dispensed!");
    }
}