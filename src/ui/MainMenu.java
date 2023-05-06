package ui;

import java.util.Scanner;

import organization.OrganizationTree;

public class MainMenu {
	private static OrganizationTree organizationTree = new OrganizationTree();
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		int choice = 0;
		while(choice != 5) {
			displayMenu();
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			try {
				switch(choice) {
				case 1:
					addEmployee();
					break;
				case 2:
					displayEmployeeData();
					break;
				case 3:
					createDepartment();
					break;
				case 4:
					displayDepartmentDetails();
					break;
				case 5:
					System.out.println("Exiting Application...");
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
	}
	
	public static void addEmployee() {
		System.out.print("Enter Employee Name: ");
		String name = scanner.next();
		System.out.println("Enter Department Name: ");
		String deptId = scanner.next();
		System.out.println("Enter Salary: ");
		float salary = scanner.nextFloat();
		organizationTree.addEmployee(name, deptId, salary);
	}
	
	public static void displayEmployeeData() {
		
	}
	
	public static void createDepartment() {
		System.out.print("DepartmentID: ");
		String deptID = scanner.next();
		System.out.print("Department Name: ");
		String deptName = scanner.next();
		organizationTree.addDepartment(deptID, deptName);
	}
	
	public static void displayDepartmentDetails() {
		//TODO
	}
}
