package G207DBAPP;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class customer_management {

	public static ArrayList<Integer> bannedCustomerIDs = new ArrayList<>();
	
	public int		customerNumber;
	public String	customerName;
	public String	contactLastName;
	public String	contactFirstName;
	public String	phone;
	public String	addressLine1;
	public String	addressLine2;
	public String	city;
	public String	state;
	public String	postalCode;
	public String	country;
	public int		salesRepEmployeeNumber;
	public int 		creditLimit;
	public boolean	isBanned;
	
	public customer_management() {
		customerNumber			= 0;
		customerName			= "";
		contactLastName			= "";
		contactFirstName		= "";
		phone					= "";
		addressLine1			= "";
		addressLine2			= "";
		city					= "";
		state					= "";
		postalCode				= "";
		country					= "";
		salesRepEmployeeNumber	= 0;
		creditLimit				= 0;
		isBanned				= false;
	}
	
	// Algorithm when interacting with databases
	// 1. Connect to the MYSQL Database
	// 2. Preparing your SQL statement
	// 3. Execute the SQL statement (if there are results, store it in a variable)
	// 4. Close the SQL statement
	// 5. Close the connection to the MYSQL Database
	
    // Method to validate integer input
    public int getValidInt(Scanner console, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(console.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }

    // Method to validate string input
    public String getValidString(Scanner console, String prompt) {
        String value;
        while (true) {
            System.out.print(prompt);
            value = console.nextLine();
            if (!value.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid string.");
            }
        }
        return value;
    }

    // Method to validate date input
    public Date getValidDate(Scanner console, String prompt) {
        Date value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Date.valueOf(console.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid date (YYYY-MM-DD).");
            }
        }
        return value;
    }
    
	public int addCustomer() {
		try {
			
			//Connect to the MYSQL Database
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
			System.out.println("Connection to DB Successful");
			
			//Prepare the SQL Statement
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO customers VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt	(1, customerNumber);
			pstmt.setString	(2, customerName);
			pstmt.setString	(3, contactLastName);
			pstmt.setString	(4, contactFirstName);
			pstmt.setString	(5, phone);
			pstmt.setString	(6, addressLine1);
			pstmt.setString	(7, addressLine2);
			pstmt.setString	(8, city);
			pstmt.setString	(9, state);
			pstmt.setString	(10, postalCode);
			pstmt.setString	(11, country);
			pstmt.setInt	(12, salesRepEmployeeNumber);
			pstmt.setInt	(13, creditLimit);
			System.out.println("SQL Statement Prepared");
			
			//Execute the SQL statement
			pstmt.executeUpdate();
			System.out.println("The Record was Updated");
			
			//Close the SQL statement
			pstmt.close();
			
			//Close the connection
			conn.close();
			
			return 1;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public int updateCustomer() {
		if (isBanned) {
	        System.out.println("Cannot delete. The customer is locally flagged as banned.");
	        return 0;
	    }
		
		try {
			
			//Connect to the MYSQL Database
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
			System.out.println("Connection to DB Successful");
			
			//Prepare the SQL Statement
			PreparedStatement pstmt = conn.prepareStatement("UPDATE customers SET customerName=?, contactLastName=?, contactFirstName=?,"
					+ "										phone=?, addressLine1=?, addressLine2=?, city=?, state=?, postalCode=?, country=?,"
					+ "										salesRepEmployeeNumber=?, creditLimit=? WHERE customerNumber=?");
			
			pstmt.setInt	(13, customerNumber);
			pstmt.setString	(1, customerName);
			pstmt.setString	(2, contactLastName);
			pstmt.setString	(3, contactFirstName);
			pstmt.setString	(4, phone);
			pstmt.setString	(5, addressLine1);
			pstmt.setString	(6, addressLine2);
			pstmt.setString	(7, city);
			pstmt.setString	(8, state);
			pstmt.setString	(9, postalCode);
			pstmt.setString	(10, country);
			pstmt.setInt	(11, salesRepEmployeeNumber);
			pstmt.setInt	(12, creditLimit);
			System.out.println("SQL Statement Prepared");
			
			//Execute the SQL statement
			pstmt.executeUpdate();
			System.out.println("Record was created");
			
			//Close the SQL statement
			pstmt.close();
			
			//Close the connection
			conn.close();
			
			return 1;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public int deleteCustomer() {
		if (isBanned) {
	        System.out.println("Cannot delete. The customer is locally flagged as banned.");
	        return 0;
	    }
		
		try {
			
			//Connect to the MYSQL Database
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
			System.out.println("Connection to DB Successful");
			
			//Prepare the SQL Statement
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM customers WHERE customerNumber=?");
			pstmt.setInt	(1, customerNumber);
			System.out.println("SQL Statement Prepared");
			
			//Execute the SQL statement
			pstmt.executeUpdate();
			System.out.println("The Record has been Deleted");
			
			//Close the SQL statement
			pstmt.close();
			
			//Close the connection
			conn.close();
			
			return 1;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public int banCustomer() {
		 try {
			 	Connection conn;
				conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
		        System.out.println("Connection to DB Successful");

		        PreparedStatement checkStmt = conn.prepareStatement("SELECT salesRepEmployeeNumber FROM customers WHERE customerNumber = ?");
		        checkStmt.setInt(1, customerNumber);
		        ResultSet rs = checkStmt.executeQuery();

		        if (rs.next() && rs.getInt("salesRepEmployeeNumber") != 0) {
		            System.out.println("Cannot ban the customer. They have a sales representative handling them.");
		            checkStmt.close();
		            conn.close();
		            return 0;
		        }
		        
		        bannedCustomerIDs.add(customerNumber); // Add customer ID to the list
		        
		        System.out.println("The Customer has been locally flagged as banned");

		        checkStmt.close();
		        conn.close();
		        return 1;

		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		        return 0;
		    }
	}
	
	public int getCustomer() {
		try {
			
			//Connect to the MYSQL Database
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
			System.out.println("Connection to DB Successful");
			
			//Prepare the SQL Statement
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM customers WHERE customerNumber=?");
			pstmt.setInt	(1, customerNumber);
			System.out.println("SQL Statement Prepared");
			
			//Execute the SQL statement
			//Stores the query into a ResultSet
			ResultSet rs = pstmt.executeQuery();
			
			//read and stores ResultSet to the class variable
			while (rs.next()) {
				customerName			= rs.getString	("customerName");
				contactLastName			= rs.getString	("contactLastName");
				contactFirstName		= rs.getString	("contactFirstname");
				phone					= rs.getString	("phone");
				addressLine1			= rs.getString	("addressLine1");
				addressLine2			= rs.getString	("addressLine2");
				city					= rs.getString	("city");
				state					= rs.getString	("state");
				postalCode				= rs.getString	("postalCode");
				country					= rs.getString	("country");
				salesRepEmployeeNumber	= rs.getInt		("salesRepEmployeeNumber");
				creditLimit				= rs.getInt		("creditLimit");
				System.out.println("The Record was Retrieved");
			}
			
			//Close the SQL statement
			pstmt.close();
			
			//Close the connection
			conn.close();
			
			return 1;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public static boolean isCustomerBanned(int customerNumber) {
        return bannedCustomerIDs.contains(customerNumber);
    }
	
	public int getCustomerWithOrders(int year) {
	    try {
	        // Connect to the MYSQL Database
	        Connection conn;
	        conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
	        System.out.println("Connection to DB Successful");

	        // Prepare the SQL Statement to select customer details and their orders for the given year
	        PreparedStatement pstmt = conn.prepareStatement(
	            "SELECT c.*, o.orderNumber, o.orderDate " +
	            "FROM customers c LEFT JOIN orders o ON c.customerNumber = o.customerNumber " +
	            "WHERE c.customerNumber = ? AND YEAR(o.orderDate) = ?");

	        pstmt.setInt(1, customerNumber);
	        pstmt.setInt(2, year);
	        System.out.println("SQL Statement Prepared");

	        // Execute the SQL statement
	        ResultSet rs = pstmt.executeQuery();

	        // Read and store ResultSet to the class variables
	        boolean found = false;
	        while (rs.next()) {
	            if (!found) {
	                customerName = rs.getString("customerName");
	                contactLastName = rs.getString("contactLastName");
	                contactFirstName = rs.getString("contactFirstname");
	                phone = rs.getString("phone");
	                addressLine1 = rs.getString("addressLine1");
	                addressLine2 = rs.getString("addressLine2");
	                city = rs.getString("city");
	                state = rs.getString("state");
	                postalCode = rs.getString("postalCode");
	                country = rs.getString("country");
	                salesRepEmployeeNumber = rs.getInt("salesRepEmployeeNumber");
	                creditLimit = rs.getInt("creditLimit");
	                System.out.println("Customer Record Retrieved");
	                found = true;
	            }
	            int orderNumber = rs.getInt("orderNumber");
	            Date orderDate = rs.getDate("orderDate");
				System.out.println("Order Number: " + orderNumber + ", Order Date: " + orderDate);
	            System.out.println("");
	        }

	        // Check if customer was found
	        if (!found) {
	            System.out.println("No customer order found with the specified ID.");
	        }

	        // Close the SQL statement
	        pstmt.close();

	        // Close the connection
	        conn.close();

	        return found ? 1 : 0;

	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return 0;
	    }
	}
}
