package models;

/**
 * Represents a medicine in the hospital management system.
 * Provides information about the medicine's name, stock levels, 
 * and current availability.
 */
public class Medicine {
    /** The name of the medicine. */
    private String medicineName;
    /** The total amount of the medicine available in stock. */
    private int stockAmount;
    /**
     * The low-level threshold for the medicine stock, 
     * used to trigger restocking alerts.
     */
    private int LowLvlStockAmt;
    /**
     * The current amount of the medicine available.
     */
    private int currentAmount;

    /**
     * Default constructor for the Medicine class.
     */
    public Medicine(){

    }

    /**
     * Constructs a Medicine object with the specified details.
     *
     * @param medicineName  The name of the medicine.
     * @param stockAmount   The total amount of the medicine available in stock.
     * @param LowLvlStockAmt The low-level threshold for the medicine stock.
     * @param currentAmt    The current amount of the medicine available.
     */
    public Medicine(String medicineName, int stockAmount, int LowLvlStockAmt,int currentAmt){
        this.medicineName = medicineName;
        this.stockAmount = stockAmount;
        this.LowLvlStockAmt = LowLvlStockAmt;
        this.currentAmount = currentAmt;
    }

    /**
     * Gets the name of the medicine.
     *
     * @return The name of the medicine.
     */
    public String getName(){
        return this.medicineName;
    }

    /**
     * Sets the name of the medicine.
     *
     * @param medicineName The new name of the medicine.
     */
    public void setName(String medicineName){
        this.medicineName = medicineName;
    }

    /**
     * Gets the total stock amount of the medicine.
     *
     * @return The total stock amount.
     */
    public int getStockAmt(){
        return this.stockAmount;
    }

    /**
     * Sets the total stock amount of the medicine.
     *
     * @param stockAmount The new total stock amount.
     */
    public void setStockAmt(int stockAmount){
        this.stockAmount = stockAmount;
    }

    /**
     * Gets the low-level stock threshold of the medicine.
     *
     * @return The low-level stock threshold.
     */
    public int getLowLvlStockAmt(){
        return this.LowLvlStockAmt;
    }
   
    /**
     * Sets the low-level stock threshold of the medicine.
     *
     * @param LowLvlStockAmt The new low-level stock threshold.
     */
    public void setLowLvlStockAmt(int LowLvlStockAmt){
        this.LowLvlStockAmt = LowLvlStockAmt;
    }
    
    /**
     * Gets the current amount of the medicine available.
     *
     * @return The current amount of the medicine.
     */
    public int getCurrentAmount() {
    	return this.currentAmount;
    }
    
    /**
     * Sets the current amount of the medicine available.
     *
     * @param currentAmt The new current amount of the medicine.
     */
    public void setCurrentAmt(int currentAmt) {
    	this.currentAmount = currentAmt;
    }
}
