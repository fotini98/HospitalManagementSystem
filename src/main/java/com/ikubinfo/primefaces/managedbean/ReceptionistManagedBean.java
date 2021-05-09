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
public class ReceptionistManagedBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Employee receptionist;
	private List<Employee> receptionists;
	private List<Department> departments; 
	
	private Department department;
	
	private String fullName;
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService receptionistService;
	
	@ManagedProperty(value = "#{messages}")
	private Messages messages;
	
	@PostConstruct
	public void init() {

		receptionists = receptionistService.getAllEmployees("Receptionist",fullName );
		departments= receptionistService.getAllDepartments();
		receptionist = new Employee();
		department=new Department();

	}
	
	public void save() {
		receptionist.setDepartment(department);
		receptionist.setRole(new Role(0,"Receptionist"));
		receptionist.setCreatedBy(loginBean.getEmployee().getFullName());
		receptionist.setModifiedBy(loginBean.getEmployee().getFullName());
		if(receptionistService.saveEmployee(receptionist)) {
			receptionists = receptionistService.getAllEmployees("Receptionist", null);
			receptionist=new Employee();
			messages.showInfoMessage("Receptionist was added successfully");
		}else {
			messages.showInfoMessage("Something went wrong!!");
		}
	}
	
	public void delete() {
		receptionist.setRole(new Role(0,"Receptionist"));
		receptionist.setModifiedBy(loginBean.getEmployee().getFullName());
		if(receptionistService.deleteEmployee(receptionist)) {
			receptionists = receptionistService.getAllEmployees("Receptionist", null);
		messages.showInfoMessage("Receptionist was removed successfully");
	}else {
		messages.showInfoMessage("Something went wrong!!");
	
	 }
	}
	
	public void update() {
		receptionist.setDepartment(department);
		receptionist.setModifiedBy(loginBean.getEmployee().getFullName());
		if(receptionistService.updateEmployee(receptionist)) {
			receptionists = receptionistService.getAllEmployees("Receptionist", null);
		messages.showInfoMessage("Receptionist was updated successfully");
	}else {
		messages.showInfoMessage("Something went wrong!!");
	
	 }
	}

	public Employee getReceptionist() {
		return receptionist;
	}

	public void setReceptionist(Employee receptionist) {
		this.receptionist = receptionist;
	}

	public List<Employee> getReceptionists() {
		return receptionists;
	}

	public void setReceptionists(List<Employee> receptionists) {
		this.receptionists = receptionists;
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

	public EmployeeService getReceptionistService() {
		return receptionistService;
	}

	public void setReceptionistService(EmployeeService receptionistService) {
		this.receptionistService = receptionistService;
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
