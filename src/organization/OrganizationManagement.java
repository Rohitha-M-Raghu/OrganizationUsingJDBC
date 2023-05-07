package organization;

public interface OrganizationManagement {
	public void addEmployee(String name, String deptId, float salary);
	public void addDepartment(String deptId, String deptName);
	public void displayAllDepartmentData();
	public void displayAllEmployeeData();
	public void assignHeadOfDepartment(String deptId, String headName);
	public void closeDatabase();
	public void departmentSalaryUpgrade(String deptId, float upgradePercentage);
	
}
