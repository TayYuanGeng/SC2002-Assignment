package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicalRecordRepo {
    private String filepath;

    public MedicalRecordRepo(String filepath)
    {
        this.filepath = filepath;
    }

    public MedicalRecord loadRecord(String patientID)
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


    public void saveContactRecord(MedicalRecord record)
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

    public void updateDiagnosisandTreatment(String patientID, String diagnosis, String treatment)
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
}
