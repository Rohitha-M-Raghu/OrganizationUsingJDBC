package exceptions;

public class DepartmentNotFound extends Exception{
	public DepartmentNotFound() {
		super("Department does not exist....");
	}
	
	public DepartmentNotFound(String deptId) {
		super("Department " +  deptId +" does not exist....");
	}
}
