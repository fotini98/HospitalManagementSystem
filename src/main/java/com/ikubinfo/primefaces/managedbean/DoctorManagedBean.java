package com.ikubinfo.primefaces.managedbean;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.service.EmployeeService;
import com.ikubinfo.primefaces.util.Messages;

@ManagedBean
@ViewScoped
public class DoctorManagedBean {
	
	private Employee doctor;
	private List<Employee> doctors;
	
	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService doctorService;
	
	@ManagedProperty(value = "#{messages}")
	private Messages messages;
	
	@PostConstruct
	public void init() {

		doctors = doctorService.getAllEmployees("Doctor");
		doctor = new Employee();

	}
	
	public void save() {
		if(doctorService.saveEmployee(doctor)) {
			doctors = doctorService.getAllEmployees("Doctor");
			messages.showInfoMessage("Doctor was added successfully");
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
	
	

}
