package G207DBAPP;
import java.sql.*;
import java.sql.Date;
import java.util.*;
	
		public class payment_transaction {
			public int customerNumber;
			public String checkNumber;
			public Date paymentDate;
			public double amount;
	
			public payment_transaction() {
				customerNumber = 0;
				checkNumber = "";
				paymentDate = null;
				amount = 0.0;
			}
	
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
		    
		    public double getValidDouble(Scanner console, String prompt) {
		        double value;
		        while (true) {
		            System.out.print(prompt);
		            try {
		                value = Double.parseDouble(console.nextLine());
		                break;
		            } catch (NumberFormatException e) {
		                System.out.println("Invalid input. Please enter a valid double.");
		            }
		        }
		        return value;
		    }
		    
			// Method to add a new payment
		    public int addPayment() {
		        try {
		            // Connect to the MYSQL Database
		            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
		            System.out.println("Connection to DB Successful");

		            // Calculate the total unpaid amount for the customer
		            PreparedStatement unpaidStmt = conn.prepareStatement(
		                "SELECT SUM(orderdetails.quantityOrdered * orderdetails.priceEach) AS totalUnpaid " +
		                "FROM orders " +
		                "JOIN orderdetails ON orders.orderNumber = orderdetails.orderNumber " +
		                "WHERE orders.customerNumber = ? AND orders.status != 'Paid'"
		            );
		            unpaidStmt.setInt(1, customerNumber);
		            ResultSet rs = unpaidStmt.executeQuery();

		            double totalUnpaid = 0;
		            if (rs.next()) {
		                totalUnpaid = rs.getDouble("totalUnpaid");
		            }
		            unpaidStmt.close();

		            // Check if the payment exceeds the total amount of unpaid orders
		            if (amount > totalUnpaid) {
		                System.out.println("Payment exceeds the total amount of unpaid orders. Overpayment is not allowed.");
		                conn.close();
		                return 0;
		            }

		            // Prepare the SQL Statement
		            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO payments (customerNumber, checkNumber, paymentDate, amount) VALUES (?,?,?,?)");
		            pstmt.setInt(1, customerNumber);
		            pstmt.setString(2, checkNumber);
		            pstmt.setDate(3, paymentDate);
		            pstmt.setDouble(4, amount);
		            System.out.println("SQL Statement Prepared");

		            // Execute the SQL statement
		            pstmt.executeUpdate();
		            System.out.println("The Payment was Added");

		            // Close the SQL statement and connection
		            pstmt.close();
		            conn.close();

		            return 1;

		        } catch (Exception e) {
		            System.out.println(e.getMessage());
		            return 0;
		        }
		    }

	
	    // Method to update an existing payment
		    public int updatePayment() {
		        try {
		            // Connect to the MYSQL Database
		            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
		            System.out.println("Connection to DB Successful");

		            // Calculate the total unpaid amount for the customer
		            PreparedStatement unpaidStmt = conn.prepareStatement(
		                "SELECT SUM(orderdetails.quantityOrdered * orderdetails.priceEach) AS totalUnpaid " +
		                "FROM orders " +
		                "JOIN orderdetails ON orders.orderNumber = orderdetails.orderNumber " +
		                "WHERE orders.customerNumber = ? AND orders.status != 'Paid'"
		            );
		            unpaidStmt.setInt(1, customerNumber);
		            ResultSet rs = unpaidStmt.executeQuery();

		            double totalUnpaid = 0;
		            if (rs.next()) {
		                totalUnpaid = rs.getDouble("totalUnpaid");
		            }
		            unpaidStmt.close();

		            // Check if the payment exceeds the total amount of unpaid orders
		            if (amount > totalUnpaid) {
		                System.out.println("Payment exceeds the total amount of unpaid orders. Overpayment is not allowed.");
		                conn.close();
		                return 0;
		            }

		            // Prepare the SQL Statement
		            PreparedStatement pstmt = conn.prepareStatement("UPDATE payments SET paymentDate = ?, amount = ? WHERE customerNumber = ? AND checkNumber = ?");
		            pstmt.setDate(1, paymentDate);
		            pstmt.setDouble(2, amount);
		            pstmt.setInt(3, customerNumber);
		            pstmt.setString(4, checkNumber);
		            System.out.println("SQL Statement Prepared");

		            // Execute the SQL statement
		            pstmt.executeUpdate();
		            System.out.println("The Payment was Updated");

		            // Close the SQL statement and connection
		            pstmt.close();
		            conn.close();

		            return 1;

		        } catch (Exception e) {
		            System.out.println(e.getMessage());
		            return 0;
		        }
		    }
	
	    // Method to delete an existing payment
	    public int deletePayment() {
	        try {
	            // Connect to the MYSQL Database
	        	Connection conn;
	        	conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
	            System.out.println("Connection to DB Successful");
	
	            // Prepare the SQL Statement
	            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM payments WHERE customerNumber = ? AND checkNumber = ?");
	            pstmt.setInt(1, customerNumber);
	            pstmt.setString(2, checkNumber);
	            System.out.println("SQL Statement Prepared");
	
	            // Execute the SQL statement
	            pstmt.executeUpdate();
	            System.out.println("The Payment was Deleted");
	
	            // Close the SQL statement
	            pstmt.close();
	
	            // Close the connection
	            conn.close();
	
	            return 1;
	
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return 0;
	        }
	   }
	    
	    public int getPayment() {
	        try {
	            // Connect to the MYSQL Database
	        	Connection conn;
	            conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
	            System.out.println("Connection to DB Successful");

	            // Prepare the SQL Statement
	            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM payments WHERE customerNumber = ? AND checkNumber = ?");
	            pstmt.setInt(1, customerNumber);
	            pstmt.setString(2, checkNumber);
	            System.out.println("SQL Statement Prepared");

	            // Execute the SQL statement
	            ResultSet rs = pstmt.executeQuery();

	            // Check if payment record exists and retrieve the details
	            if (rs.next()) {
	                paymentDate = rs.getDate("paymentDate");
	                amount = rs.getDouble("amount");
	                System.out.println("---------------------------------------------------------------");
	                System.out.println("Payment Details Retrieved:");
	                System.out.println("Customer Number: " + customerNumber);
	                System.out.println("Check Number: " + checkNumber);
	                System.out.println("Payment Date: " + paymentDate);
	                System.out.println("Amount: " + amount);
	            } else {
	                System.out.println("No payment record found for the given customer number and check number.");
	            }

	            // Close the SQL statement and connection
	            rs.close();
	            pstmt.close();
	            conn.close();

	            return 1;

	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return 0;
	        }
	    }
	}