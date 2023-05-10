package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.DepartmentNotFound;
import organization.Department;
import organization.Employee;

public class OrganizationDataBase implements DatabaseAccess, DatabaseManipulation{
	private final String url = "jdbc:mysql://localhost:3306/Organization";
	private final String userName = "root";
	private final String password = "";
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
	
	@Override
	public void truncateTables() {
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
	
	public void addDepartmentData(Department dept) {
		try {
			cstmt = conn.prepareCall("{CALL insert_department(?, ?)}");
			cstmt.setString(1, dept.getDeptId());
			cstmt.setString(2, dept.getDeptName());
			cstmt.executeUpdate();
			System.out.println("New Department Added to Department Table...");
		}catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	public Integer addEmployeeData(Employee emp) {
		try {
			cstmt = conn.prepareCall("{CALL insert_employee(?, ?, ?)}");
			cstmt.setString(1, emp.getEmpName());
			cstmt.setString(2, emp.getDeptId());
			cstmt.setFloat(3, emp.getSalary());
			try {
				cstmt.executeUpdate();
			}catch (SQLException e) {
				throw new DepartmentNotFound();
			}
			System.out.println("New Employee Added to Employee Table...");
			return getEmployeeID(emp.getEmpName());
		}catch (DepartmentNotFound de) {
			System.err.println(de.getMessage());
//			de.printStackTrace();
//			System.out.println("EXCEPTION IN addEmployeeData");
		}
		catch (SQLException se) {
			System.err.println(se.getMessage());
//			se.printStackTrace();
//			System.out.println("EXCEPTION IN addEmployeeData");
		}
		return -1;
	}
	
	public Employee getEmployeeData(Integer empID) {
		query = "SELECT * FROM Employee WHERE empID = ?";
		Employee emp = new Employee();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empID);
			res = pstmt.executeQuery();
			if(res.next()) {
				emp.setEmpId(res.getInt("empID"));
				emp.setEmpName(res.getString("empName"));
				emp.setDeptId(res.getString("deptID"));
				emp.setSalary(res.getFloat("salary"));
				emp.setManagerId(res.getInt("managerID"));
			}
			return emp;
		}catch (SQLException se) {
			se.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Integer getEmployeeID(String empName) {
	    query = "SELECT empID FROM Employee WHERE empName = ?";
	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, empName);
	        ResultSet res = pstmt.executeQuery();
	        if(res.next()) {
	            return res.getInt(1);
	        }
	    } catch (SQLException se) {
	        se.printStackTrace();
	        System.out.println("EXCEPTION IN getEmployeeID");
	    }
	    return null;
	}
	
	@Override
	public List<Department> getAllDepartmentData() {
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
	
	@Override
	public List<Employee> getAllEmployeeData(){
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
	
	@Override
	public boolean setHeadOfDepartment(String deptId, String headName) {
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
	
	@Override
	public void updateEmployeeSalary(Integer empId, float salary) {
		query = "UPDATE Employee SET salary = ? WHERE empID = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setFloat(1, salary);
			pstmt.setInt(2, empId);
			pstmt.executeUpdate();
			System.out.println("Successfully Updated Employee Salary to " + salary);
		}catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	@Override
	public void updateEmployeeName(Integer empId, String newName) {
		query = "UPDATE Employee SET empName = ? WHERE empID = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newName);
			pstmt.setInt(2, empId);
			pstmt.executeUpdate();
			System.out.println("Successfully Updated Employee Name to " + newName);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	@Override
	public void departmentSalaryUpraisal(String deptId, float salaryUpgradePercentage) {
	    query = "SELECT * FROM Employee WHERE deptID = ?";
	    try {
	        pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	        pstmt.setString(1, deptId);
	        res = pstmt.executeQuery();
	        while(res.next()) {
	            float salary = res.getFloat("salary");
	            salary = (float) (salary * (1 + salaryUpgradePercentage*0.01));
	            res.updateFloat("salary", salary);
	            res.updateRow(); //changes saved to Database
	        }
	        System.out.println("Salaries of Employees in Department " + deptId + " updated successfully...");
	    } catch (SQLException se) {
	        System.err.println(se.getMessage());
	    }
	}

	@Override
	public boolean isEmployeeExists(String name) {
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
//	    	System.out.println("EXCEPTION IN isEmployeeExists");
	        System.out.println(se.getMessage());
	    }
	    return false;
	}
	
	@Override
	public boolean isDepartmentExists(String deptId) {
	    try {
	        query = "SELECT COUNT(*) FROM Department WHERE deptID = ?";
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, deptId);
	        res = pstmt.executeQuery();
	        if (res.next()) {
	            int count = res.getInt(1);
	            return count > 0;
	        }
	    } catch (SQLException se) {
//	    	System.out.println("EXCEPTION IN isEmployeeExists");
	        System.out.println(se.getMessage());
	    }
	    return false;
	}
	
	@Override
	public int deleteEmployeeData(int empId) {
		query = "DELETE FROM Employee WHERE empID = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empId);
			return pstmt.executeUpdate();
			
		}catch (SQLException se) {
			System.err.println(se.getMessage());
			System.err.println("TRY AGAIN...");
		}
		return 0;
	}

	@Override
	public void closeResources() {
		System.out.println("Closing all Resources...");
		try {
			if(res != null) {
				res.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(cstmt != null) {
				cstmt.close();
			}
			if(conn != null) {
				conn.close();
			}
			System.out.println("Database Disconnected...");
		}catch (SQLException se) {
			se.printStackTrace();
		}
	}
}
