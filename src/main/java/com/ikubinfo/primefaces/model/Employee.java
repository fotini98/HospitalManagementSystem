package com.ikubinfo.primefaces.model;

public class Employee extends Person{
	
	/*
	 * CREATE TABLE employee ( employee_id serial PRIMARY KEY, person_id int,
	 * role_id int, CONSTRAINT person_id FOREIGN KEY (person_id) REFERENCES
	 * person(person_id), CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES
	 * role(role_id))
	 */
	private long employeeId;
	private long roleId;
	private long departmentId;
	private Role role;
	private Department department;
	

	
	public Employee() {
		super();
	}
		
	
	public Employee(long employeeId,  long roleId, long departmentId) {
		super();
		this.employeeId = employeeId;
		this.roleId = roleId;
		this.departmentId = departmentId;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
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


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
	
	



}
