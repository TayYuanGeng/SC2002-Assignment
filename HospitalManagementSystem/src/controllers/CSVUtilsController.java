package controllers;

import interfaces.CSVUtilsInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;
import models.Appointment.AppointmentStatus;
import models.ReplenishmentRequest.ReplenishmentStatus;

/**
 * Controller class implementing CSVUtilsInterface for managing CSV data storage and retrieval
 * for users, staff, patients, and medicines in the Hospital Management System.
 */
public class CSVUtilsController implements CSVUtilsInterface {
    
    /**
     * Saves a user record to a CSV file.
     * 
     * @param filePath The file path of the CSV.
     * @param username The username of the user.
     * @param password The hashed password of the user.
     * @param role     The role of the user (e.g., admin, doctor).
     */
    @Override
    public void saveUserToCSV(String filePath, String username, String password, String role) {
        String hashedPassword = password;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(username + "," + hashedPassword + "," + role);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a staff record to a CSV file.
     * 
     * @param filePath The file path of the CSV.
     * @param staff    The staff object containing staff details.
     */
    @Override
    public void saveStaffToCSV(String filePath, Staff staff) {
        String hashedPassword = staff.getPassword();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(staff.getID() + "," + staff.getName() + "," + hashedPassword + "," + staff.getRole() + "," +
                     staff.getGender() + "," + staff.getAge());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing staff record in a CSV file.
     * 
     * @param filePath     The file path of the CSV.
     * @param updatedStaff The staff object with updated details.
     */
    @Override
    public void updateStaffInCSV(String filePath, Staff updatedStaff) {
        List<String> lines = new ArrayList<>();
        String staffID = updatedStaff.getID();
        String hashedPassword = updatedStaff.getPassword();
        boolean found = false;

        // Read and modify lines
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 0 && fields[0].equals(staffID)) {
                    line = staffID + "," + updatedStaff.getName() + "," + hashedPassword + "," +
                           updatedStaff.getRole() + "," + updatedStaff.getGender() + "," + updatedStaff.getAge();
                    found = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite file if staff found
        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
                System.out.println("Staff record updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Staff with ID " + staffID + " not found.");
        }
    }

    /**
     * Updates an existing patient record in a CSV file.
     * 
     * @param filePath       The file path of the CSV.
     * @param updatedPatient The patient object with updated details.
     */
    @Override
    public void updatePatientInCSV(String filePath, Patient updatedPatient) {
        List<String> lines = new ArrayList<>();
        String patientID = updatedPatient.getID();
        boolean found = false;

        // Read and modify lines
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 0 && fields[0].equals(patientID)) {
                    line = patientID + "," + updatedPatient.getName() + "," + updatedPatient.getDOB() + "," +
                           updatedPatient.getGender() + "," + updatedPatient.getbloodType() + "," +
                           updatedPatient.getEmail() + "," + updatedPatient.getphoneNumber() + "," +
                           fields[7] + "," + updatedPatient.getPassword();
                    found = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite file if patient found
        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
                System.out.println("Patient record updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Patient with ID " + patientID + " not found.");
        }
    }

