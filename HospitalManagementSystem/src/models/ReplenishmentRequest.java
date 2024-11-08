package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReplenishmentRequest {
	static ArrayList<ReplenishmentRequest> repReqList = new ArrayList<ReplenishmentRequest>();
	private int reqId;
	private String medicineName;
	private ReplenishmentStatus replenishmentStatus;
    

    public enum ReplenishmentStatus {
        PENDING,
        CANCELLED,
        COMPLETED,
    }
    
    public ReplenishmentRequest(){

    }

    public ReplenishmentRequest(String medicineName){
    	this.reqId = generateID();
        this.medicineName = medicineName;
        this.replenishmentStatus = ReplenishmentStatus.PENDING ;
    }
    
    public ReplenishmentRequest(int uid,String medicineName,ReplenishmentStatus status){
    	this.reqId = uid;
        this.medicineName = medicineName;
        this.replenishmentStatus = status;
    }

    public int getRequestID() {
    	return this.reqId;
    }
    
    
    public String getName(){
        return this.medicineName;
    }

    public void setName(String medicineName){
        this.medicineName = medicineName;
    }

    public ReplenishmentStatus getReplenishmentStatus(){
        return this.replenishmentStatus;
    }
  
    public void setReplenishmentStatus(ReplenishmentStatus status){
    	this.replenishmentStatus = status;
    }
    
    private static int generateID() {
    	readRepReqData("src\\data\\ReplenishRequest_List.csv");
		int maxid = 0;
		for(ReplenishmentRequest req : repReqList) {
			int uid = Integer.valueOf(req.getRequestID());
			System.out.println(uid);
			if(uid>maxid) {
				maxid = uid;
			}
		}
		return maxid+1;
	
	}
    
    private static void readRepReqData(String filePath){
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
        		repReqList.add(new ReplenishmentRequest(Integer.valueOf(values[0]), values[1],ReplenishmentStatus.valueOf(values[3])));

                }
                
                System.out.println();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

