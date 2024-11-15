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


public class CSVUtilsController implements CSVUtilsInterface {
    
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

    @Override
    public void saveStaffToCSV(String filePath, Staff staff) {
        String hashedPassword = staff.getPassword();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(staff.getID() + "," + staff.getName() + "," + hashedPassword + "," + staff.getRole()+ "," + staff.getGender()+ "," + staff.getAge());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void updateStaffInCSV(String filePath, Staff updatedStaff) {
        List<String> lines = new ArrayList<>();
        String staffID = updatedStaff.getID();
        String hashedPassword = updatedStaff.getPassword();

        boolean found = false;

        // Read all lines and modify the matching line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length > 0 && fields[0].equals(staffID)) { // Check if the ID matches
                    // Replace with updated staff information
                    line = staffID + "," + updatedStaff.getName() + "," + hashedPassword + "," +
                           updatedStaff.getRole() + "," + updatedStaff.getGender() + "," + updatedStaff.getAge();
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
                System.out.println("Staff record updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Staff with ID " + staffID + " not found.");
        }
    }

    @Override
    public void updatePatientInCSV(String filePath, Patient updatedPatient) {
        List<String> lines = new ArrayList<>();
        String patientID = updatedPatient.getID();
        boolean found = false;
        // Read all lines and modify the matching line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length > 0 && fields[0].equals(patientID)) { // Check if the ID matches
                    line = patientID + "," + updatedPatient.getName() + "," + updatedPatient.getDOB() + "," + updatedPatient.getGender() 
                    + "," + updatedPatient.getbloodType() + "," + updatedPatient.getEmail() + "," 
                    + updatedPatient.getphoneNumber() + "," + fields[7] + "," + updatedPatient.getPassword();
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
                System.out.println("Patient record updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Patient with ID " + patientID + " not found.");
        }
    }

    @Override
    public ArrayList<Staff> StaffDataInit(String filePath, ArrayList<Staff> staffList){
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
                staffList.add(new Staff(values[0], values[1],values[2],values[3]));
                staffList.get(staffList.size()-1).setGender(values[4]);
                staffList.get(staffList.size()-1).setAge(Integer.parseInt(values[5]));
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    @Override
    public ArrayList<Medicine> MedicineDataInit(String filePath) { 
        ArrayList<Medicine> medicineList = new ArrayList<Medicine>();
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
                Medicine m = new Medicine(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                medicineList.add(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medicineList;
    }


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
    
    @Override
    public void saveMedToCSV(String filePath, Medicine med) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(med.getName() + "," + med.getStockAmt() + "," + med.getLowLvlStockAmt() + "," + med.getCurrentAmount());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
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

    // Initialize Appointment Request
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

    // Initialize Unavailability Schedule
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

    // Initialize Patient Data (patientId to name map)
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

    // Initialize Staff Data (doctorId to name map)
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

    // Initialize Appointment Outcomes
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

    // Save data to CSV (For Unavailability.csv, Appointment.csv, ApptOutcome.csv)
    @Override
    public void writeToCSV(String fileName, List<?> dataList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            if (dataList instanceof List<?>) {
                if (dataList.get(0) instanceof Unavailability) {
                    bw.write("DateTime,DoctorID");
                    for (Object data : dataList) {
                        Unavailability unavailability = (Unavailability) data;
                        bw.write("\n" + unavailability.getDateTime() + "," + unavailability.getDoctorID());
                    }
                } else if (dataList.get(0) instanceof Appointment) {
                    bw.write("DateTime Slot,DoctorID,PatientID,Status");
                    for (Object data : dataList) {
                        Appointment appt = (Appointment) data;
                        bw.write("\n" + appt.getAppointmentDateTime() + "," +
                                appt.getDoctorID() + "," +
                                appt.getPatientID() + "," +
                                appt.getAppointmentStatus());
                    }
                } else if (dataList.get(0) instanceof AppOutcome) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to CSV file.");
        }
    }

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

    @Override
    public Boolean CheckPatientApptDoctorCSV(String filePath, String patientID, String doctorID) { 
        ArrayList<Medicine> medicineList = new ArrayList<Medicine>();
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
                if (doctorID == values[1] && values[2] == patientID) {
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
