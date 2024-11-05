package views;

import java.util.*;

public class Welcome {
    public static void WelcomePage() throws Exception{
        System.out.println("========================================");
        System.out.println("Welcome to Hospital Management System");
        int choice = 0;
        do{
            try {
                System.out.println("========================================");
                System.out.println("(1) Login");
                System.out.println("(2) Exit");
                System.out.println("========================================");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 1) {
                    LoginUI();
                }
                else if (choice == 2){
                    System.out.println("Thank you for using HMS");
                    System.exit(0);
                }
                else{
                    System.out.println("Invalid Input. Please enter an integer (1-3):");
                }
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer: ");
            sc.next();
        }
        }while(true);
    }
}

