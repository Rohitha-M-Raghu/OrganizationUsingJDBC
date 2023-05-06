package organization;

public class Department {
	private String deptId;
	private String deptName;
	
	public Department(String deptId, String deptName) {
		this.deptId = deptId;
		this.deptName = deptName;
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
