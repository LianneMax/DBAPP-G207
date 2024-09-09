package G207DBAPP;
import java.util.Scanner;

public class main_menu {

    public main_menu() {
    }

    public int menu() {
        int menuselection = -1;
        Scanner console = new Scanner(System.in);

        while (menuselection < 0 || menuselection > 8) {
            System.out.println();
            System.out.println("=======================================================");
            System.out.println("                   Application Main Menu               ");
            System.out.println("=======================================================");
            System.out.println("|  [1] Product Management                              |");
            System.out.println("|  [2] Customer Management                             |");
            System.out.println("|  [3] Employee Management                             |");
            System.out.println("|  [4] Office Management                               |");
            System.out.println("|  [5] Order Processing                                |");
            System.out.println("|  [6] Payment Processing                              |");
            System.out.println("|  [7] Report Generation - Sales Report 1              |");
            System.out.println("|  [8] Report Generation - Sales Report 2              |");
            System.out.println("|  [0] Exit Application                                |");
            System.out.println("=======================================================");
            System.out.print("Enter Selected Function: ");

            if (console.hasNextInt()) {
                menuselection = console.nextInt();
                if (menuselection < 0 || menuselection > 8) {
                    System.out.println("Invalid selection. Please enter a number between 0 and 8.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                console.next(); // Consume the invalid input
            }
        }

        return menuselection;
    }

    public static void main(String[] args) {
        main_menu mm = new main_menu();
        int selection;
        do {
            selection = mm.menu();
            switch (selection) {
                case 1:
                    product_management_menu pmm = new product_management_menu();
                    pmm.menu();
                    break;
                    
//                case 2:
//                    customer_management_menu cmm = new customer_management_menu();
//                    cmm.menu();
//                    break;
                    
                case 3:
                	//test
                	break;
                	
                case 5:
                    order_transaction_menu otm = new order_transaction_menu();
                    otm.menu();
                    break;
                    
//                case 6:
//                	payment_transaction_menu ptm = new payment_transaction_menu();
//                	ptm.menu();
//                	break;
                // Implement other cases here if needed
                // case 2:
                //    customer_management_menu cmm = new customer_management_menu();
                //    cmm.menu();
                //    break;
                // Add more cases for each menu option
            }
        } while (selection != 0);

        System.out.println("Exiting application.");
        System.exit(0);
    }
}
