package models;

public class Medicine {
    private String medicineName;
    private int stockAmount;
    private int LowLvlStockAmt;
    private int currentAmount;

    public Medicine(){

    }

    public Medicine(String medicineName, int stockAmount, int LowLvlStockAmt,int currentAmt){
        this.medicineName = medicineName;
        this.stockAmount = stockAmount;
        this.LowLvlStockAmt = LowLvlStockAmt;
        this.currentAmount = currentAmt;
    }

    public String getName(){
        return this.medicineName;
    }

    public void setName(String medicineName){
        this.medicineName = medicineName;
    }

    public int getStockAmt(){
        return this.stockAmount;
    }

    public void setStockAmt(int stockAmount){
        this.stockAmount = stockAmount;
    }

    public int getLowLvlStockAmt(){
        return this.LowLvlStockAmt;
    }
   

    public void setLowLvlStockAmt(int LowLvlStockAmt){
        this.LowLvlStockAmt = LowLvlStockAmt;
    }
    
    public int getCurrentAmount() {
    	return this.currentAmount;
    }
    
    public void setCurrentAmt(int currentAmt) {
    	this.currentAmount = currentAmt;
    }
}
