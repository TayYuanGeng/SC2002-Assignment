package models;

public class Docter extends Staff {
    private String medicineName;
    private int stockAmount;
    private int lowLvlStockAmt;
    //private MedicalRecord medicalRecord;

    public Docter(){}

    public Docter(String uID,String name, String pass, String r) {
		super(uID,name,pass,r);
	}


    // Getter, Setters
    public String getMedicineName(){
        return this.medicineName;
    }

    public void setMedicineName(String medicineName){
        this.medicineName = medicineName;
    }

    public int getStockAmt(){
        return this.stockAmount;
    }

    public void setStockAmt(int stockAmount){
        this.stockAmount = stockAmount;
    }

    public int getLowLvlStockAmt(){
        return this.lowLvlStockAmt;
    }

    public void setLowLvlStockAmt(int lowLvlStockAmt){
        this.lowLvlStockAmt = lowLvlStockAmt;
    }

    // Other Methods
    public void displayMenu() {

    }

    public boolean UpdateMedicalRecord() {
        return false;
    }

    public void ViewApplication() {

    }

    public void viewSchedule() {

    }

    public void setAvailability(String date) {

    }

    public void acceptDeclineAppt(String date, boolean response) {

    }

    public boolean cancelAppointment(String date) {
        return false;
    }


}
