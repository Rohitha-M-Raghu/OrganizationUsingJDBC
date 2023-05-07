package ui;

import java.util.Scanner;

import database.EmployeeColumn;
import database.OrganizationDataBase;
import organization.OrganizationTree;

public class MainMenu {
	private static OrganizationTree organizationTree = new OrganizationTree();
	private static Scanner scanner = new Scanner(System.in);
	private static EmployeeColumn empname = EmployeeColumn.EMPNAME;
	private static EmployeeColumn salary = EmployeeColumn.SALARY;

	public static void main(String[] args) {
		//new OrganizationDataBase();
		int choice = 0;
		while(choice != 8) {
			displayMenu();
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			try {
				switch(choice) {
				case 1:
					addEmployee();
					break;
				case 2:
					editEmployeeData();
					break;
				case 3:
					displayAllEmployeeDetails();
					break;
				case 4:
					createDepartment();
					break;
				case 5:
					displayAllDepartmentDetails();
					break;
				case 6:
					assignHOD();
					break;
				case 7:
					salaryUpraisal();
					break;
				case 8:
					organizationTree.closeDatabase();
					System.out.println("Exiting Application...");
					break;
				default:
					System.out.println("Wrong Input... Try Again");
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
	
	public static void displayMenu() {
		System.out.println("\nMENU");
		System.out.println("================");
		System.out.println("1. Add Employee");
		System.out.println("2. Edit Employee Data");
		System.out.println("3. Display All Employee Details");
		System.out.println("4. Create New Department");
		System.out.println("5. Display All Department Details");
		System.out.println("6. Assign Head Of Department");
		System.out.println("7. Salary Upraisal for Department");
		System.out.println("8. Exit");
		System.out.println("");
		
	}
	
	public static void addEmployee() {
		scanner.nextLine(); // consume the remaining newline character
		System.out.print("Enter Employee Name: ");
		String name = scanner.nextLine();
		System.out.print("Enter Department ID: ");
		String deptId = scanner.nextLine();
		System.out.print("Enter Salary: ");
		float salary = scanner.nextFloat();
		if(OrganizationDataBase.isDepartmentExists(deptId)) {
			organizationTree.addEmployee(name, deptId, salary);
		}
	}
	
	
	public static void createDepartment() {
		System.out.print("DepartmentID: ");
		String deptID = scanner.next();
		scanner.nextLine(); // consume the remaining newline character
		System.out.print("Department Name: ");
		String deptName = scanner.nextLine();
		organizationTree.addDepartment(deptID, deptName);
	}
	
	public static void displayAllDepartmentDetails() {
		organizationTree.displayAllDepartmentData();
	}
	
	public static void displayAllEmployeeDetails() {
		organizationTree.displayAllEmployeeData();
	}
	
	public static void assignHOD() {
		System.out.print("Enter Department ID: ");
		String deptId = scanner.next();
		scanner.nextLine();
		System.out.print("Enter HOD name: ");
		String headName = scanner.nextLine();
		organizationTree.assignHeadOfDepartment(deptId, headName);
	}
	
	public static void editEmployeeData() {
		System.out.print("Emp ID: ");
		Integer empID = scanner.nextInt();
		System.out.print("ColumnName(ename/esalary): ");
		String column = scanner.next();
		if(column.equalsIgnoreCase("ename")) {
			organizationTree.modifyEmployeeData(empID, empname);
		}
		else if(column.equalsIgnoreCase("esalary")) {
			organizationTree.modifyEmployeeData(empID, salary);
		}
		else {
			System.err.println("Invalid Employee Data Field...");
			System.err.println("TRY AGAIN...");
		}
	}
	
	public static void salaryUpraisal() {
		System.out.print("Enter Department ID: ");
		String deptId = scanner.next();
		System.out.print("Enter Percentage Upraisal(in %): ");
		float upgradePercentage = scanner.nextFloat();
		organizationTree.departmentSalaryUpgrade(deptId, upgradePercentage);
	}
}
