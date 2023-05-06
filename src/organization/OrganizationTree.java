package organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.OrganizationDataBase;
import exceptions.DepartmentNotFound;

public class OrganizationTree {
	private String topEmployee;
	private Map<Integer, String> employee;
	private Map<Integer, List<Integer>> manager;
	//private OrganizationDataBase organizationDB = new OrganizationDataBase();
	
	public OrganizationTree() {
		new OrganizationDataBase();
		topEmployee = "noname";
		employee = new HashMap<>();
		manager = new HashMap<>();
	}
	
	public void addEmployee(String name, String deptId, float salary) {
		Employee emp = new Employee(name, deptId, salary);
		OrganizationDataBase.addEmployeeData(name, deptId, salary);
	}
	
	public void addDepartment(String deptId, String deptName) {
		Department dept = new Department(deptId, deptName); // is this necessary
		OrganizationDataBase.addDepartmentData(dept.getDeptId(), dept.getDeptName());
	}
	
	
	public void displayDepartmentData() {
		System.out.println("DEPARTMENT DATA");
		System.out.println("DEPARTMENT ID \t\tDEPARTMENT NAME \t\tHEAD OF DEPARTMENT\t\tNO OF EMPLOYEES");
		System.out.print("----------------------------------------------------");
		System.out.println("---------------------------------------------------");
		List<Department> departmentData = OrganizationDataBase.getDepartmentData();
		if(departmentData.isEmpty()) {
			System.err.println("Empty Data Set.....");
			return;
		}
		for(Department dept:departmentData) {
			System.out.print(dept.getDeptId() + "\t\t\t");
			System.out.print(dept.getDeptName() + "\t\t\t");
			System.out.print(dept.getDeptHead() + "\t\t\t");
			System.out.println(dept.getNoOfEmployees());
		}
		
	}
	
	public void displayEmployeeData() {
		System.out.println("EMPLOYEE DATA");
		System.out.println("EMP ID\t\tEMP NAME\t\tDEPT ID\t\tSALARY\t\tMANAGER ID");
		System.out.print("---------------------------------------------");
		System.out.println("-------------------------------------");
		List<Employee> employeeData = OrganizationDataBase.getEmployeeData();
		if(employeeData.isEmpty()) {
			System.err.println("Emplty Data Set.....");
			return;
		}
		for(Employee emp:employeeData) {
			System.out.print(emp.getEmpId() + "\t\t");
			System.out.print(emp.getEmpName() + "\t\t");
			System.out.print(emp.getDeptId() + "\t\t");
			System.out.print(emp.getSalary() + "\t\t");
			if(emp.getManagerId() == 0) {
				System.out.println("Not Assigned");
			}
			else {
				System.out.println(emp.getManagerId());
			}
		}
	}
	
	public void assignHeadOfDepartment(String deptId, String headName) {
		//checking whether HOD is a registered emp or not
		if(!OrganizationDataBase.isEmployeeExists(headName)) {
			System.out.println(headName + "is not an employee of this organization");
			System.out.println("TRY AGAIN...");
		}
		else {
			if(OrganizationDataBase.setHeadOfDepartment(deptId, headName)) {
				System.out.println("Successfully Updated...");
			}
			else {
				System.out.println("Deptartment ID doesn't exist");
			}
		}
	}
	
	public void closeDatabase() {
		OrganizationDataBase.closeResources();
	}
	
}
