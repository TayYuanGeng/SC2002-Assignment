package models;

public class Medicine {
    private String medicineName;
    private int stockAmount;
    private int LowLvlStockAmt;

    public Medicine(){

    }

    public Medicine(String medicineName, int stockAmount, int LowLvlStockAmt){
        this.medicineName = medicineName;
        this.stockAmount = stockAmount;
        this.LowLvlStockAmt = LowLvlStockAmt;
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

    public void setName(int LowLvlStockAmt){
        this.LowLvlStockAmt = LowLvlStockAmt;
    }
}
