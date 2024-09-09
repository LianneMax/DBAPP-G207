package G207DBAPP;
import java.sql.*;
import java.util.Scanner;

public class office_management_menu {

    public office_management_menu() {
    }

    public int menu() {
        int menuselection = 0;
        Scanner console = new Scanner(System.in);

        while (true) {
            System.out.println("===============================================================");
            System.out.println("			Office Management Menu		     			");
            System.out.println("---------------------------------------------------------------");
            System.out.println("		[1] Create a new Office");
            System.out.println("		[2] Update a Record");
            System.out.println("		[3] Delete a Record");
            System.out.println("		[4] Close an Office");
            System.out.println("		[5] View a Record");
            System.out.println("		[0] Exit ");
            System.out.println("===============================================================");

            System.out.println("Enter Selected Function: ");
            menuselection = Integer.parseInt(console.nextLine());

            office_management office = new office_management();

            switch (menuselection) {
                case 1://Create new office 
                    System.out.println("Enter New Office Information:");
                    office.officeCode = getValidString(console, "Office Code: ");
                    office.city = getValidString(console, "City: ");
                    office.phone = getValidString(console, "Phone: ");
                    office.addressLine01 = getValidString(console, "Address Line 1: ");
                    office.addressLine02 = getValidString(console, "Address Line 2: ");
                    office.state = getValidString(console, "State: ");
                    office.country = getValidString(console, "Country: ");
                    office.postalCode = getValidString(console, "Postal Code: ");
                    office.territory = getValidString(console, "Territory: ");

                    int createResult = office.createNew_office();
                    System.out.println(createResult == 1 ? "Office created successfully." : "Failed to create office.");
                    break;

                case 2:// Update an Office Record
                    System.out.println("Enter Office Code to Update:");
                    office.officeCode = getValidString(console, "Office Code: ");
                    int result = office.get_office(); // Retrieve current office details

                    if (result == 1) {
                        System.out.println("Enter New Information:");
                        office.city = getValidString(console, "City: ");
                        office.phone = getValidString(console, "Phone: ");
                        office.addressLine01 = getValidString(console, "Address Line 1: ");
                        office.addressLine02 = getValidString(console, "Address Line 2: ");
                        office.state = getValidString(console, "State: ");
                        office.country = getValidString(console, "Country: ");
                        office.postalCode = getValidString(console, "Postal Code: ");
                        office.territory = getValidString(console, "Territory: ");

                        int updateResult = office.update_office();
                        System.out.println(updateResult == 1 ? "Office updated successfully." : "Failed to update office.");
                    } else {
                        System.out.println("No office record found for the given office code.");
                    }
                    break;

                case 3: // Delete an Office Record
                    System.out.println("Enter Office Code to Delete:");
                    office.officeCode = getValidString(console, "Office Code: ");

                    int deleteResult = office.delete_office();
                    System.out.println(deleteResult == 1 ? "Office deleted successfully." : "Failed to delete office.");
                    break;

                case 4: // Close an Office
                    System.out.println("Enter Office Code to Close:");
                    office.officeCode = getValidString(console, "Office Code: ");

                    int closeResult = office.close_office();
                    System.out.println(closeResult == 1 ? "Office closed successfully." : "Failed to close office.");
                    break;

                case 5:// View a Record 
                    System.out.println("Enter Office Code to View:");
                    office.officeCode = getValidString(console, "Office Code: ");
                    int viewResult = office.get_office();

                    if (viewResult == 1) {
                        System.out.println("Office Details:");
                        System.out.println("City: " + office.city);
                        System.out.println("Phone: " + office.phone);
                        System.out.println("Address Line 1: " + office.addressLine01);
                        System.out.println("Address Line 2: " + office.addressLine02);
                        System.out.println("State: " + office.state);
                        System.out.println("Country: " + office.country);
                        System.out.println("Postal Code: " + office.postalCode);
                        System.out.println("Territory: " + office.territory);
                       
                        System.out.println("Employees assigned to this office:");
                        listEmployees(office.officeCode);
                    } else {
                        System.out.println("No office record found for the given office code.");
                    }
                    break;

                case 0:
                 return 0;

                default:
                    System.out.println("Invalid selection. Please enter a number between 0 and 5.");
                    break;
            }
        }
    }

//getters 
    private String getValidString(Scanner console, String prompt) {
        System.out.print(prompt);
        return console.nextLine();
    }

    private int getValidInt(Scanner console, String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(console.nextLine());
    }

    private double getValidDouble(Scanner console, String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(console.nextLine());
    }

    private java.sql.Date getValidDate(Scanner console, String prompt) {
        System.out.print(prompt);
        return java.sql.Date.valueOf(console.nextLine());
    }


    private void listEmployees(String officeCode) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            String query = "SELECT * FROM employees WHERE officeCode = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, officeCode);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Assuming you have these columns in your employees table
                System.out.println("Employee ID: " + rs.getInt("employeeID"));
                System.out.println("First Name: " + rs.getString("firstName"));
                System.out.println("Last Name: " + rs.getString("lastName"));
                System.out.println("Position: " + rs.getString("position"));
                System.out.println("---------------------------------------------------------------");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving employees: " + e.getMessage());
        }
    }
}
