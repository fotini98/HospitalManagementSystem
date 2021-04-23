package com.ikubinfo.primefaces.model;

import javax.faces.convert.FacesConverter;

@FacesConverter("department")
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
	public Department() {
		// TODO Auto-generated constructor stub
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
		return  name ;
	}
	
	

}
