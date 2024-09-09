package G207DBAPP;

import java.util.Scanner;
import java.sql.Date;

public class payment_transaction_menu {

    public payment_transaction_menu() {
    }

    public int menu() {
        int menuSelection = 0;
        Scanner console = new Scanner(System.in);
		payment_transaction pt = new payment_transaction();
        

        System.out.println("===============================================================");
        System.out.println("Payment Transaction Menu");
        System.out.println("---------------------------------------------------------------");

        System.out.println("[1] Create a new Payment");
        System.out.println("[2] Update a Payment");
        System.out.println("[3] Delete a Payment");
        System.out.println("[0] Exit Payment Management");
        System.out.println("===============================================================");

        menuSelection = pt.getValidInt(console, "Enter selected function: ");

        if (menuSelection == 1) {
            payment_transaction p = new payment_transaction();

            // Gets the input for a New Payment
            System.out.println("Enter Payment Information:");
            p.customerNumber = pt.getValidInt(console, "CustomerNumber ");
            p.checkNumber = pt.getValidString(console, "Check Number: ");
            p.paymentDate = pt.getValidDate(console, "Payment Date (YYYY-MM-DD): ");
            p.amount = pt.getValidDouble(console, "Amount: ");
            
            // Adds the New Payment
            p.addPayment();

        } else if (menuSelection == 2) {
        	payment_transaction p = new payment_transaction();

            // Update an Existing Payment
            System.out.println("Enter Payment Info to Update:");
            p.customerNumber = pt.getValidInt(console, "CustomerNumber ");
            p.checkNumber = pt.getValidString(console, "Check Number: ");

            int result = p.getPayment();  // This method should retrieve and print the current payment details
            System.out.println("---------------------------------------------------------------");

            if (result == 1) {
                // Get new payment information
                System.out.println("Enter New Payment Information:");
                p.paymentDate = pt.getValidDate(console, "Payment Date (YYYY-MM-DD): ");
                p.amount = pt.getValidDouble(console, "Amount: ");

                p.updatePayment();
            } else {
                System.out.println("No payment record found for the given customer number and check number.");
            }

        } else if (menuSelection == 3) {
            payment_transaction p = new payment_transaction();

            // Delete a Payment
            System.out.println("Enter payment information to delete:");
            p.customerNumber = pt.getValidInt(console, "CustomerNumber ");
            p.checkNumber = pt.getValidString(console, "Check Number: ");

            p.deletePayment();

        } else if (menuSelection == 0) {
        
        }
        return menuSelection;
    }
}