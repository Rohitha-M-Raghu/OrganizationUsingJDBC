package database;

public interface DatabaseManipulation {
	public void truncateTables();
	public void addDepartmentData(String deptID, String deptName);
	public Integer addEmployeeData(String empName, String deptID, float salary);
	public boolean setHeadOfDepartment(String deptId, String headName);
	public void updateEmployeeSalary(Integer empId, float salary);
	public void updateEmployeeName(Integer empId, String newName);
	public void departmentSalaryUpraisal(String deptId, float salaryUpgradePercentage);
	
}
