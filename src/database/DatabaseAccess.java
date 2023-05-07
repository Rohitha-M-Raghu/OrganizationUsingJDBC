package database;

import java.util.List;

import organization.Department;
import organization.Employee;

public interface DatabaseAccess {
	public Integer getEmployeeID(String empName);
	public List<Department> getAllDepartmentData();
	public List<Employee> getAllEmployeeData();
	public boolean isEmployeeExists(String name);
	public boolean isDepartmentExists(String deptId);
	public void closeResources();
	
}
