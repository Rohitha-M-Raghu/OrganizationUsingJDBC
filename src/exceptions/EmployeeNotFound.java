package exceptions;

public class EmployeeNotFound extends Exception{
	public EmployeeNotFound() {
		super("Invalid EMP ID!!!! Employee does not exist....");
	}
	
	public EmployeeNotFound(Integer empId) {
		super("Employee with empID " +  empId +" does not exist....");
	}
}
