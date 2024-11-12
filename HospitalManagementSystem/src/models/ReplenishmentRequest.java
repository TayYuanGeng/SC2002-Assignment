package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import controllers.CSVUtils;

public class ReplenishmentRequest {
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
    	ArrayList<ReplenishmentRequest> repReqList = CSVUtils.ReadReplenishRequestCSV("src\\data\\ReplenishRequest_List.csv");
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
}

