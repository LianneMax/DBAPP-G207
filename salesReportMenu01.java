package G207DBAPP;

import java.util.Scanner;

public class salesReportMenu01 {
	
	public salesReportMenu01() {
		
	}
	
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        salesReport01 report = new salesReport01();

        while (true) {
        	System.out.println("===============================================================");
            System.out.println("Sales Report Menu");
            System.out.println("---------------------------------------------------------------");
            System.out.println("[1] Generate Sales Report for a specific month and year");
            System.out.println("[0] Exit Sales Report Menu");
            System.out.println("===============================================================");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter month (1-12): ");
                    int month = scanner.nextInt();
                    System.out.print("Enter year (e.g., 2023): ");
                    int year = scanner.nextInt();
                    report.generateSalesReport(month, year);
                    break;
                case 0:
                    System.out.println("Exiting Sales Report Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
}

