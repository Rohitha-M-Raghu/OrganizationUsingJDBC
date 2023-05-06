package organization;

public class Department {
	private String deptId;
	private String deptName;
	private String deptHead;
	private int noOfEmployees;
	
	public Department(String deptId, String deptName, String deptHead, int noOFEmployees) {
		this.deptId = deptId;
		this.deptName = deptName;
		this.deptHead = deptHead;
		this.noOfEmployees = noOFEmployees;
	}
	
	public Department(String deptId, String deptName) {
		this.deptId = deptId;
		this.deptName = deptName;
	}
	public String getDeptHead() {
		return deptHead;
	}
	public void setDeptHead(String deptHead) {
		this.deptHead = deptHead;
	}
	public int getNoOfEmployees() {
		return noOfEmployees;
	}
	public void setNoOfEmployees(int noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
