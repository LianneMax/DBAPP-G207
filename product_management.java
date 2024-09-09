package G207DBAPP;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class product_management {

    // Product attributes
    public String productCode;
    public String productName;
    public String productLine;
    public String productScale;
    public String productDescription;
    public String productVendor;
    public int quantityInStock;
    public float buyPrice;
    public float MSRP;

    // Constructor to initialize the product attributes
    public product_management() {
        productCode = "";
        productName = "";
        productLine = "";
        productScale = "";
        productDescription = "";
        productVendor = "";
        quantityInStock = 0;
        buyPrice = 0;
        MSRP = 0;
    }

    // Method to get the list of valid product lines from the database
    public List<String> getValidProductLines() {
        List<String> validProductLines = new ArrayList<>();
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");

            // Prepare and execute SQL query to get product lines
            PreparedStatement pstmt = conn.prepareStatement("SELECT productLine FROM productlines");
            ResultSet rs = pstmt.executeQuery();

            // Add each product line to the list
            while (rs.next()) {
                validProductLines.add(rs.getString("productLine"));
            }

            // Close statement and connection
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return validProductLines;
    }

    // Method to validate if the product line is valid
    public boolean isProductLineValid(String productLine) {
        List<String> validProductLines = getValidProductLines();
        return validProductLines.contains(productLine);
    }

    // Method to validate if the product scale is in the correct format
    public boolean isProductScaleValid(String productScale) {
        return productScale.matches("1:\\d+");
    }

    // Method to check if the product is discontinued
    public boolean isProductDiscontinued() {
        return productDescription.startsWith("DISCONTINUED -");
    }

    // Method to add a new product to the database
    public int add_product() {
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            System.out.println("\nConnection to DB Successful\n");

            // Check if the product already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM products WHERE productCode=?");
            checkStmt.setString(1, productCode);
            ResultSet checkRs = checkStmt.executeQuery();
            checkRs.next();
            if (checkRs.getInt(1) > 0) {
                System.out.println("---------------------------------------------");
                System.out.println("This Product Already Exists\n");
                return 0; // Product already exists
            }
            checkStmt.close();

            // Validate product line
            if (!isProductLineValid(productLine)) {
                System.out.println("---------------------------------------------");
                System.out.println("Invalid Product Line\n");
                return 0; // Invalid product line
            }

            // Validate product scale
            if (!isProductScaleValid(productScale)) {
                System.out.println("---------------------------------------------");
                System.out.println("Invalid Product Scale Format\n");
                return 0; // Invalid product scale format
            }

            // Validate that buy price is not more than MSRP
            if (buyPrice > MSRP) {
                System.out.println("---------------------------------------------");
                System.out.println("Buy Price cannot be more than MSRP\n");
                return 0; // Invalid pricing
            }

            // Prepare SQL statement to insert new product
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO products VALUES (?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, productCode);
            pstmt.setString(2, productName);
            pstmt.setString(3, productLine);
            pstmt.setString(4, productScale);
            pstmt.setString(5, productDescription);
            pstmt.setString(6, productVendor);
            pstmt.setInt(7, quantityInStock);
            pstmt.setFloat(8, buyPrice);
            pstmt.setFloat(9, MSRP);
            System.out.println("SQL Statement Prepared\n");

            // Execute SQL statement
            pstmt.executeUpdate();
            System.out.println("Record was created\n");

            // Close statement and connection
            pstmt.close();
            conn.close();
            return 1; // Success
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0; // Failure
        }
    }

    // Method to update an existing product in the database
    public int update_product() {
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            System.out.println("\nConnection to DB Successful\n");

            // Check if the product exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT productDescription FROM products WHERE productCode=?");
            checkStmt.setString(1, productCode);
            ResultSet checkRs = checkStmt.executeQuery();
            checkRs.next();
            if (checkRs.getRow() == 0) {
                System.out.println("---------------------------------------------");
                System.out.println("This Product does not exist\n");
                return 0; // Product does not exist
            }
            productDescription = checkRs.getString("productDescription");
            if (isProductDiscontinued()) {
                System.out.println("---------------------------------------------");
                System.out.println("Discontinued Products cannot be updated\n");
                return 0; // Product is discontinued
            }
            checkStmt.close();

            // Validate product line
            if (!isProductLineValid(productLine)) {
                System.out.println("---------------------------------------------");
                System.out.println("Invalid Product Line\n");
                return 0; // Invalid product line
            }

            // Validate product scale
            if (!isProductScaleValid(productScale)) {
                System.out.println("---------------------------------------------");
                System.out.println("Invalid Product Scale Format\n");
                return 0; // Invalid product scale format
            }

            // Validate buy price, MSRP, and quantity in stock
            if (buyPrice < 0 || MSRP < 0 || quantityInStock < 0) {
                System.out.println("---------------------------------------------");
                System.out.println("Buy Price, MSRP, and Quantity in Stock cannot be negative\n");
                return 0; // Invalid values
            }

            // Validate that buy price is not more than MSRP
            if (buyPrice > MSRP) {
                System.out.println("---------------------------------------------");
                System.out.println("Buy Price cannot be more than MSRP\n");
                return 0; // Invalid pricing
            }

            // Prepare SQL statement to update product
            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE products SET productName=?, productLine=?, productScale=?, productDescription=?, productVendor=?, quantityInStock=?, buyPrice=?, MSRP=? WHERE productCode=?");
            pstmt.setString(1, productName);
            pstmt.setString(2, productLine);
            pstmt.setString(3, productScale);
            pstmt.setString(4, productDescription);
            pstmt.setString(5, productVendor);
            pstmt.setInt(6, quantityInStock);
            pstmt.setFloat(7, buyPrice);
            pstmt.setFloat(8, MSRP);
            pstmt.setString(9, productCode);
            System.out.println("SQL Statement Prepared\n");

            // Execute SQL statement
            pstmt.executeUpdate();
            System.out.println("Record was updated\n");

            // Close statement and connection
            pstmt.close();
            conn.close();
            return 1; // Success
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0; // Failure
        }
    }

    // Method to delete a product from the database
    public int delete_product() {
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            System.out.println("\nConnection to DB Successful\n");

            // Check if the product exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT productDescription FROM products WHERE productCode=?");
            checkStmt.setString(1, productCode);
            ResultSet checkRs = checkStmt.executeQuery();
            checkRs.next();
            if (checkRs.getRow() == 0) {
                System.out.println("---------------------------------------------");
                System.out.println("This Product does not exist\n");
                return 0; // Product does not exist
            }
            productDescription = checkRs.getString("productDescription");
            if (isProductDiscontinued()) {
                System.out.println("---------------------------------------------");
                System.out.println("Discontinued Products cannot be deleted\n");
                return 0; // Product is discontinued
            }
            checkStmt.close();

            // Check if the product is referenced in order details
            PreparedStatement refCheckStmt = conn.prepareStatement("SELECT COUNT(*) FROM orderdetails WHERE productCode=?");
            refCheckStmt.setString(1, productCode);
            ResultSet refCheckRs = refCheckStmt.executeQuery();
            refCheckRs.next();
            if (refCheckRs.getInt(1) > 0) {
                System.out.println("---------------------------------------------");
                System.out.println("This product cannot be deleted as it is referenced in other records\n");
                return 0; // Product is referenced in other records
            }
            refCheckStmt.close();

            // Prepare SQL statement to delete product
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM products WHERE productCode=?");
            pstmt.setString(1, productCode);
            System.out.println("SQL Statement Prepared\n");

            // Execute SQL statement
            pstmt.executeUpdate();
            System.out.println("Record was deleted\n");

            // Close statement and connection
            pstmt.close();
            conn.close();
            return 1; // Success
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0; // Failure
        }
    }

    // Method to discontinue a product (mark as discontinued)
    public int discontinue_product() {
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            System.out.println("\nConnection to DB Successful\n");

            // Check if the product exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT productDescription FROM products WHERE productCode=?");
            checkStmt.setString(1, productCode);
            ResultSet checkRs = checkStmt.executeQuery();
            checkRs.next();
            if (checkRs.getRow() == 0) {
                System.out.println("---------------------------------------------");
                System.out.println("This Product does not exist\n");
                return 0; // Product does not exist
            }
            productDescription = checkRs.getString("productDescription");
            if (isProductDiscontinued()) {
                System.out.println("---------------------------------------------");
                System.out.println("Product is already discontinued\n");
                return 0; // Product is already discontinued
            }
            checkStmt.close();

            // Prepare SQL statement to mark product as discontinued
            PreparedStatement pstmt = conn.prepareStatement("UPDATE products SET productDescription=? WHERE productCode=?");
            pstmt.setString(1, "DISCONTINUED - " + productDescription);
            pstmt.setString(2, productCode);
            System.out.println("SQL Statement Prepared\n");

            // Execute SQL statement
            pstmt.executeUpdate();
            System.out.println("Product was discontinued\n");

            // Close statement and connection
            pstmt.close();
            conn.close();
            return 1; // Success
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0; // Failure
        }
    }

    // Method to get a product's details from the database
    public int get_product() {
        int recordcount = 0;
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            System.out.println("\nConnection to DB Successful\n");

            // Prepare SQL statement to get product details
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM products WHERE productCode=?");
            pstmt.setString(1, productCode);
            System.out.println("SQL Statement Prepared\n");

            // Execute SQL statement and retrieve product details
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                recordcount++;
                productName = rs.getString("productName");
                productLine = rs.getString("productLine");
                productScale = rs.getString("productScale");
                productDescription = rs.getString("productDescription");
                productVendor = rs.getString("productVendor");
                quantityInStock = rs.getInt("quantityInStock");
                buyPrice = rs.getFloat("buyPrice");
                MSRP = rs.getFloat("MSRP");
            }

            // Close statement and connection
            pstmt.close();
            conn.close();
            return recordcount; // Return number of records found
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0; // Failure
        }
    }

    // Method to make a product current
    public int make_product_current() {
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            System.out.println("\nConnection to DB Successful\n");

            // Check if the product exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT productDescription FROM products WHERE productCode=?");
            checkStmt.setString(1, productCode);
            ResultSet checkRs = checkStmt.executeQuery();
            checkRs.next();
            if (checkRs.getRow() == 0) {
                System.out.println("---------------------------------------------");
                System.out.println("This Product does not exist\n");
                return 0; // Product does not exist
            }
            productDescription = checkRs.getString("productDescription");
            if (!isProductDiscontinued()) {
                System.out.println("---------------------------------------------");
                System.out.println("Product is not discontinued\n");
                return 0; // Product is not discontinued
            }
            checkStmt.close();

            // Prepare SQL statement to make product current
            String newDescription = productDescription.replaceFirst("DISCONTINUED - ", "");
            PreparedStatement pstmt = conn.prepareStatement("UPDATE products SET productDescription=? WHERE productCode=?");
            pstmt.setString(1, newDescription);
            pstmt.setString(2, productCode);
            System.out.println("SQL Statement Prepared\n");

            // Execute SQL statement
            pstmt.executeUpdate();
            System.out.println("Product is now current\n");

            // Close statement and connection
            pstmt.close();
            conn.close();
            return 1; // Success
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0; // Failure
        }
    }

    // Method to get the orders for a product in a specific year
    public int get_product_orders(int year) {
        int recordcount = 0;
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            System.out.println("\nConnection to DB Successful\n");

            // Check if the product exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM products WHERE productCode=?");
            checkStmt.setString(1, productCode);
            ResultSet checkRs = checkStmt.executeQuery();
            checkRs.next();
            if (checkRs.getInt(1) == 0) {
                System.out.println("---------------------------------------------");
                System.out.println("This Product does not exist\n");
                return 0; // Product does not exist
            }
            checkStmt.close();

            // Prepare SQL statement to get orders for the product in the specified year
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT orders.orderNumber, orders.orderDate, orders.status FROM orderdetails " +
                "JOIN orders ON orderdetails.orderNumber = orders.orderNumber " +
                "WHERE orderdetails.productCode = ? AND YEAR(orders.orderDate) = ?");
            pstmt.setString(1, productCode);
            pstmt.setInt(2, year);
            System.out.println("SQL Statement Prepared\n");

            // Execute SQL statement and retrieve orders
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Orders for Product Code: " + productCode + " in Year: " + year);
            System.out.println("-------------------------------------------------------------------\n");
            while (rs.next()) {
                recordcount++;
                System.out.println("Order Number        : " + rs.getInt("orderNumber"));
                System.out.println("Order Date          : " + rs.getDate("orderDate"));
                System.out.println("Order Status        : " + rs.getString("status"));
                System.out.println("-------------------------------------------------------------------\n");
            }

            // Close statement and connection
            pstmt.close();
            conn.close();
            return recordcount; // Return number of records found
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0; // Failure
        }
    }
}
