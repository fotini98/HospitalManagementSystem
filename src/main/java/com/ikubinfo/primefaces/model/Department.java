package com.ikubinfo.primefaces.model;

public class Department {
	private long departmentId;
	private String name;
	private String desription;
	
	
	
	public Department(String name) {
		super();
		this.name = name;
	}
	public Department(long departmentId, String name, String desription) {
		super();
		this.departmentId = departmentId;
		this.name = name;
		this.desription = desription;
	}
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesription() {
		return desription;
	}
	public void setDesription(String desription) {
		this.desription = desription;
	}
	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", name=" + name + ", desription=" + desription + "]";
	}
	
	

}
