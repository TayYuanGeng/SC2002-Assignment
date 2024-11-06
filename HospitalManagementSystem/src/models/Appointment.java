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
    private String appointmentDateTime;
    private static Map<String, String> patientIdToNameMap = new HashMap<>();
    private static Map<String, String> doctorIdToNameMap = new HashMap<>();
    private static List<Appointment> appointments = new ArrayList<Appointment>();
    private static List<Unavailability> unavailabilities = new ArrayList<Unavailability>();
    private static final String STAFF_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/Staff_List.csv";
    private static final String PATIENT_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/Patient_List.csv";
    private static final String UNAVAIL_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/Unavailability.csv";
    private static final String APPTREQ_CSV_FILE = "/Users/yuangeng/Downloads/Y2S1/SC2002 Object Oriented Des & Prog/SC2002-Assignment/HospitalManagementSystem/src/data/ApptRequest.csv";
    public static void main(String[] args) throws Exception {
        DataInitApptReq(APPTREQ_CSV_FILE);
        DataInitUnavail(UNAVAIL_CSV_FILE);
        DataInitPatient(PATIENT_CSV_FILE);
        DataInitStaff(STAFF_CSV_FILE);
        //updateDoctorUnavailability("19-10-2025 23:00", "D002");
        //showDoctorUnavailability();
        //showDoctorSchedule("D001");
        //showAppointmentRequests("D001");
        //respondToRequest("D001", "P1001", "12-01-2025 11:00", true);
        //respondToRequest("D002", "P1003", "12-01-2025 11:00", true);
        //showUpcomingAppointment("D001");
        //scheduleAppointment("23-01-2025 11:00", "P1001", "Emily Clarke");
        rescheduleAppointment("P1001", "12-01-2025 14:00", "13-01-2025 14:00");
        //cancelAppointment("P1001", "12-01-2025 11:00");
		//WelcomePage();
	}

    //[MAPPING TESTER]
    // public static void printHashMapContents() {
    //     System.out.println("Patient ID to Name Map:");
    //     for (Map.Entry<String, String> entry : patientIdToNameMap.entrySet()) {
    //         System.out.println("Patient ID: " + entry.getKey() + ", Patient Name: " + entry.getValue());
    //     }
    //     System.out.println("\nDoctor ID to Name Map:");
    //     for (Map.Entry<String, String> entry : doctorIdToNameMap.entrySet()) {
    //         System.out.println("Doctor ID: " + entry.getKey() + ", Doctor Name: " + entry.getValue());
    //     }
    // }
    
    public static void writeToCSV(String fileName, List<?> dataList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            // Write headers based on whether it's appointments or unavailability
            if (dataList.equals(unavailabilities)){
                bw.write("DateTime,DoctorID");
                for (Object data : dataList) {
                    Unavailability unavailability = (Unavailability) data;
                    bw.write("\n" + unavailability.getDateTime() + "," + unavailability.getDoctorID());
                }
            } else {
                bw.write("DateTime Slot,DoctorID,PatientID,Status");
                for (Object data : dataList) {
                    Appointment appt = (Appointment) data;
                    bw.write("\n" + appt.getAppointmentDateTime() + "," +
                            appt.getDoctorID() + "," +
                            appt.getPatientID() + "," +
                            appt.getAppointmentStatus());
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

    // Doctor accepts or declines a request
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

    // Cancel appointment requests in CSV
    public static void cancelAppointmentRequests(String doctorID, String dateTime) {
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
            writeToCSV(APPTREQ_CSV_FILE, appointments);
            writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);
    
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
        writeToCSV(APPTREQ_CSV_FILE, appointments);
        writeToCSV(UNAVAIL_CSV_FILE, unavailabilities);

        System.out.println("Appointment cancelled successfully! The time slot has been made available.");
    }

    // Getters
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
}