package G207DBAPP;

import java.util.Scanner;

public class Salesreport02_menu {

    public Salesreport02_menu() {
    }

    public int menu() {
        Scanner console = new Scanner(System.in);
        int month = 0;
        int year = 0;
        int menuselection = -1;

        System.out.println("===============================================================");
        System.out.println("Sales Report Generation Menu");
        System.out.println("---------------------------------------------------------------");
        System.out.println("[1] Generate Sales Report");
        System.out.println("[0] Exit");
        System.out.println("===============================================================");

         do {
            System.out.println("Enter Selected Function: ");
            menuselection = Integer.parseInt(console.nextLine());

            if (menuselection == 1) {
                System.out.println("Enter Month (1-12): ");
                month = Integer.parseInt(console.nextLine());
                System.out.println("Enter Year (YYYY): ");
                year = Integer.parseInt(console.nextLine());

                if (month >= 1 && month <= 12 && year > 0) {
                    Salesreport02 report = new Salesreport02();
                    report.setDate(month, year);
                    report.generateReport();
                } else if ( (month < 1 && month > 12 && year < 0)){
                    System.out.println("Invalid month or year entered.");
                }

            }  else if(menuselection < 0 || (menuselection > 1)) {
                System.out.println("Invalid selection. Please try again.");
            }
        }while (menuselection != 0);
        if (menuselection == 0) 
            System.out.println("Exiting...");
        
        return 0;
    }
}