package organization;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private static int noOfEmployees = 0;
	private Integer empId;
	private String empName;
	private String deptId;
	private float salary;
	private List<Integer> subordinatesId;
	private Integer managerId;
	
	public Employee(String name, String deptId, float salary) {
		noOfEmployees ++;
		empId = noOfEmployees;
		this.empName = name;
		this.deptId = deptId;
		this.salary = salary;
		this.managerId = 0;
		this.subordinatesId = new ArrayList<>();
	}
	
	public Employee(Integer empId, String empName, String deptId, float salary, Integer managerId) {
		this.empId = empId;
		this.empName = empName;
		this.deptId = deptId;
		this.salary = salary;
		this.managerId = managerId;
	}
	
	public static int getNoOfEmployees() {
		return noOfEmployees;
	}
	public static void setNoOfEmployees(int noOfEmployees) {
		Employee.noOfEmployees = noOfEmployees;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public List<Integer> getSubordinatesID() {
		return subordinatesId;
	}
	public void setSubordinatesID(List<Integer> subordinatesID) {
		this.subordinatesId = subordinatesID;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	
	
}
