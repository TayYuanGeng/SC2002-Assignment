package models;

import controllers.CSVUtilsController;
import controllers.MainMenuController;
import interfaces.CSVUtilsInterface;
import java.util.ArrayList;

/**
 * Represents a replenishment request for a specific medicine. Each request contains
 * a unique ID, the name of the medicine, and the current status of the request.
 * This class also includes utilities for generating unique request IDs and
 * interacting with replenishment request data stored in CSV files.
 */
public class ReplenishmentRequest {

    /** Utility interface for handling CSV operations */
    static CSVUtilsInterface csvUtils = new CSVUtilsController();

    /** The unique ID of the replenishment request */
    private int reqId;

    /** The name of the medicine associated with the replenishment request */
    private String medicineName;

    /** The current status of the replenishment request */
    private ReplenishmentStatus replenishmentStatus;

    /**
     * Enum representing the possible statuses of a replenishment request.
     */
    public enum ReplenishmentStatus {
        /** Request is pending and awaiting approval */
        PENDING,

        /** Request has been cancelled and will not be fulfilled */
        CANCELLED,

        /** Request has been approved and completed */
        COMPLETED,
    }

    /**
     * Default constructor. Initializes a replenishment request with default values.
     * <p>No unique ID or medicine name is assigned by default.</p>
     */
    public ReplenishmentRequest() {
    }

    /**
     * Parameterized constructor. Creates a replenishment request with a generated unique ID
     * and associates it with the specified medicine name. The status is set to {@code PENDING}.
     *
     * @param medicineName the name of the medicine for the replenishment request
     */
    public ReplenishmentRequest(String medicineName) {
        this.reqId = generateID();
        this.medicineName = medicineName;
        this.replenishmentStatus = ReplenishmentStatus.PENDING;
    }

    /**
     * Parameterized constructor. Creates a replenishment request with a specified unique ID,
     * medicine name, and status.
     *
     * @param uid           the unique ID of the replenishment request
     * @param medicineName  the name of the medicine for the replenishment request
     * @param status        the current status of the replenishment request
     */
    public ReplenishmentRequest(int uid, String medicineName, ReplenishmentStatus status) {
        this.reqId = uid;
        this.medicineName = medicineName;
        this.replenishmentStatus = status;
    }

    /**
     * Gets the unique ID of the replenishment request.
     *
     * @return the unique ID of the request
     */
    public int getRequestID() {
        return this.reqId;
    }

    /**
     * Gets the name of the medicine associated with the replenishment request.
     *
     * @return the name of the medicine
     */
    public String getName() {
        return this.medicineName;
    }

    /**
     * Sets the name of the medicine for the replenishment request.
     *
     * @param medicineName the new name of the medicine
     */
    public void setName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * Gets the current status of the replenishment request.
     *
     * @return the current status of the request
     */
    public ReplenishmentStatus getReplenishmentStatus() {
        return this.replenishmentStatus;
    }

    /**
     * Sets the current status of the replenishment request.
     *
     * @param status the new status of the request
     */
    public void setReplenishmentStatus(ReplenishmentStatus status) {
        this.replenishmentStatus = status;
    }

    /**
     * Generates a unique ID for a new replenishment request by reading the current list
     * of requests from the CSV file and finding the maximum existing ID. The new ID is
     * incremented by 1 from the maximum.
     *
     * @return a unique ID for the new replenishment request
     */
    private static int generateID() {
        ArrayList<ReplenishmentRequest> repReqList = csvUtils.ReadReplenishRequestCSV(MainMenuController.CSV_FILE_PATH + "ReplenishRequest_List.csv");
        int maxid = 0;
        for (ReplenishmentRequest req : repReqList) {
            int uid = req.getRequestID();
            if (uid > maxid) {
                maxid = uid;
            }
        }
        return maxid + 1;
    }
}
