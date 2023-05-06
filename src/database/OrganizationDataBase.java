package database;

import java.sql.*;

public class OrganizationDataBase {
	private static String url = "jdbc:mysql://localhost:3306/Organization";
	private static String userName = "root";
	private static String password = "";
	private static String query = "";
	private static PreparedStatement pstmt = null;
	private static CallableStatement cstmt = null;
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet res = null;
	
	public OrganizationDataBase() {
		try {
			//Registering JDBC 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Creating Connection to Database
			conn = DriverManager.getConnection(url, userName, password);
			
			//Creating Statement
			stmt = conn.createStatement();
			
			System.out.println("Database Connection Successfully Established...");
			truncateTables();
			
		}catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Exception in Truncate");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in Truncate");
		}
	}
	
	public static void truncateTables() {
	    try {
	    	// Disable foreign key checks
	        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
	        
	        Statement stmt = conn.createStatement();
	        String[] tables = {"Employee", "Manager", "Department"};
	        for (String table : tables) {
	            String query = "TRUNCATE TABLE " + table;
	            stmt.executeUpdate(query);
	        }
	        // Enable foreign key checks
	        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=1");
	        System.out.println("Database Cleared Successfully...");
	    } catch (SQLException se) {
	        se.printStackTrace();
	    }
	}

	public static void addDepartmentData(String deptID, String deptName) {
		try {
			cstmt = conn.prepareCall("{CALL insert_department(?, ?)}");
			cstmt.setString(1, deptID);
			cstmt.setString(2, deptName);
			cstmt.executeUpdate();
			System.out.println("New Department Added to Department Table...");
		}catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	public static void addEmployeeData() {
		
	}
	
	public void createTable(String tableName) {
		try {
			pstmt = conn.prepareStatement("CREATE TABLE ?");
			pstmt.setString(1, tableName);
			pstmt.executeUpdate();
			System.out.println("Creating Table " + tableName);
			System.out.println("");
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
}
