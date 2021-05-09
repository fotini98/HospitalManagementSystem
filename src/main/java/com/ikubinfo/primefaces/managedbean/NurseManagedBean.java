package com.ikubinfo.primefaces.managedbean;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ikubinfo.primefaces.model.Department;
import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.service.EmployeeService;
import com.ikubinfo.primefaces.util.Messages;

@ManagedBean
@ViewScoped
public class NurseManagedBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Employee nurse;
	private List<Employee> nurses;
	private List<Department> departments; 
	
	private Department department;
	
	private String fullName;
	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService nurseService;
	
	@ManagedProperty(value = "#{messages}")
	private Messages messages;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	@PostConstruct
	public void init() {

		nurses = nurseService.getAllEmployees("Nurse", fullName);
		departments= nurseService.getAllDepartments();
		nurse = new Employee();
		department=new Department();

	}
	
	public void save() {
		nurse.setDepartment(department);
		nurse.setRole(new Role(0,"Nurse"));
		nurse.setCreatedBy(loginBean.getEmployee().getFullName());
		nurse.setModifiedBy(loginBean.getEmployee().getFullName());
		if(nurseService.saveEmployee(nurse)) {
			nurses = nurseService.getAllEmployees("Nurse", null);
			nurse=new Employee();
			messages.showInfoMessage("Nurse was added successfully");
		}else {
			messages.showInfoMessage("Something went wrong!!");
		}
	}
	
	public void delete() {
		nurse.setRole(new Role(0,"Nurse"));
		nurse.setModifiedBy(loginBean.getEmployee().getFullName());
		if(nurseService.deleteEmployee(nurse)) {
			nurses = nurseService.getAllEmployees("Nurse", null);
		messages.showInfoMessage("Nurse was removed successfully");
	}else {
		messages.showInfoMessage("Something went wrong!!");
	
	 }
	}
	
	public void update() {
		nurse.setDepartment(department);
		nurse.setModifiedBy(loginBean.getEmployee().getFullName());
		if(nurseService.updateEmployee(nurse)) {
			nurses = nurseService.getAllEmployees("Nurse", null);
		messages.showInfoMessage("Nurse was updated successfully");
	}else {
		messages.showInfoMessage("Something went wrong!!");
	
	 }
	}

	public Employee getNurse() {
		return nurse;
	}

	public void setNurse(Employee nurse) {
		this.nurse = nurse;
	}

	public List<Employee> getNurses() {
		return nurses;
	}

	public void setNurses(List<Employee> nurses) {
		this.nurses = nurses;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public EmployeeService getNurseService() {
		return nurseService;
	}

	public void setNurseService(EmployeeService nurseService) {
		this.nurseService = nurseService;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	

	

}
