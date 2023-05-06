package organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.OrganizationDataBase;

public class OrganizationTree {
	private String topEmployee;
	private Map<Integer, String> employee;
	private Map<Integer, List<Integer>> manager;
	private OrganizationDataBase organizationDB = new OrganizationDataBase();
	
	public OrganizationTree() {
		topEmployee = "noname";
		employee = new HashMap<>();
		manager = new HashMap<>();
	}
	
	public void addEmployee(String name, String deptId, float salary) {
		Employee emp = new Employee(name, deptId, salary);
	}
	
	public void addDepartment(String deptId, String deptName) {
		Department dept = new Department(deptId, deptName); // is this necessary
		OrganizationDataBase.addDepartmentData(dept.getDeptId(), dept.getDeptName());
	}
}
