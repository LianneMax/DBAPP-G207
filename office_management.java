package G207DBAPP;
import java.sql.*;

public class office_management {
	public String officeCode;
	public String city;
	public String addressLine01;
	public String addressLine02;	
	public String state;
	public String country;
	public String territory;
	public String postalCode;
	public String phone;

	
	public office_management() {
		officeCode = "";
        city = "";
        addressLine01 = "";
        addressLine02 = "";
        state = "";
        country = "";
        territory = "";
        postalCode = "";
        phone = "";
	
	}
	
	public int createNew_office() {
		try {
			
			Connection conn;
			conn = DriverManager.getConnection ("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
			System.out.println("Connection to DB Successful");
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO offices VALUES (?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, officeCode);
            pstmt.setString(2, city);
            pstmt.setString(3, phone);
            pstmt.setString(4, addressLine01);
            pstmt.setString(5, addressLine02);
            pstmt.setString(6, state);
            pstmt.setString(7, country);
            pstmt.setString(8, postalCode);
            pstmt.setString(9, territory);
			
            pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
            return 1;
            
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public int update_office() {
		try {
			
			Connection conn;
			conn = DriverManager.getConnection ("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
			System.out.println("Connection to DB Successful");

			try (PreparedStatement CPstmt = conn.prepareStatement("SELECT city FROM offices WHERE officeCode = ?")) {
				CPstmt.setString(1, officeCode);
                try (ResultSet rs = CPstmt.executeQuery()) {
                 if (rs.next()) {
                    String city = rs.getString("city");
                    if ("CLOSED".equals(city)) {
                        System.out.println("Error: Cannot update a closed office."); 
                        return 0;
                      }
                 }
               }
			}
			
			PreparedStatement pstmt = conn.prepareStatement("UPDATE offices SET city = ?, phone = ?, addressLine1 = ?, addressLine2 = ?, state = ?, country = ?, postalCode = ?, territory = ? WHERE officeCode = ? ");
			pstmt.setString(1, city);
            pstmt.setString(2, phone);
            pstmt.setString(3, addressLine01);
            pstmt.setString(4, addressLine02);
            pstmt.setString(5, state);
            pstmt.setString(6, country);
            pstmt.setString(7, postalCode);
            pstmt.setString(8, territory);
            pstmt.setString(9, officeCode);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
    		return 1;	
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}

	}
	public int delete_office() {
		try {
			
			Connection conn;
			conn = DriverManager.getConnection ("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
			System.out.println("Connection to DB Successful");
			
			try (PreparedStatement CPstmt = conn.prepareStatement("SELECT city FROM offices WHERE officeCode = ?")) {
					CPstmt.setString(1, officeCode);
	                try (ResultSet rs = CPstmt.executeQuery()) {
	                 if (rs.next()) {
	                    String city = rs.getString("city");
	                    if ("CLOSED".equals(city)) {
	                        System.out.println("Error: Cannot delete a closed office."); 
	                        return 0;
	                      }
	                 }
	               }
	        }
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM offices WHERE officeCode = ? ");
			pstmt.setString(1, officeCode);
			
            pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
    		return 1;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}

	}
	public int close_office() {
	  try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
            System.out.println("Connection to DB Successful");

            String SFOfficeCode = null;
            PreparedStatement findSFPstmt = conn.prepareStatement("SELECT officeCode FROM offices WHERE city = ?");
            findSFPstmt.setString(1, "San Francisco");
            ResultSet rs = findSFPstmt.executeQuery();

            if (rs.next()) {
                SFOfficeCode = rs.getString("officeCode");
            } else {
                System.out.println("San Francisco office not found.");
                findSFPstmt.close();
                conn.close();
                return 0;
            }
            findSFPstmt.close();

            PreparedStatement reassignPstmt = conn.prepareStatement("UPDATE employees SET officeCode = ? WHERE officeCode = ?");
            reassignPstmt.setString(1, SFOfficeCode);
            reassignPstmt.setString(2, officeCode);
            reassignPstmt.executeUpdate();
            reassignPstmt.close();

            PreparedStatement closePstmt = conn.prepareStatement("UPDATE offices SET city = 'CLOSED' WHERE officeCode = ?");
            closePstmt.setString(1, officeCode);
            closePstmt.executeUpdate();
            ///closePstmt.close();

            conn.close();
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
	}
	
	public int get_office() {
		try {

			Connection conn;
			conn = DriverManager.getConnection ("jdbc:mysql://mysql-176128-0.cloudclusters.net:10107/dbsales?useTimezone=true&serverTimezone=UTC&user=CCINFOM_G207&password=DLSU1234");
			System.out.println("Connection to DB Successful");
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM offices WHERE officeCOde = ? ");
			pstmt.setString(1, officeCode);
			
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	city = rs.getString("city");
                phone = rs.getString("phone");
                addressLine01 = rs.getString("addressLine1");
                addressLine02 = rs.getString("addressLine2");
                state = rs.getString("state");
                country = rs.getString("country");
                postalCode = rs.getString("postalCode");
                territory = rs.getString("territory");
            }
            
            pstmt.close();
            conn.close();
    		return 1;			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}

	}
	
	
}
