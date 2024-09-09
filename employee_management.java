package G207DBAPP;

import java.sql.*;

public class employee_management {
    private int employeeNumber;
    private String lastName;
    private String firstName;
    private String extension;
    private String email;
    private String officeCode;
    private int reportsTo;
    private String jobTitle;

    public employee_management() {
        employeeNumber = 0;
        lastName = "";
        firstName = "";
        extension = "";
        email = "";
        officeCode = "";
        reportsTo = 0;
        jobTitle = "";
    }

    public void createEmployee(int employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, int reportsTo, String jobTitle) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            String sql = "INSERT INTO employees (employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeNumber);
            pstmt.setString(2, lastName);
            pstmt.setString(3, firstName);
            pstmt.setString(4, extension);
            pstmt.setString(5, email);
            pstmt.setString(6, officeCode);
            pstmt.setInt(7, reportsTo);
            pstmt.setString(8, jobTitle);
            pstmt.executeUpdate();
            System.out.println("Employee created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating employee: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Method to update an employee with parameters
    public void updateEmployee(int employeeNumber, String field, String newValue) {
        Connection conn = null;
        PreparedStatement pstmtCheckResigned = null;
        PreparedStatement pstmtUpdate = null;

        try {
            // Establish database connection
            conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");

            // Check if employee is resigned
            String sqlCheckResigned = "SELECT jobTitle FROM employees WHERE employeeNumber = ?";
            pstmtCheckResigned = conn.prepareStatement(sqlCheckResigned);
            pstmtCheckResigned.setInt(1, employeeNumber);

            ResultSet rs = pstmtCheckResigned.executeQuery();
            if (rs.next() && "Resigned".equals(rs.getString("jobTitle"))) {
                System.out.println("Cannot update resigned employee.");
                return;
            }

            // Proceed with update operation
            String sqlUpdate = "UPDATE employees SET " + field + " = ? WHERE employeeNumber = ?";
            pstmtUpdate = conn.prepareStatement(sqlUpdate);
            pstmtUpdate.setString(1, newValue);
            pstmtUpdate.setInt(2, employeeNumber);

            int rowsUpdated = pstmtUpdate.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmtCheckResigned != null) pstmtCheckResigned.close();
                if (pstmtUpdate != null) pstmtUpdate.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Method to delete an employee with parameters
    public void deleteEmployee(int employeeNumber) {
        Connection conn = null;
        PreparedStatement pstmtCheckResigned = null;
        PreparedStatement pstmtDelete = null;

        try {
            // Establish database connection
            conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");

            // Check if employee is resigned
            String sqlCheckResigned = "SELECT jobTitle FROM employees WHERE employeeNumber = ?";
            pstmtCheckResigned = conn.prepareStatement(sqlCheckResigned);
            pstmtCheckResigned.setInt(1, employeeNumber);

            ResultSet rs = pstmtCheckResigned.executeQuery();
            if (rs.next() && "Resigned".equals(rs.getString("jobTitle"))) {
                System.out.println("Cannot delete resigned employee.");
                return;
            }

            // Proceed with delete operation
            String sqlDelete = "DELETE FROM employees WHERE employeeNumber = ?";
            pstmtDelete = conn.prepareStatement(sqlDelete);
            pstmtDelete.setInt(1, employeeNumber);

            int rowsDeleted = pstmtDelete.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmtCheckResigned != null) pstmtCheckResigned.close();
                if (pstmtDelete != null) pstmtDelete.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Method to resign an employee with parameters
    public void resignEmployee(int employeeNumber) {
        Connection conn = null;
        PreparedStatement pstmtUpdateCustomers = null;
        PreparedStatement pstmtUpdateEmployee = null;

        try {
            // Establish database connection
            conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");

            // Update customers table to remove sales representative
            String sqlUpdateCustomers = "UPDATE customers SET salesRepEmployeeNumber = NULL WHERE salesRepEmployeeNumber = ?";
            pstmtUpdateCustomers = conn.prepareStatement(sqlUpdateCustomers);
            pstmtUpdateCustomers.setInt(1, employeeNumber);

            int rowsUpdatedCustomers = pstmtUpdateCustomers.executeUpdate();
            System.out.println("Updated " + rowsUpdatedCustomers + " customers to remove sales representative.");

            // Mark employee as resigned by setting jobTitle to "Resigned"
            String sqlUpdateEmployee = "UPDATE employees SET jobTitle = 'Resigned' WHERE employeeNumber = ?";
            pstmtUpdateEmployee = conn.prepareStatement(sqlUpdateEmployee);
            pstmtUpdateEmployee.setInt(1, employeeNumber);

            int rowsUpdatedEmployee = pstmtUpdateEmployee.executeUpdate();
            if (rowsUpdatedEmployee > 0) {
                System.out.println("Employee resigned successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmtUpdateCustomers != null) pstmtUpdateCustomers.close();
                if (pstmtUpdateEmployee != null) pstmtUpdateEmployee.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void viewEmployee(int employeeNumber) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            String sql = "SELECT * FROM employees WHERE employeeNumber = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Employee Number: " + rs.getInt("employeeNumber"));
                System.out.println("Last Name: " + rs.getString("lastName"));
                System.out.println("First Name: " + rs.getString("firstName"));
                System.out.println("Extension: " + rs.getString("extension"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Office Code: " + rs.getString("officeCode"));
                System.out.println("Reports To: " + rs.getInt("reportsTo"));
                System.out.println("Job Title: " + rs.getString("jobTitle"));

                if (rs.getString("jobTitle").equalsIgnoreCase("Sales Rep")) {
                    String sqlCustomers = "SELECT customerNumber, customerName FROM customers WHERE salesRepEmployeeNumber = ?";
                    PreparedStatement pstmtCustomers = conn.prepareStatement(sqlCustomers);
                    pstmtCustomers.setInt(1, employeeNumber);
                    ResultSet rsCustomers = pstmtCustomers.executeQuery();
                    System.out.println("Customers handled:");
                    while (rsCustomers.next()) {
                        System.out.println("Customer Number: " + rsCustomers.getInt("customerNumber") + ", Customer Name: " + rsCustomers.getString("customerName"));
                    }
                }
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing employee: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
