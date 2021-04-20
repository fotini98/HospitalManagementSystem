package com.ikubinfo.primefaces.model;

public class Employee {
	
	/*
	 * CREATE TABLE employee ( employee_id serial PRIMARY KEY, person_id int,
	 * role_id int, CONSTRAINT person_id FOREIGN KEY (person_id) REFERENCES
	 * person(person_id), CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES
	 * role(role_id))
	 */
	private long employeeId;
	private long patientId;
	private long roleId;
	private long departmentId;
	
	
	public Employee() {
		super();
	}
	public Employee(long patientId, long roleId) {
		super();
		this.patientId = patientId;
		this.roleId = roleId;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", patientId=" + patientId + ", roleId=" + roleId
				+ ", departmentId=" + departmentId + "]";
	}
	
	



}
