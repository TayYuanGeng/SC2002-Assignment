package models;
/**
 * Doctor class which inherits from Staff class.
 */
public class Doctor extends Staff {
    /**
     * Default constructor
     */
    public Doctor(){}

    /**
     * Constructor
     *
     * @param uID The ID of the doctor.
     * @param name The name of the doctor.
     * @param pass The password for the doctor's account.
     * @param r The role assigned to the doctor.
     */
    public Doctor(String uID,String name, String pass, String r) {
		super(uID,name,pass,r);
	}

    /**
     * Doctor menu.
     */
    public void displayMenu() {
        System.out.println("========================================");
        System.out.println("(1) View Patient Medical Records");
        System.out.println("(2) Update Patient Medical Records");
        System.out.println("(3) View Personal Schedule");
        System.out.println("(4) Set Availability for Appointments");
        System.out.println("(5) Accept or Decline Appointment Requests");
        System.out.println("(6) View Upcoming Appointments");
        System.out.println("(7) Record Appointment Outcome ");
        System.out.println("(8) Logout");

        System.out.println("========================================");
    }
}
