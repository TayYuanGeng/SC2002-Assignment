package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import models.Staff;

public class CSVUtils {
    
    private static void saveUserToCSV(String filePath, String username, String password, String role) {
        String hashedPassword = PasswordUtilsController.hashPassword(password);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(username + "," + hashedPassword + "," + role);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