    /**
     * Initializes staff data from a CSV file.
     * 
     * @param filePath  The file path of the CSV.
     * @param staffList The list to populate with staff data.
     * @return The populated list of staff.
     */
    @Override
    public ArrayList<Staff> StaffDataInit(String filePath, ArrayList<Staff> staffList) {
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(csvSplitBy);
                Staff staff = new Staff(values[0], values[1], values[2], values[3]);
                staff.setGender(values[4]);
                staff.setAge(Integer.parseInt(values[5]));
                staffList.add(staff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    /**
     * Initializes medicine data from a CSV file.
     * 
     * @param filePath The file path of the CSV.
     * @return The populated list of medicines.
     */
    @Override
    public ArrayList<Medicine> MedicineDataInit(String filePath) {
        ArrayList<Medicine> medicineList = new ArrayList<>();
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(csvSplitBy);
                Medicine medicine = new Medicine(values[0], Integer.parseInt(values[1]),
                                                  Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                medicineList.add(medicine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medicineList;
    }


    /**
     * Initializes a list of {@link Patient} objects by reading data from a CSV file.
     * The method processes each line, creates a new {@link Patient} for each entry,
     * and sets additional properties like date of birth, gender, blood type, email, and phone number.
     * 
     * @param filePath the path to the CSV file containing patient data.
     * @param patientList the list to which the new {@link Patient} objects will be added.
     * @return the updated {@link ArrayList} of {@link Patient} objects.
     */
    @Override
    public ArrayList<Patient> PatientDataInit(String filePath, ArrayList<Patient> patientList){
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
                for (String value : values) {
                    System.out.print(value + " ");
                }
                MedicalRecordRepo recordRepo = new MedicalRecordRepo(filePath);
                MedicalRecordService medicalRR = new MedicalRecordService(recordRepo);
                patientList.add(new Patient(values[0], values[8],values[1],"Patient", medicalRR));
                patientList.get(patientList.size()-1).setDOB(values[2]);
                patientList.get(patientList.size()-1).setGender(values[3]);
                patientList.get(patientList.size()-1).setbloodType(values[4]);
                patientList.get(patientList.size()-1).setEmail(values[5]);
                patientList.get(patientList.size()-1).setphoneNumber(Integer.parseInt(values[6]));

                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return patientList;
    }


    /**
     * Removes a staff member from the CSV file based on their staff ID.
     * The method reads the CSV file, searches for the staff member by ID,
     * and removes their record from the file if found.
     * 
     * @param filePath the path to the CSV file containing staff data.
     * @param removeStaff the {@link Staff} object representing the staff member to remove.
     */
    @Override
    public void removeStaffInCSV(String filePath,Staff removeStaff) {
	List<String> lines = new ArrayList<>();
    String staffID = removeStaff.getID();
    boolean found = false;

    // Read all lines and modify the matching line
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            
            if (fields.length > 0 && fields[0].equals(staffID)) { // Check if the ID matches
                // Replace with updated staff information
            	System.out.println("Staff to remove Found");
                found = true;
            }
            else {
            	lines.add(line); // Add each line (modified or not) to the list
            }
        }
        }catch (IOException e) {
            e.printStackTrace();
        }
        // Rewrite the file with updated lines
        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
                System.out.println("Staff record removed successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Staff with ID " + staffID + " not found.");
    }
}
    

    /**
     * Saves a {@link Medicine} object to the CSV file by appending its details (name, stock amount, low stock level, current amount).
     * 
     * @param filePath the path to the CSV file where the medicine record will be saved.
     * @param med the {@link Medicine} object to be saved.
     */
    @Override
    public void saveMedToCSV(String filePath, Medicine med) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(med.getName() + "," + med.getStockAmt() + "," + med.getLowLvlStockAmt() + "," + med.getCurrentAmount());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    

    /**
     * Removes a {@link Medicine} record from the CSV file based on the medicine's name.
     * The method reads the CSV file, searches for the medicine by name, and removes its record if found.
     * 
     * @param filePath the path to the CSV file containing medicine data.
     * @param removeMedicine the {@link Medicine} object representing the medicine to remove.
     */
    @Override
    public void removeMedInCSV(String filePath,Medicine removeMedicine) {
    	List<String> lines = new ArrayList<>();
        String medName = removeMedicine.getName();
        boolean found = false;

        // Read all lines and modify the matching line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length > 0 && fields[0].equals(medName)) { // Check if the ID matches
                    // Replace with updated staff information
                    found = true;
                }
                else {
                	lines.add(line); // Add each line (modified or not) to the list
                }
            
            } 
        }catch (IOException e) {
                e.printStackTrace();
            }
        // Rewrite the file with updated lines
        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
                System.out.println("Medicine record removed successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Medicine with Name " + medName + " not found.");
        }
            
        }
    	
    /**
     * Updates an existing {@link Medicine} record in the CSV file with new details.
     * The method searches for the medicine by name and updates its stock amounts if found.
     * 
     * @param filePath the path to the CSV file containing the medicine data.
     * @param updatedMedicine the {@link Medicine} object with updated details.
     */
    @Override
    public void updateMedInCSV(String filePath, Medicine updatedMedicine) {
        List<String> lines = new ArrayList<>();
        String medName = updatedMedicine.getName();

        boolean found = false;

        // Read all lines and modify the matching line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length > 0 && fields[0].equals(medName)) { // Check if the ID matches
                    // Replace with updated staff information
                    line = medName + "," + updatedMedicine.getStockAmt() + "," + updatedMedicine.getLowLvlStockAmt()+","+ updatedMedicine.getCurrentAmount();
                    found = true;
                }
                
                lines.add(line); // Add each line (modified or not) to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite the file with updated lines
        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
                System.out.println("Medicine record updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Medicine with Name " + medName + " not found.");
        }
    }
    
    
    /**
     * Reads replenishment requests from a CSV file and returns a list of requests.
     *
     * @param filePath the path to the CSV file.
     * @return a list of {@link ReplenishmentRequest} objects read from the file, or {@code null} if an error occurs.
     */
    @Override
    public ArrayList<ReplenishmentRequest> ReadReplenishRequestCSV(String filePath){
        ArrayList<ReplenishmentRequest> repReqList = new ArrayList<ReplenishmentRequest>();
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
        		repReqList.add(new ReplenishmentRequest(Integer.valueOf(values[0]), values[1],ReplenishmentStatus.valueOf(values[2])));
                }
                
                System.out.println();
                return repReqList;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Saves a replenishment request to a CSV file by appending it.
     *
     * @param filePath the path to the CSV file.
     * @param request the {@link ReplenishmentRequest} to save.
     * @return {@code true} if the operation is successful, {@code false} otherwise.
     */
    @Override
    public boolean saveReplenishReqToCSV(String filePath, ReplenishmentRequest request) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(request.getRequestID()+","+ request.getName() + "," + request.getReplenishmentStatus());
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    

    /**
     * Updates an existing replenishment request in the CSV file.
     *
     * @param filePath the path to the CSV file.
     * @param request the updated {@link ReplenishmentRequest}.
     */
    @Override
    public void updateRepReqInCSV(String filePath, ReplenishmentRequest request) {
        List<String> lines = new ArrayList<>();
        String medName = request.getName();

        boolean found = false; 

        // Read all lines and modify the matching line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length > 0 && fields[1].equals(medName)) { // Check if the ID matches
                    // Replace with updated staff information
                    line = request.getRequestID() + "," + medName + "," + request.getReplenishmentStatus();
                    found = true;
                }
                
                lines.add(line); // Add each line (modified or not) to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite the file with updated lines
        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
                System.out.println("Replenishment Request updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
        else {
            System.out.println("Replenishment Request for medicine " + medName + " not found.");
        }
    }

    /**
     * Initializes appointment requests from a CSV file.
     *
     * @param filePath the path to the CSV file.
     * @return a list of {@link Appointment} objects.
     */
    @Override
    public List<Appointment> DataInitApptReq(String filePath) {
        List<Appointment> appointments = new ArrayList<>();
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(csvSplitBy);
                appointments.add(new Appointment(values[0], values[1], values[2], AppointmentStatus.valueOf(values[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointments;
    }


    /**
     * Initializes unavailability schedules from a CSV file.
     *
     * @param filePath the path to the CSV file.
     * @return a list of {@link Unavailability} objects.
     */
    @Override
    public List<Unavailability> DataInitUnavail(String filePath) {
        List<Unavailability> unavailabilities = new ArrayList<>();
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(csvSplitBy);
                unavailabilities.add(new Unavailability(values[0], values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return unavailabilities;
    }

    /**
     * Initializes patient data from a CSV file into a map.
     *
     * @param filePath the path to the CSV file.
     * @return a map of patient IDs to names.
     */
    @Override
    public Map<String, String> DataInitPatient(String filePath) {
        Map<String, String> patientIdToNameMap = new HashMap<>();
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(csvSplitBy);
                patientIdToNameMap.put(values[0], values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patientIdToNameMap;
    }

  
    /**
     * Initializes staff data (doctors) from a CSV file into a map.
     *
     * @param filePath the path to the CSV file.
     * @return a map of doctor IDs to names.
     */
    @Override
    public Map<String, String> DataInitStaff(String filePath) {
        Map<String, String> doctorIdToNameMap = new HashMap<>();
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(csvSplitBy);
                if (values[3].equals("Doctor")) {
                    doctorIdToNameMap.put(values[0], values[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctorIdToNameMap;
    }

    
    /**
     * Initializes appointment outcomes from a CSV file.
     *
     * @param filePath the path to the CSV file.
     * @return a list of {@link AppOutcome} objects.
     */
    @Override
    public List<AppOutcome> DataInitApptOutcome(String filePath) {
        List<AppOutcome> appOutcomes = new ArrayList<>();
        String line;
        String csvSplitBy = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(csvSplitBy);
                String dateTime = values[0];
                String patientID = values[1];
                String serviceType = values[2];
                String medications = values[3];
                String notes = values[4];
                String status = values[5];

                List<String> medicationsList = new ArrayList<>(Arrays.asList(medications.split(" ")));
                appOutcomes.add(new AppOutcome(dateTime, patientID, serviceType, medicationsList, notes, status));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appOutcomes;
    }


    /**
     * Writes data to a CSV file based on the type of the provided list.
     *
     * @param fileName the path to the CSV file.
     * @param dataList the list of data to write, which can contain {@link Unavailability}, {@link Appointment}, or {@link AppOutcome}.
     */
    @Override
    public void writeToCSV(String fileName, List<?> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            System.out.println("No data to write to CSV.");
            return;
        }
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            // Check the type of the first element
            Object firstItem = dataList.get(0);
    
            if (firstItem instanceof Unavailability) {
                // Write header
                bw.write("DateTime,DoctorID");
                bw.newLine();
    
                // Write data without trailing newline
                for (int i = 0; i < dataList.size(); i++) {
                    Unavailability unavailability = (Unavailability) dataList.get(i);
                    bw.write(unavailability.getDateTime() + "," + unavailability.getDoctorID());
                    if (i < dataList.size() - 1) {
                        bw.newLine();
                    }
                }
            } else if (firstItem instanceof Appointment) {
                // Write header
                bw.write("DateTime Slot,DoctorID,PatientID,Status");
                bw.newLine();
    
                // Write data without trailing newline
                for (int i = 0; i < dataList.size(); i++) {
                    Appointment appt = (Appointment) dataList.get(i);
                    bw.write(appt.getAppointmentDateTime() + "," +
                            appt.getDoctorID() + "," +
                            appt.getPatientID() + "," +
                            appt.getAppointmentStatus());
                    if (i < dataList.size() - 1) {
                        bw.newLine();
                    }
                }
            } else if (firstItem instanceof AppOutcome) {
                // Write header
                bw.write("DateTime,PatientID,ServiceType,PrescribedMedications,ConsultationNotes,PrescriptionStatus");
                bw.newLine();
    
                // Write data without trailing newline
                for (int i = 0; i < dataList.size(); i++) {
                    AppOutcome apptOut = (AppOutcome) dataList.get(i);
                    String medications = String.join(" ", apptOut.getPrescribedMedications());
                    bw.write(apptOut.getDateTime() + "," +
                            apptOut.getPatientID() + "," +
                            apptOut.getServiceType() + "," +
                            medications + "," +
                            apptOut.getConsultationNotes() + "," +
                            apptOut.getPrescriptionStatus());
                    if (i < dataList.size() - 1) {
                        bw.newLine();
                    }
                }
            } else {
                System.out.println("Unsupported data type for CSV writing.");
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }    


    /**
     * Retrieves a {@link MedicalRecord} for a given patient ID from a CSV file.
     * 
     * @param filepath the path to the CSV file.
     * @param patientID the ID of the patient whose medical record is to be fetched.
     * @return the {@link MedicalRecord} corresponding to the given patient ID, or {@code null} if the record is not found or an error occurs.
     */
    @Override
    public MedicalRecord getMedicalRecord(String filepath, String patientID)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) 
        {
            String line;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");

                if (columns[0].equals(patientID))
                {
                    HashMap<String, String> dntMap = new HashMap<>();
                    // split into each diagnosis and treatment pair
                    String[] diagnosisAndTreatments = columns[7].split("\\|");
                    for (String pair : diagnosisAndTreatments) 
                    {
                        String[] parts = pair.split(":");
                        if (parts.length == 2) 
                        {
                            dntMap.put(parts[0],parts[1]);
                        }
                    }
                    return new MedicalRecord(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5], columns[6], dntMap);
                }
                
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Saves or updates the contact information (email and phone) for a given patient in a CSV file.
     * 
     * @param filepath the path to the CSV file.
     * @param record the {@link MedicalRecord} object containing the updated contact details.
     */
    @Override
    public void saveContactRecordCSV(String filepath, MedicalRecord record)
    {
        List<String> updatedLines = new ArrayList<>();  
        boolean updated = false;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) 
        {
            String line;

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                

                if (columns[0].equals(record.getPatientId()))
                {
                    columns[5] = record.getEmail();
                    columns[6] = record.getPhone();
                    updated = true;
                }
                
                updatedLines.add(String.join(",", columns));
            }

            if (updated) 
            {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) 
                {
                    for (String updatedLine : updatedLines) 
                    {
                        bw.write(updatedLine);
                        bw.newLine();
                    }

                }
            } 

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }


    /**
     * Updates the diagnosis and treatment information for a given patient in the CSV file.
     * 
     * @param filepath the path to the CSV file.
     * @param patientID the ID of the patient whose diagnosis and treatment information is to be updated.
     * @param diagnosis the diagnosis to add or update.
     * @param treatment the treatment corresponding to the diagnosis.
     */
    @Override
    public void updateDiagnosisandTreatmentCSV(String filepath, String patientID, String diagnosis, String treatment)
    {
        List<String> updatedLines = new ArrayList<>();  
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) 
        {
            String line;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                
                if (columns[0].equals(patientID)) 
                {
                    // read from csv split into key value pair
                    String[] diagnosesAndTreatments = columns[7].split("\\|");
                    Map<String, String> diagnosisMap = new HashMap<>();
                    
                    for (String diagnosisTreatment : diagnosesAndTreatments)
                    {
                        String[] parts = diagnosisTreatment.split(":");
                        if (parts.length == 2) 
                        {
                            diagnosisMap.put(parts[0], parts[1]);
                        }
                    }
                
                    diagnosisMap.put(diagnosis,treatment);
                    List<String> new_entry = new ArrayList<>();
                    for(Map.Entry<String,String> pair : diagnosisMap.entrySet())
                    {
                        new_entry.add(pair.getKey() + ":" + pair.getValue());
                    }
                    columns[7] = String.join("|", new_entry);
                    updated = true;
                }
                updatedLines.add(String.join(",", columns));
            }
            if (updated) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
                    for (String updatedLine : updatedLines) {
                        bw.write(updatedLine);
                        bw.newLine();
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Checks whether a given patient has an appointment with a specific doctor, by reading from the CSV file.
     * 
     * @param filePath the path to the CSV file containing the appointment records.
     * @param patientID the ID of the patient whose appointment is to be checked.
     * @param doctorID the ID of the doctor to check against the patient's appointments.
     * @return {@code true} if the patient has an appointment with the specified doctor, {@code false} otherwise.
     */
    @Override
    public Boolean CheckPatientApptDoctorCSV(String filePath, String patientID, String doctorID) { 
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

                if (doctorID.equals(values[1]) && patientID.equals(values[2])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
