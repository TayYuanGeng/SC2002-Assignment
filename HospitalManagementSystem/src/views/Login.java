package views;

import java.util.*;;

public class Login{
    public static void LoginPage() throws Exception{
        String username;
        String password;
        int success;
        int exitChoice=-3;
        do{
            System.out.println("========================================");
            System.out.println("Please enter ID");
            username = sc.nextLine();
            System.out.println("Please enter password");
            password = sc.nextLine();
    
            success = Login(username, password);
            switch (success){
                case 0:
                    System.out.println("Username or password is incorrect");
                    System.out.println("========================================");
                    System.out.println("(1) Try again");
                    System.out.println("(2) Exit");
                    exitChoice = sc.nextInt();
                    sc.nextLine();
                    break;
                case 1:
                    //Administrator
                    System.out.println("Administrator logged in");
                    
                    break;
                case 2:
                    //Patient
                    System.out.println("Patient logged in");
                    break;
                case 3:
                    //Doctor
                    System.out.println("Doctor logged in");
                    break;
                case 4:
                    //Pharmacist
                    System.out.println("Pharmacist logged in");
                    break;
                default:
                    mainMenu.WelcomePage();
                    break;
            }
        }while(exitChoice != 2 && success<5);
        
        if (exitChoice == 2)
            mainMenu.WelcomePage();
    }
}
