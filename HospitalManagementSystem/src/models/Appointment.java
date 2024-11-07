package models;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Appointment {
    static Scanner sc = new Scanner(System.in);
    private String patientID;
    private String doctorID;
    private AppointmentStatus appointmentStatus;
    private AppOutcome outcome;
    private String appointmentDateTime;
    private static Map<String, String> patientIdToNameMap = new HashMap<>();
    private static Map<String, String> doctorIdToNameMap = new HashMap<>();
    private static List<Appointment> appointments = new ArrayList<Appointment>();
    private static List<Unavailability> unavailabilities = new ArrayList<Unavailability>();
    private static List<AppOutcome> appOutcomes = new ArrayList<AppOutcome>();
    private static final String STAFF_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/Staff_List.csv";
    private static final String PATIENT_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/Patient_List.csv";
    private static final String UNAVAIL_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/Unavailability.csv";
    private static final String APPTREQ_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/ApptRequest.csv";
    private static final String APPTOUTCOME_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/ApptOutcome.csv";
    public static void main(String[] args) throws Exception {
        DataInitUnavail(UNAVAIL_CSV_FILE);
        DataInitPatient(PATIENT_CSV_FILE);
        DataInitStaff(STAFF_CSV_FILE);
        DataInitApptOutcome(APPTOUTCOME_CSV_FILE);
        //updateDoctorUnavailability("19-10-2025 23:00", "D002");
        Appointment.showDoctorUnavailability();
        //showDoctorSchedule("D001");
        //showAppointmentRequests("D001");
        //respondToRequest("D002", "P1001", "13-01-2025 15:00", true);
        //respondToRequest("D002", "P1003", "12-01-2025 11:00", true);
        //showUpcomingAppointment("D001");
        //scheduleAppointment("23-01-2025 11:00", "P1001", "Emily Clarke");
        //rescheduleAppointment("P1001", "12-01-2025 14:00", "13-01-2025 14:00");
        //cancelAppointment("P1001", "12-01-2025 11:00");
        //showPatientAppointment("P1001");
        //completeAppointment("D001", "P1001", "13-01-2025 14:00");
        //completeAppointment("D002", "P1003", "12-01-2025 11:00");
        //readApptOutcome("P1002");
        //setPrescriptionStatus("P1003");
        //readApptOutcome("P1003");
		//WelcomePage();
	}

    public static void writeToCSV(String fileName, List<?> dataList) {
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitUnavail(UNAVAIL_CSV_FILE);
        DataInitApptOutcome(APPTOUTCOME_CSV_FILE);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            // Write headers based on whether it's appointments or unavailability
            if (dataList.equals(unavailabilities)){
                bw.write("DateTime,DoctorID");
                for (Object data : dataList) {
                    Unavailability unavailability = (Unavailability) data;
                    bw.write("\n" + unavailability.getDateTime() + "," + unavailability.getDoctorID());
                }
            } else if (dataList.equals(appointments)) {
                bw.write("DateTime Slot,DoctorID,PatientID,Status");
                for (Object data : dataList) {
                    Appointment appt = (Appointment) data;
                    bw.write("\n" + appt.getAppointmentDateTime() + "," +
                            appt.getDoctorID() + "," +
                            appt.getPatientID() + "," +
                            appt.getAppointmentStatus());
                }
            } else if (dataList.equals(appOutcomes)){
                bw.write("DateTime,PatientID,ServiceType,PrescribedMedications,ConsultationNotes,PrescriptionStatus");
                for (Object data : dataList) {
                    AppOutcome apptOut = (AppOutcome) data;
                    String medications = String.join(" ", apptOut.getPrescribedMedications());
                    bw.write("\n" + apptOut.getDateTime() + "," +
                    apptOut.getPatientID() + "," +
                    apptOut.getServiceType() + "," +
                    medications + "," + 
                    apptOut.getConsultationNotes() + "," + 
                    apptOut.getPrescriptionStatus());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to CSV file.");
        }
    }

    // Init Appt Req
    private static void DataInitApptReq(String filePath){
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
            	if(isFirstLine) {
            		isFirstLine = false;
            		continue;
            	}
                // Use comma as separator
                String[] values = line.split(csvSplitBy);
                
                // Example: Print the values
                // for (String value : values) {
                //     System.out.print(value + " ");
                // }
                appointments.add(new Appointment(values[0], values[1], values[2], AppointmentStatus.valueOf(values[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Init Unavailability Sched
    private static void DataInitUnavail(String filePath){
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            //System.out.println("The following doctor's dates and times are unavailable for booking:");
            while ((line = br.readLine()) != null) {
            	if(isFirstLine) {
            		isFirstLine = false;
            		continue;
            	}
                // Use comma as separator
                String[] values = line.split(csvSplitBy);
                //System.out.print("Slot: " + values[0] + ", Doctor ID: " + values[1]);
                unavailabilities.add(new Unavailability(values[0], values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Init patientID to patient name
    private static void DataInitPatient(String filePath){
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
            	if(isFirstLine) {
            		isFirstLine = false;
            		continue;
            	}
                // Use comma as separator
                String[] values = line.split(csvSplitBy);
                patientIdToNameMap.put(values[0], values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Init staffID to staff name
    private static void DataInitStaff(String filePath){
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
            	if(isFirstLine) {
            		isFirstLine = false;
            		continue;
            	}
                // Use comma as separator
                String[] values = line.split(csvSplitBy);
                if (values[2].equals("Doctor")) {
                    doctorIdToNameMap.put(values[0], values[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Init Appt Outcome
    private static void DataInitApptOutcome(String filePath){
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
            	if(isFirstLine) {
            		isFirstLine = false;
            		continue;
            	}
                // Use comma as separator
                String[] values = line.split(csvSplitBy);

            // Extract data and create AppOutcome
            String dateTime = values[0];
            String patientID = values[1];
            String serviceType = values[2];
            String medications = values[3];
            String notes = values[4];
            String status = values[5];

            // Convert medications from a single string to a list
            List<String> medicationsList = new ArrayList<>(Arrays.asList(medications.split(" ")));

            // Create an AppOutcome object and add it to the list
            appOutcomes.add(new AppOutcome(dateTime, patientID, serviceType, medicationsList, notes, status));
        }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from CSV file.");
        }
    }


    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED,
    }

    // DateTimeFormatter to format the date
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

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
    
    // Update doctor unavailability in CSV (Doctor)
    public static void updateDoctorUnavailability(String dateTime, String doctorID) {
        DataInitUnavail(UNAVAIL_CSV_FILE);
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
        DataInitUnavail(UNAVAIL_CSV_FILE);
        DataInitStaff(STAFF_CSV_FILE);
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
        DataInitUnavail(UNAVAIL_CSV_FILE);
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
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitPatient(PATIENT_CSV_FILE);
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
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitUnavail(UNAVAIL_CSV_FILE);
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
                        writeToCSV(APPTREQ_CSV_FILE, appointments);
                        writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
                        System.out.println("Appointment Accepted!");
                    } else {
                        writeToCSV(APPTREQ_CSV_FILE, appointments);
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
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitApptOutcome(APPTOUTCOME_CSV_FILE);
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
        appointmentToComplete.appointmentStatus = AppointmentStatus.COMPLETED;

        // Save to CSV or database if needed
        writeToCSV(APPTREQ_CSV_FILE, appointments);  // Updating appointments CSV with "COMPLETED" status
        writeToCSV(APPTOUTCOME_CSV_FILE, appOutcomes);
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
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitPatient(PATIENT_CSV_FILE);
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
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitUnavail(UNAVAIL_CSV_FILE);
        for (Appointment appt : appointments) {
            if (appt.getDoctorID().equals(doctorID) && appt.getAppointmentDateTime().equals(dateTime) && appt.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)){
                appt.setAppointmentStatus(AppointmentStatus.CANCELLED);
                unavailabilities.removeIf(unavail -> unavail.getDoctorID().equals(appt.getDoctorID()) 
                                            && unavail.getDateTime().equals(appt.getAppointmentDateTime()));
                break;
            }
        }
        writeToCSV(UNAVAIL_CSV_FILE, appointments);
        writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
    }

    // Check if slot is available for booking
    public static boolean isSlotAvailable(String dateTime, String doctorID) {
        DataInitUnavail(UNAVAIL_CSV_FILE);
        for (Unavailability unavail : unavailabilities) {
            if (unavail.getDoctorID().equals(doctorID) && unavail.getDateTime().equals(dateTime)) {
                return false;
            }
        }
        return true;
    }

    // Schedule Appointment (Patient)
    public static void scheduleAppointment(String dateTime, String patientID, String doctorName){
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitStaff(STAFF_CSV_FILE);
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
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitUnavail(UNAVAIL_CSV_FILE);
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
            writeToCSV(APPTREQ_CSV_FILE, appointments);
            writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
    
            System.out.println("Appointment rescheduled successfully!");
        } else {
            System.out.println("Doctor's time slot is unavailable!");
        }
    }
    
    // Cancel Appointment (Patient)
    public static void cancelAppointment(String patientID, String dateTime){
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitUnavail(UNAVAIL_CSV_FILE);
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
        writeToCSV(APPTREQ_CSV_FILE, appointments);
        writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);

        System.out.println("Appointment cancelled successfully! The time slot has been made available.");
    }

    // Show appointment status (Patient)
    public static void showPatientAppointment(String patientID){
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitStaff(STAFF_CSV_FILE);
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
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitApptOutcome(APPTOUTCOME_CSV_FILE);
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
                writeToCSV(APPTOUTCOME_CSV_FILE, appOutcomes);
                System.out.println("Medications dispensed!");
                return;
            }
        }
        System.out.println("No medications pending dispensed!");
    }
}