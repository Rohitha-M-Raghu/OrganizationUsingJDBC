package database;

import organization.Department;
import organization.Employee;

public interface DatabaseManipulation {
	public void truncateTables();
	public void addDepartmentData(Department dept);
	public Integer addEmployeeData(Employee emp);
	public boolean setHeadOfDepartment(String deptId, String headName);
	public void updateEmployeeSalary(Integer empId, float salary);
	public void updateEmployeeName(Integer empId, String newName);
	public void departmentSalaryUpraisal(String deptId, float salaryUpgradePercentage);
	
}
