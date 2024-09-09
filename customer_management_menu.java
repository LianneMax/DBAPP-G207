package G207DBAPP;
import java.io.*;
import java.util.Scanner;

public class customer_management_menu {

	public customer_management_menu() {
		
	}
	
	// Algorithm for Menu Processing
	// 1. Display the menu
	// 2. Ask the user for function selection
	// 3. Based on the function selection, ask for input
	// 4. Perform the function
	
	public int menu() {
		int menuSelection = 0;
		Scanner console = new Scanner(System.in);
		customer_management cm = new customer_management();
		
		//Display the menu
		System.out.println("===============================================================");
		System.out.println("Customer Management Menu");
		System.out.println("---------------------------------------------------------------");
		
		System.out.println("[1] Create a new Customer");
		System.out.println("[2] Update a Customer Record");
		System.out.println("[3] Delete a Customer Record");
		System.out.println("[4] Ban a Customer");
		System.out.println("[5] View a Customer and their Order Records");
		System.out.println("[6] View a Customer");
		System.out.println("[0] Exit Customer Management");
		System.out.println("===============================================================");
		
		// Ask the user for function selection
        menuSelection = cm.getValidInt(console, "Enter selected function: ");
		
		if (menuSelection == 1) {
			//Creating a new Customer record
			customer_management c = new customer_management();
			
			c.customerNumber = cm.getValidInt(console, "Customer Number: ");
            c.customerName = cm.getValidString(console, "Customer Name: ");
            c.contactLastName = cm.getValidString(console, "Contact Last Name: ");
            c.contactFirstName = cm.getValidString(console, "Contact First Name: ");
            c.phone = cm.getValidString(console, "Phone: ");
            c.addressLine1 = cm.getValidString(console, "Address Line 1: ");
            c.addressLine2 = cm.getValidString(console, "Address Line 2: ");
            c.city = cm.getValidString(console, "City: ");
            c.state = cm.getValidString(console, "State: ");
            c.postalCode = cm.getValidString(console, "Postal Code: ");
            c.country = cm.getValidString(console, "Country: ");
            c.salesRepEmployeeNumber = cm.getValidInt(console, "Sales Rep Employee Number: ");
            c.creditLimit = cm.getValidInt(console, "Credit Limit: ");
			
			c.addCustomer();
			
		} else if (menuSelection == 2) {
			//Updating a Customer record
			customer_management c = new customer_management();
			
			// Ask the user for the record to be updated
			// Validates the user input
			System.out.println("Enter Customer Information:");
			c.customerNumber = cm.getValidInt(console, "Customer Number: ");
			
			// Loads customer details
			c.getCustomer();
			
			if (customer_management.isCustomerBanned(c.customerNumber)) {
	            System.out.println("Cannot update. The customer is banned.");
	            
	        } else {
				// Presents the old values	        	
	        	System.out.println("Current Customer Information:");
				System.out.println("---------------------------------------------------------------");
				System.out.println("Customer Number: "					+ c.customerNumber);
				System.out.println("Customer Name: "					+ c.customerName);
				System.out.println("Contact Last Name: "				+ c.contactLastName);
				System.out.println("Contact First Name: "				+ c.contactFirstName);
				System.out.println("Phone: "							+ c.phone);	
				System.out.println("Address Line 1: " 					+ c.addressLine1);		
				System.out.println("Address Line 2: "					+ c.addressLine2);	
				System.out.println("City: "								+ c.city);				
				System.out.println("State: "							+ c.state);				
				System.out.println("Postal Code: "						+ c.postalCode);		
				System.out.println("Country: "							+ c.country);	
				System.out.println("Sales Rep Employee Number: "		+ c.salesRepEmployeeNumber);
				System.out.println("Credit Limit: "						+ c.creditLimit);
				
				//Update the Customer Information
				c.customerNumber = cm.getValidInt(console, "Customer Number: ");
	            c.customerName = cm.getValidString(console, "Customer Name: ");
	            c.contactLastName = cm.getValidString(console, "Contact Last Name: ");
	            c.contactFirstName = cm.getValidString(console, "Contact First Name: ");
	            c.phone = cm.getValidString(console, "Phone: ");
	            c.addressLine1 = cm.getValidString(console, "Address Line 1: ");
	            c.addressLine2 = cm.getValidString(console, "Address Line 2: ");
	            c.city = cm.getValidString(console, "City: ");
	            c.state = cm.getValidString(console, "State: ");
	            c.postalCode = cm.getValidString(console, "Postal Code: ");
	            c.country = cm.getValidString(console, "Country: ");
	            c.salesRepEmployeeNumber = cm.getValidInt(console, "Sales Rep Employee Number: ");
	            c.creditLimit = cm.getValidInt(console, "Credit Limit: ");
				c.updateCustomer();
	        }
			
		} else if (menuSelection == 3) {
			customer_management c = new customer_management();
			
        	// Input Customer Number
			System.out.println("Enter Customer Information (to delete):");							
			c.customerNumber = cm.getValidInt(console, "Customer Number: ");
			
			// Checks if customer number is banned
			c.getCustomer();
			if (customer_management.isCustomerBanned(c.customerNumber)) {
	            System.out.println("Cannot delete. The customer is banned.");
	            
	        } else {
				//Delete a Customer Record
				c.deleteCustomer();
	        }
			
		} else if (menuSelection == 4) {
			customer_management c = new customer_management();

			//Bans a Customer Record
            System.out.println("Enter Customer Information (to ban):");
            c.customerNumber = cm.getValidInt(console, "Customer Number: ");

            c.banCustomer();
			
		} else if (menuSelection == 5) {
			customer_management c = new customer_management();
			int year = 0;
			
			//View a Customer Record
			System.out.println("Enter Customer Information:");
            c.customerNumber = cm.getValidInt(console, "Customer Number: ");
            year = cm.getValidInt(console, "Enter year: ");

			// Presents the Customer Record
			c.getCustomerWithOrders(year);
			System.out.println("---------------------------------------------------------------");
			System.out.println("Current Customer Information:");
			System.out.println("---------------------------------------------------------------");
			System.out.println("Customer Number: "					+ c.customerNumber);
			System.out.println("Customer Name: "					+ c.customerName);
			System.out.println("Contact Last Name: "				+ c.contactLastName);
			System.out.println("Contact First Name: "				+ c.contactFirstName);
			System.out.println("Phone: "							+ c.phone);	
			System.out.println("Address Line 1: " 					+ c.addressLine1);		
			System.out.println("Address Line 2: "					+ c.addressLine2);	
			System.out.println("City: "								+ c.city);				
			System.out.println("State: "							+ c.state);				
			System.out.println("Postal Code: "						+ c.postalCode);		
			System.out.println("Country: "							+ c.country);	
			System.out.println("Sales Rep Employee Number: "		+ c.salesRepEmployeeNumber);
			System.out.println("Credit Limit: "						+ c.creditLimit);
			
		} else if (menuSelection == 6) {
			customer_management c = new customer_management();

            // View a Customer Record without orders
            System.out.println("Enter Customer Information:");
            c.customerNumber = cm.getValidInt(console, "Customer Number: ");

            // Presents the Customer Record
            c.getCustomer();
            System.out.println("---------------------------------------------------------------");
            System.out.println("Current Customer Information:");
            System.out.println("---------------------------------------------------------------");
            System.out.println("Customer Number: " + c.customerNumber);
            System.out.println("Customer Name: " + c.customerName);
            System.out.println("Contact Last Name: " + c.contactLastName);
            System.out.println("Contact First Name: " + c.contactFirstName);
            System.out.println("Phone: " + c.phone);
            System.out.println("Address Line 1: " + c.addressLine1);
            System.out.println("Address Line 2: " + c.addressLine2);
            System.out.println("City: " + c.city);
            System.out.println("State: " + c.state);
            System.out.println("Postal Code: " + c.postalCode);
            System.out.println("Country: " + c.country);
            System.out.println("Sales Rep Employee Number: " + c.salesRepEmployeeNumber);
            System.out.println("Credit Limit: " + c.creditLimit);

		} else if (menuSelection == 0) {
			// i run
		}
		return menuSelection;
	}
}
