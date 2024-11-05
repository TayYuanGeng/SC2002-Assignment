package controllers;

import java.util.Scanner;

import models.*;

public class AdministratorController {
    static Scanner sc = new Scanner(System.in);

    public static void main(Account loggedInUser) throws Exception {
            // You now have access to the logged-in user here
            System.out.println("Welcome, " + loggedInUser.getName());
            // Additional administrator logic
            AdminPage();
    }

    public static void AdminPage() throws Exception{
        System.out.println("========================================");
        System.out.println("Welcome to Administrator Menu");
        int choice = 0;
        do{
            try {
                System.out.println("========================================");
                System.out.println("(1) Add staff");
                System.out.println("(2) Update staff");
                System.out.println("(3) Remove staff");
                System.out.println("(4) Sign out");
                System.out.println("(5) Exit Application");
                System.out.println("========================================");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        
                        break;
                    case 4:
                        MainMenuController.main(null);
                        break;
                    case 5:
                        System.out.println("Thank you for using HMS");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Input. Please enter an integer (1-5):");
                        break;
                }
            }
            catch (Exception e) {
            System.out.println("Invalid input. Please enter an integer: ");
            sc.next();
        }
        }while(true);
    }
  

}
