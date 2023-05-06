package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.DepartmentNotFound;
import organization.Department;
import organization.Employee;

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
	
	static {
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
	
	public static void addEmployeeData(String empName, String deptID, float salary) {
		try {
			cstmt = conn.prepareCall("{CALL insert_employee(?, ?, ?)}");
			cstmt.setString(1, empName);
			cstmt.setString(2, deptID);
			cstmt.setFloat(3, salary);
			try {
				cstmt.executeUpdate();
			}catch (SQLException e) {
				throw new DepartmentNotFound();
			}
			System.out.println("New Employee Added to Employee Table...");
			
		}catch (DepartmentNotFound de) {
			System.err.println(de.getMessage());
		}
		catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}
	
	public static List<Department> getDepartmentData() {
		try {
			query = "SELECT * FROM Department";
			res = stmt.executeQuery(query);
			List<Department> departmentData = new ArrayList<>();
			while(res.next()) {
				String deptId = res.getString("deptID");
				String deptName = res.getString("deptName");
				String deptHead = res.getString("deptHead");
				int noOfEmployees = res.getInt("no_of_employees");
				departmentData.add(new Department(deptId, deptName, deptHead, noOfEmployees));
			}
			return departmentData;
		}catch (SQLException se) {
			se.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	public static List<Employee> getEmployeeData(){
		try {
			query = "SELECT * FROM Employee";
			res = stmt.executeQuery(query);
			List<Employee> employeeData = new ArrayList<>();
			while(res.next()) {
				Integer empId = res.getInt("empID");
				String empName = res.getString("empName");
				String deptId = res.getString("deptID");
				float salary = res.getFloat("salary");
				Integer managerId = res.getInt("managerID");
				employeeData.add(new Employee(empId, empName, deptId, salary, managerId));
			}
			return employeeData;
		}catch (SQLException se) {
			System.err.println(se.getMessage());
		}
		return Collections.emptyList();
	}
	
	public static boolean setHeadOfDepartment(String deptId, String headName) {
		try {
			query = "UPDATE Department SET deptHead = ? WHERE deptID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, headName);
			pstmt.setString(2, deptId);
			pstmt.executeUpdate();
			return true;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public static boolean isEmployeeExists(String name) {
	    try {
	        query = "SELECT COUNT(*) FROM Employee WHERE empName = ?";
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, name);
	        res = pstmt.executeQuery();
	        if (res.next()) {
	            int count = res.getInt(1);
	            return count > 0;
	        }
	    } catch (SQLException se) {
	        System.out.println(se.getMessage());
	    }
	    return false;
	}

	
	
	
}
