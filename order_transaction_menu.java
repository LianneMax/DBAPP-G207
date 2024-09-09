package G207DBAPP;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class order_transaction_menu {

    public order_transaction_menu() {
    }

    public int menu() {
        int menuselection = -1;
        Scanner console = new Scanner(System.in);
        order_transaction ot = new order_transaction();

        while (menuselection != 0) {
            System.out.println();
            System.out.println("=======================================================");
            System.out.println("                   Order Transaction Menu              ");
            System.out.println("=======================================================");
            System.out.println("|  [1] Create a New Order                              |");
            System.out.println("|  [2] Update an Order                                 |");
            System.out.println("|  [3] Update a Product in an Order                    |");
            System.out.println("|  [4] Delete a Product in an Order                    |");
            System.out.println("|  [0] Exit Order Transaction Menu                     |");
            System.out.println("=======================================================");
            System.out.print("Enter Selected Function: ");

            if (console.hasNextInt()) {
                menuselection = console.nextInt();
                console.nextLine(); // Consume newline

                switch (menuselection) {
                    case 1:
                        createOrder(console, ot);
                        break;
                    case 2:
                        updateOrder(console, ot);
                        break;
                    case 3:
                        updateOrderDetail(console, ot);
                        break;
                    case 4:
                        deleteOrderDetail(console, ot);
                        break;
                    case 0:
                        System.out.println("\nExiting Order Transaction Menu.");
                        break;
                    default:
                        System.out.println("Invalid selection. Please enter a number between 0 and 4.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                console.next(); // Consume the invalid input
            }
        }

        return menuselection;
    }

    private void createOrder(Scanner console, order_transaction ot) {
        System.out.println("\n------- Enter order information -------");

        while (true) {
            ot.orderNumber = getInt(console, "Order Number: ");
            if (!ot.isOrderNumberExists(ot.orderNumber)) {
                break;
            } else {
                System.out.println("Order Number already exists. Please enter a unique Order Number.");
            }
        }

        ot.orderDate = getDate(console, "Order Date (yyyy-MM-dd HH:mm:ss): ");
        ot.requiredDate = getDate(console, "Required Date (yyyy-MM-dd HH:mm:ss): ");

        // Get optional shipped date first
        ot.shippedDate = getOptionalDate(console, "Shipped Date (yyyy-MM-dd HH:mm:ss, optional): ");

        // Status selection
        ot.status = getStatus(console);

        // Ensure shipped date is entered if status is "Shipped"
        if (ot.status.equalsIgnoreCase("Shipped")) {
            while (ot.shippedDate == null) {
                System.out.println("Shipped Date is required for 'Shipped' status.");
                ot.shippedDate = getDate(console, "Shipped Date (yyyy-MM-dd HH:mm:ss): ");
            }
        }

        ot.comments = getString(console, "Comments: ");
        ot.customerNumber = getValidCustomerNumber(console, ot);

        // Enter order details
        System.out.println("\n------- Enter order details -------");
        String productCode = getString(console, "Product Code: ");
        while (!ot.isProductCodeValid(productCode)) {
            System.out.println("Invalid Product Code. Please enter a valid Product Code.");
            productCode = getString(console, "Product Code: ");
        }
        int quantityInStock = ot.getProductStock(productCode);
        System.out.println("Product Code: " + productCode + " has " + quantityInStock + " quantity in stock.");
        int quantityOrdered = getValidQuantity(console, ot, productCode);
        double priceEach = getValidPriceEach(console, ot, productCode);
        int orderLineNumber = ot.getOrderLineNumber(ot.orderNumber);

        ot.orderDetails.add(new order_transaction.OrderDetail(ot.orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber));

        ot.create_order();
    }

    private void updateOrder(Scanner console, order_transaction ot) {
        System.out.println("\n------- Update order information -------");
        ot.orderNumber = getInt(console, "Order Number: ");

        // Check if order exists and retrieve current details
        if (ot.get_order() > 0) {
            // Check if the order is in-process
            if (!ot.status.equalsIgnoreCase("In Process")) {
                System.out.println("This Order is not in-process");
                return;
            }

            ot.orderDate = getDate(console, "Order Date (yyyy-MM-dd HH:mm:ss): ");
            ot.requiredDate = getDate(console, "Required Date (yyyy-MM-dd HH:mm:ss): ");

            // Get optional shipped date first
            ot.shippedDate = getOptionalDate(console, "Shipped Date (yyyy-MM-dd HH:mm:ss, optional): ");

            // Status selection
            ot.status = getStatus(console);

            // Ensure shipped date is entered if status is "Shipped"
            if (ot.status.equalsIgnoreCase("Shipped")) {
                while (ot.shippedDate == null) {
                    System.out.println("Shipped Date is required for 'Shipped' status.");
                    ot.shippedDate = getDate(console, "Shipped Date (yyyy-MM-dd HH:mm:ss): ");
                }
            }

            ot.comments = getString(console, "Comments: ");
            ot.customerNumber = getValidCustomerNumber(console, ot);

            ot.update_order();
        } else {
            System.out.println("This Order does not exist");
        }
    }

    private void updateOrderDetail(Scanner console, order_transaction ot) {
        System.out.println("\n------- Update product in order -------");
        ot.orderNumber = getInt(console, "Order Number: ");

        // Check if order exists and retrieve current details
        if (ot.get_order() > 0) {
            // Check if the order is in-process
            if (!ot.status.equalsIgnoreCase("In Process")) {
                System.out.println("This Order is not in-process");
                return;
            }

            String productCode = getString(console, "Product Code: ");
            // Fetch and display current order detail
            if (ot.get_order_detail(productCode) > 0) {
                int quantityInStock = ot.getProductStock(productCode);
                System.out.println("Product Code: " + productCode + " has " + quantityInStock + " quantity in stock.");
                int quantityOrdered = getValidQuantity(console, ot, productCode);
                double priceEach = getValidPriceEach(console, ot, productCode);

                ot.update_order_detail(productCode, quantityOrdered, priceEach);
            } else {
                System.out.println("This Product does not exist in the order");
            }
        } else {
            System.out.println("This Order does not exist");
        }
    }

    private void deleteOrderDetail(Scanner console, order_transaction ot) {
        System.out.println("\n------- Delete product in order -------");
        ot.orderNumber = getInt(console, "Order Number: ");

        // Check if order exists and retrieve current details
        if (ot.get_order() > 0) {
            // Check if the order is in-process
            if (!ot.status.equalsIgnoreCase("In Process")) {
                System.out.println("This Order is not in-process");
                return;
            }

            String productCode = getString(console, "Product Code: ");
            // Check if order detail exists before attempting to delete
            if (ot.get_order_detail(productCode) > 0) {
                ot.delete_order_detail(productCode);
            } else {
                System.out.println("This Product does not exist in the order");
            }
        } else {
            System.out.println("This Order does not exist");
        }
    }

    private java.sql.Timestamp getDate(Scanner console, String prompt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false); // Ensure strict date validation
        while (true) {
            System.out.print(prompt);
            try {
                Date date = sdf.parse(console.nextLine());
                return new java.sql.Timestamp(date.getTime());
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter a valid date (yyyy-MM-dd HH:mm:ss).");
            }
        }
    }

    private java.sql.Timestamp getDate(Scanner console, String prompt, java.sql.Timestamp referenceDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false); // Ensure strict date validation
        while (true) {
            System.out.print(prompt);
            try {
                Date date = sdf.parse(console.nextLine());
                if (referenceDate != null && date.before(referenceDate)) {
                    System.out.println(prompt + " cannot be before " + referenceDate.toString() + ".");
                } else {
                    return new java.sql.Timestamp(date.getTime());
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter a valid date (yyyy-MM-dd HH:mm:ss).");
            }
        }
    }

    private java.sql.Timestamp getOptionalDate(Scanner console, String prompt) {
        System.out.print(prompt);
        String input = console.nextLine();
        if (input.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return new java.sql.Timestamp(sdf.parse(input).getTime());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter a valid date (yyyy-MM-dd HH:mm:ss).");
            return getOptionalDate(console, prompt);
        }
    }

    private int getInt(Scanner console, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (console.hasNextInt()) {
                int value = console.nextInt();
                console.nextLine(); // Consume newline
                return value;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                console.next(); // Consume the invalid input
            }
        }
    }

    private double getDouble(Scanner console, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (console.hasNextDouble()) {
                double value = console.nextDouble();
                console.nextLine(); // Consume newline
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Invalid input. Price cannot be negative.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                console.next(); // Consume the invalid input
            }
        }
    }

    private String getString(Scanner console, String prompt) {
        System.out.print(prompt);
        return console.nextLine();
    }

    private String getStatus(Scanner console) {
        String[] statuses = {"Shipped", "In Process", "Cancelled", "Resolved", "On Hold", "Disputed"};
        while (true) {
            System.out.println("Select Status:");
            for (int i = 0; i < statuses.length; i++) {
                System.out.println("[" + (i + 1) + "] " + statuses[i]);
            }
            System.out.print("Enter choice: ");
            if (console.hasNextInt()) {
                int choice = console.nextInt();
                console.nextLine(); // Consume newline
                if (choice > 0 && choice <= statuses.length) {
                    return statuses[choice - 1];
                }
            }
            console.nextLine(); // Consume invalid input
            System.out.println("Invalid selection. Please choose a valid status.");
        }
    }

    private int getValidCustomerNumber(Scanner console, order_transaction ot) {
        while (true) {
            int customerNumber = getInt(console, "Customer Number: ");
            if (ot.isCustomerNumberValid(customerNumber)) {
                return customerNumber;
            } else {
                System.out.println("Invalid Customer Number. Please enter a valid Customer Number.");
            }
        }
    }

    private int getValidQuantity(Scanner console, order_transaction ot, String productCode) {
        while (true) {
            int quantityOrdered = getInt(console, "Quantity Ordered: ");
            if (quantityOrdered < 0) {
                System.out.println("Invalid quantity. Quantity cannot be negative.");
                continue;
            }
            if (ot.isProductValid(productCode, quantityOrdered)) {
                return quantityOrdered;
            } else {
                System.out.println("Invalid quantity. Either product does not exist or insufficient stock.");
            }
        }
    }

    private double getValidPriceEach(Scanner console, order_transaction ot, String productCode) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");

            PreparedStatement pstmt = conn.prepareStatement("SELECT buyPrice, MSRP FROM products WHERE productCode=?");
            pstmt.setString(1, productCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double buyPrice = rs.getDouble("buyPrice");
                double MSRP = rs.getDouble("MSRP");
                pstmt.close();
                conn.close();

                while (true) {
                    System.out.print("Price Each (Buy Price: " + buyPrice + ", MSRP: " + MSRP + "): ");
                    double priceEach = getDouble(console, "Price Each: ");
                    if (priceEach >= buyPrice && priceEach <= MSRP) {
                        return priceEach;
                    } else {
                        System.out.println("Invalid price. Price Each must be between Buy Price and MSRP.");
                    }
                }
            } else {
                System.out.println("Product does not exist.");
                pstmt.close();
                conn.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0; // Invalid price
    }
}