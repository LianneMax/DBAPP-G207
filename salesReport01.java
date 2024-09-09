package G207DBAPP;

import java.sql.*;

public class salesReport01 {
    private String productCode;
    private String productName;
    private String productLine;
    private String productScale;
    private String productVendor;
    private String productDescription;
    private int quantityInStock;
    private float buyPrice;
    private float MSRP;

    public salesReport01() {
        productCode = "";
        productName = "";
        productLine = "";
        productScale = "";
        productVendor = "";
        productDescription = "";
        quantityInStock = 0;
        buyPrice = 0;
        MSRP = 0;
    }

    public void generateSalesReport(int month, int year) {
        Connection conn = null;
        try {
            // Establish database connection
            conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");

            String sql = "SELECT DATE(orderDate) AS date, status, SUM(priceEach * quantityOrdered) AS totalSales " +
                         "FROM orders o " +
                         "JOIN orderdetails od ON o.orderNumber = od.orderNumber " +
                         "WHERE MONTH(orderDate) = ? AND YEAR(orderDate) = ? " +
                         "GROUP BY DATE(orderDate), status";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            ResultSet rs = pstmt.executeQuery();

            System.out.printf("%-15s %-15s %-15s%n", "Date", "Status", "Total Sales");
            double totalSales = 0;
            while (rs.next()) {
                String date = rs.getString("date");
                String status = rs.getString("status");
                double sales = rs.getDouble("totalSales");
                totalSales += sales;
                System.out.printf("%-15s %-15s %-15.2f%n", date, status, sales);
            }
            System.out.printf("%nTotal Sales: %.2f%n", totalSales);

        } catch (SQLException e) {
            System.out.println("Error generating sales report: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
