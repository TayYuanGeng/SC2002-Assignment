package models;

public class Unavailability {
    private String dateTime;
    private String doctorID;

    public Unavailability(String dateTime, String doctorID) {
        this.dateTime = dateTime;
        this.doctorID = doctorID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }
}