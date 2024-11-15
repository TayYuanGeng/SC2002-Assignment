package models;

import controllers.CSVUtilsController;
import controllers.MainMenuController;
import interfaces.CSVUtilsInterface;
import java.util.ArrayList;

public class ReplenishmentRequest {
    static CSVUtilsInterface csvUtils = new CSVUtilsController();
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
    	ArrayList<ReplenishmentRequest> repReqList = csvUtils.ReadReplenishRequestCSV(MainMenuController.CSV_FILE_PATH + "ReplenishRequest_List.csv");
		int maxid = 0;
		for(ReplenishmentRequest req : repReqList) {
			int uid = Integer.valueOf(req.getRequestID());
			if(uid>maxid) {
				maxid = uid;
			}
		}
		return maxid+1;
	}
}

