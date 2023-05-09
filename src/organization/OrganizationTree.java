package organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import database.EmployeeColumn;
import database.OrganizationDataBase;
import exceptions.DepartmentNotFound;
import exceptions.EmployeeNotFound;

public class OrganizationTree implements OrganizationManagement{
	private String topEmployee;
	private Map<Integer, String> employeeMap; // empID mapped to empName
	private Map<Integer, List<Integer>> managerMap; // managerID mapped to subordinateIDs
	private OrganizationDataBase organizationDB;
	private static Scanner scanner = new Scanner(System.in);
		
	public OrganizationTree() {
		organizationDB = new OrganizationDataBase();
		topEmployee = "noname";
		employeeMap = new HashMap<>();
		managerMap = new HashMap<>();
	}
	
	@Override
	public void addEmployee(String name, String deptId, float salary) {
		Employee emp = new Employee(name, deptId, salary);
		emp.setEmpId(organizationDB.addEmployeeData(emp));
		if(emp.getEmpId()!= null) {
			if(topEmployee.equals("noname")) {
				topEmployee = name;
			}
			employeeMap.put(emp.getEmpId(), name);
		}
//		displayEmployeeMap(); //remove
	}
	
	//checking function
	public void displayEmployeeMap() {
		System.out.println("EMPID\t\tEMPNAME");
		System.out.println("------------------------");
		employeeMap.forEach((empID, empName) ->{
			System.out.println(empID + "\t\t" + empName);
		});
	}
	
	@Override
	public void addDepartment(String deptId, String deptName) {
		Department dept = new Department(deptId, deptName); // is this necessary
		organizationDB.addDepartmentData(dept);
	}
	
	@Override
	public void findEmployeeData(int empId) {
		Employee emp;
		emp = organizationDB.getEmployeeData(empId);
		try {
			if(emp == null) {
				throw new EmployeeNotFound();
			}
			else {
				System.out.println("Employee Data");
				System.out.println("-------------------");
				System.out.println("Emp ID: " + emp.getEmpId());
				System.out.println("Emp Name: " + emp.getEmpName());
				System.out.print("Dept ID: ");
				if(emp.getDeptId() == null) {
					System.out.println("Not assigned");
				}
				else {
					System.out.println(emp.getDeptId());
				}
				System.out.println("Salary: " + emp.getSalary());
				System.out.print("Manager ID: ");
				if(emp.getManagerId() == 0) {
					System.out.println("Not assigned");
				}
				else {
					System.out.println(emp.getManagerId());
				}
			}
		}catch (EmployeeNotFound e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public void displayAllDepartmentData() {
		System.out.println("DEPARTMENT DATA");
		System.out.println("DEPARTMENT ID \t\tDEPARTMENT NAME \t\tHEAD OF DEPARTMENT\t\tNO OF EMPLOYEES");
		System.out.print("----------------------------------------------------");
		System.out.println("---------------------------------------------------");
		List<Department> departmentData = organizationDB.getAllDepartmentData();
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
	
	@Override
	public void displayAllEmployeeData() {
		System.out.println("EMPLOYEE DATA");
		System.out.println("EMP ID\t\tEMP NAME\t\tDEPT ID\t\tSALARY\t\tMANAGER ID");
		System.out.print("---------------------------------------------");
		System.out.println("-------------------------------------");
		List<Employee> employeeData = organizationDB.getAllEmployeeData();
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
	
	@Override
	public void assignHeadOfDepartment(String deptId, String headName) {
		//checking whether HOD is a registered emp or not
		if(!organizationDB.isEmployeeExists(headName)) {
			System.out.println(headName + "is not an employee of this organization");
			System.out.println("TRY AGAIN...");
		}
		else {
			if(organizationDB.setHeadOfDepartment(deptId, headName)) {
				System.out.println("Successfully Updated...");
			}
			else {
				System.out.println("Deptartment ID doesn't exist");
			}
		}
	}
	
	@Override
	public void closeDatabase() {
		organizationDB.closeResources();
	}
	
	public void modifyEmployeeData(Integer empId, EmployeeColumn columnName) {
		try {
			if(!employeeMap.containsKey(empId)) {
				throw new EmployeeNotFound(empId);
			}
			else {
				if(columnName.equals(EmployeeColumn.EMPNAME)) {
					System.out.print("Enter Employee Name(for updation): ");
					String name = scanner.nextLine();
					organizationDB.updateEmployeeName(empId, name);
					employeeMap.put(empId, name);
				}
				else {
					System.out.print("Enter new Salary: ");
					float salary = scanner.nextFloat();
					organizationDB.updateEmployeeSalary(empId, salary);
				}
			}
		}catch (EmployeeNotFound e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void departmentSalaryUpgrade(String deptId, float upgradePercentage) {
		try {
			if(!organizationDB.isDepartmentExists(deptId)) {
				throw new DepartmentNotFound(deptId);
			}
			else {
				organizationDB.departmentSalaryUpraisal(deptId, upgradePercentage);
			}
		}catch (DepartmentNotFound de) {
			System.err.println(de.getMessage());
		}
		
	}
	
	public boolean checkValidDeptID(String deptId) {
		return organizationDB.isDepartmentExists(deptId);
	}

	
}
