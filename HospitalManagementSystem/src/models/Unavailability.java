package models;

/**
 * Represents a doctor's unavailability for appointments.
 */
public class Unavailability {
    
    /**
     * The date and time of the unavailability.
     */
    private String dateTime;

    /**
     * The unique identifier of the doctor who is unavailable.
     */
    private String doctorID;

    /**
     * Constructs an Unavailability object.
     *
     * @param dateTime The date and time of the unavailability.
     * @param doctorID The ID of the doctor who is unavailable.
     */
    public Unavailability(String dateTime, String doctorID) {
        this.dateTime = dateTime;
        this.doctorID = doctorID;
    }

    /**
     * Gets the date and time of the unavailability.
     *
     * @return The date and time as a string.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Gets the doctor ID.
     *
     * @return The ID of the doctor.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the date and time of the unavailability.
     *
     * @param dateTime The new date and time of the unavailability.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Sets the doctor ID.
     *
     * @param doctorID The new ID of the doctor.
     */
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }
}