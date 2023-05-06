package ui;

import java.util.Scanner;

import database.OrganizationDataBase;
import organization.OrganizationTree;

public class MainMenu {
	private static OrganizationTree organizationTree = new OrganizationTree();
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		new OrganizationDataBase();
		int choice = 0;
		while(choice != 6) {
			displayMenu();
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			try {
				switch(choice) {
				case 1:
					addEmployee();
					break;
				case 2:
					displayEmployeeDetails();
					break;
				case 3:
					createDepartment();
					break;
				case 4:
					displayDepartmentDetails();
					break;
				case 5:
					assignHOD();
					break;
				case 6:
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
		System.out.println("MENU");
		System.out.println("================");
		System.out.println("1. Add Employee");
		System.out.println("2. Display EmployeeDetails");
		System.out.println("3. Create New Department");
		System.out.println("4. Display Department Details");
		System.out.println("5. Assign Head Of Department");
		System.out.println("6. Exit");
	}
	
	public static void addEmployee() {
		scanner.nextLine(); // consume the remaining newline character
		System.out.print("Enter Employee Name: ");
		String name = scanner.nextLine();
		System.out.print("Enter Department ID: ");
		String deptId = scanner.nextLine();
		System.out.print("Enter Salary: ");
		float salary = scanner.nextFloat();
		organizationTree.addEmployee(name, deptId, salary);
	}
	
	
	public static void createDepartment() {
		System.out.print("DepartmentID: ");
		String deptID = scanner.next();
		scanner.nextLine(); // consume the remaining newline character
		System.out.print("Department Name: ");
		String deptName = scanner.nextLine();
		organizationTree.addDepartment(deptID, deptName);
	}
	
	public static void displayDepartmentDetails() {
		organizationTree.displayDepartmentData();
	}
	
	public static void displayEmployeeDetails() {
		organizationTree.displayEmployeeData();
	}
	
	public static void assignHOD() {
		System.out.print("Enter Department ID: ");
		String deptId = scanner.next();
		scanner.nextLine();
		System.out.print("Enter HOD name: ");
		String headName = scanner.nextLine();
		organizationTree.assignHeadOfDepartment(deptId, headName);
	}
}
