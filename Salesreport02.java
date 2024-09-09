package G207DBAPP;

import java.sql.*;

public class Salesreport02 {
    private int month;
    private int year;

    public Salesreport02() {
        month = 0;
        year = 0;
    }

    public void setDate(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public void generateReport() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234"
            );
            System.out.println("Connection to DB Successful");

            //sales per product for each day
            pstmt = conn.prepareStatement("SELECT p.productName, DAY(o.orderDate) AS day, SUM(od.quantityOrdered * od.priceEach) AS dailySales " +
                    "FROM orderdetails od " +
                    "JOIN orders o ON od.orderNumber = o.orderNumber " +
                    "JOIN products p ON od.productCode = p.productCode " +
                    "WHERE MONTH(o.orderDate) = ? AND YEAR(o.orderDate) = ? " +
                    "GROUP BY p.productName, DAY(o.orderDate) " +
                    "ORDER BY p.productName, DAY(o.orderDate)");
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);

            rs = pstmt.executeQuery();

            System.out.println("Sales Report for " + month + "/" + year);
            System.out.println("------------------------------------------------");
            System.out.println("Product | Day | Sales");

            double totalSales = 0;

            boolean hasResults = false;
            while (rs.next()) {
                String productName = rs.getString("productName");
                int day = rs.getInt("day");
                double dailySales = rs.getDouble("dailySales");

                System.out.println(productName + " | " + day + " | $" + String.format("%.2f", dailySales));
                totalSales += dailySales;
                hasResults = true;
            }

            if (!hasResults) {
                System.out.println("[No sales data found for the given month and year]");
            }

            System.out.println("------------------------------------------------");
            System.out.println("Total Sales: $" + String.format("%.2f", totalSales));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}