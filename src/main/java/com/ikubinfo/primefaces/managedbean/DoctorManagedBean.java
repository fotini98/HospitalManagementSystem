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
public class DoctorManagedBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Employee doctor;
	private List<Employee> doctors;
	private List<Department> departments; 
	private String fullName;
	
	private Department department;
	
	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService doctorService;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	
	@ManagedProperty(value = "#{messages}")
	private Messages messages;
	
	@PostConstruct
	public void init() {

		doctors = doctorService.getAllEmployees("Doctor", fullName);
		departments= doctorService.getAllDepartments();
		doctor = new Employee();
		department=new Department();

	}
	
	public void filter() {
		System.out.println("filter called"+fullName);
		doctors = doctorService.getAllEmployees("Doctor", fullName);
	}
	
	public void save() {
		doctor.setDepartment(department);
		doctor.setRole(new Role(0,"Doctor"));
		doctor.setCreatedBy(loginBean.getEmployee().getFullName());
		doctor.setModifiedBy(loginBean.getEmployee().getFullName());
		if(doctorService.saveEmployee(doctor)) {
			doctors = doctorService.getAllEmployees("Doctor", null);
			doctor=new Employee();
			messages.showInfoMessage("Doctor was added successfully");
		}else {
			messages.showInfoMessage("Something went wrong!!");
		}
	}
	
	public void delete() {
        doctor.setModifiedBy(loginBean.getEmployee().getFullName());
		System.out.println(doctor );
		if(doctorService.deleteEmployee(doctor)) {
			doctors = doctorService.getAllEmployees("Doctor", null);
		messages.showWarningMessage("Doctor was removed successfully");
	}else {
		messages.showErrorMessage("Something went wrong!!");
	
	 }
	}
	
	public void update() {
		doctor.setDepartment(department);
		 doctor.setModifiedBy(loginBean.getEmployee().getFullName());
		System.out.println(doctor +" department "+department.getName());
		if(doctorService.updateEmployee(doctor)) {
			doctors = doctorService.getAllEmployees("Doctor",null);
		messages.showInfoMessage("Doctor was updated successfully");
	}else {
		messages.showInfoMessage("Something went wrong!!");
	
	 }
	}
	

	public Employee getDoctor() {
		return doctor;
	}

	public void setDoctor(Employee doctor) {
		this.doctor = doctor;
	}

	public List<Employee> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Employee> employees) {
		this.doctors = employees;
	}

	public EmployeeService getDoctorService() {
		return doctorService;
	}

	public void setDoctorService(EmployeeService doctorService) {
		this.doctorService = doctorService;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
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

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		System.out.println("setFullName called");
		this.fullName = fullName;
	}
	
	

}
