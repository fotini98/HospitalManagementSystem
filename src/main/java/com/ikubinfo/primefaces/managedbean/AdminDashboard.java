package com.ikubinfo.primefaces.managedbean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ikubinfo.primefaces.service.EmployeeService;


@ManagedBean
@ViewScoped
public class AdminDashboard {
	
	private int doctorCount;
	private int receptionistCount;
	private int nurseCount;
	private int patientCount;
	
	
	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService employeeService;
	
	
	@PostConstruct
	public void init() {
		doctorCount=employeeService.getEmployeeCountByRole("Doctor");
		receptionistCount=employeeService.getEmployeeCountByRole("Receptionist");
		nurseCount=employeeService.getEmployeeCountByRole("Nurse");

	
	}


	public int getDoctorCount() {
		return doctorCount;
	}


	public void setDoctorCount(int doctorCount) {
		this.doctorCount = doctorCount;
	}


	public int getReceptionistCount() {
		return receptionistCount;
	}


	public void setReceptionistCount(int receptionistCount) {
		this.receptionistCount = receptionistCount;
	}


	public int getNurseCount() {
		return nurseCount;
	}


	public void setNurseCount(int nurseCount) {
		this.nurseCount = nurseCount;
	}


	public int getPatientCount() {
		return patientCount;
	}


	public void setPatientCount(int patientCount) {
		this.patientCount = patientCount;
	}


	public EmployeeService getEmployeeService() {
		return employeeService;
	}


	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	

}
