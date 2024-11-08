package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.*;

public class CSVUtils {
    
    public static void saveUserToCSV(String filePath, String username, String password, String role) {
        String hashedPassword = password;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(username + "," + hashedPassword + "," + role);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void saveStaffToCSV(String filePath, Staff staff) {
        String hashedPassword = staff.getPassword();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(staff.getID() + "," + staff.getName() + "," + hashedPassword + "," + staff.getRole()+ "," + staff.getGender()+ "," + staff.getAge());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void updateStaffInCSV(String filePath, Staff updatedStaff) {
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

    // public static void updatePatientInCSV(String filePath, Patient updatedPatient) {
    //     List<String> lines = new ArrayList<>();
    //     String staffID = updatedPatient.getID();
    //     String hashedPassword = updatedPatient.getPassword();
    //     boolean found = false;

    //     // Read all lines and modify the matching line
    //     try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    //         String line;
    //         while ((line = br.readLine()) != null) {
    //             String[] fields = line.split(",");
                
    //             if (fields.length > 0 && fields[0].equals(staffID)) { // Check if the ID matches
    //                 // Replace with updated staff information
    //                 line = staffID + "," + updatedPatient.getName() + "," + updatedPatient.getDOB() + "," +
    //                 found = true;
    //             }
                
    //             lines.add(line); // Add each line (modified or not) to the list
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }

    //     // Rewrite the file with updated lines
    //     if (found) {
    //         try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
    //             for (String l : lines) {
    //                 bw.write(l);
    //                 bw.newLine();
    //             }
    //             System.out.println("Patient record updated successfully.");
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //     } else {
    //         System.out.println("Patient with ID " + staffID + " not found.");
    //     }
    // }

    public static ArrayList<Staff> StaffDataInit(String filePath, ArrayList<Staff> staffList){
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
                staffList.get(staffList.size()-1).setAge(Integer.valueOf(values[5]));
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public static ArrayList<Patient> PatientDataInit(String filePath, ArrayList<Patient> patientList){
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
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patientList;
    }


    // private static void DataInit(String filePath){
    //     String line;
    //     String csvSplitBy = ",";
    //     boolean isFirstLine = true;

        
    //     try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    //         while ((line = br.readLine()) != null) {
    //         	if(isFirstLine) {
    //         		isFirstLine = false;
    //         		continue;
    //         	}
    //             // Use comma as separator
    //             String[] values = line.split(csvSplitBy);
                
    //             // Example: Print the values
    //             for (String value : values) {
    //                 System.out.print(value + " ");
    //             }
    //             staffList.add(new Staff(values[0], values[1],"Password",values[2]));
                
    //             System.out.println();
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}
