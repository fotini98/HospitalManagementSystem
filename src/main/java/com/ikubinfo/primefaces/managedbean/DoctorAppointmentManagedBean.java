package com.ikubinfo.primefaces.managedbean;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


import com.ikubinfo.primefaces.model.Appointment;
import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.model.Patient;
import com.ikubinfo.primefaces.service.AppointmentService;
import com.ikubinfo.primefaces.util.Messages;

@ManagedBean
@ViewScoped
public class DoctorAppointmentManagedBean {
	
	private Employee doctor;
	private Appointment appointment;
	private List<Appointment> appointments;
	private List<Patient> patients;
	private Patient patient;//??
	@ManagedProperty(value = "#{appointmentService}")
	private AppointmentService service;
	
	@ManagedProperty(value="#{messages}")
	private Messages message;
	
	
	@PostConstruct
	public void init() {
		doctor=new Employee();
		appointment=new Appointment();
		appointments=service.getAllDoctorAppointment(5);
		patients=service.getPatients();
		patient=new Patient();
	}
	
	public void create() {
		appointment.setPatient(patient);
		appointment.setDoctorId(5);
		if(service.create(appointment)) {
			appointments=service.getAllDoctorAppointment(5);
			message.showInfoMessage("Appointment Added Successfully!");
		}else {
			message.showFatalMessage("Something went wrong!!");
		}
	}
	
	public void update() {
		appointment.setPatient(patient);
		appointment.setDoctorId(5);
		if(service.updateAppointment(appointment)) {
			appointments=service.getAllDoctorAppointment(5);
			message.showInfoMessage("Appointment updated Successfully!");
		}else {
			message.showFatalMessage("Something went wrong!!");
		}
	}
	
	public void delete() {
		if(service.delete(appointment)) {
			message.showInfoMessage("Appointment deleted Successfully!");
		}else {
			message.showFatalMessage("Something went wrong!!");
		}
	}
	
	public void markAsCompleted() {
		System.out.println(" mark as complted invoked");
		
		if(service.markAsCompleted(appointment)) {
			appointments=service.getAllDoctorAppointment(5);
			message.showInfoMessage("Appointment Status changed to Completed Successfully!");
		}else {
			message.showFatalMessage("Something went wrong!!");
		}
	}


	public Employee getDoctor() {
		return doctor;
	}


	public void setDoctor(Employee doctor) {
		this.doctor = doctor;
	}


	public Appointment getAppointment() {
		return appointment;
	}


	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}


	public List<Appointment> getAppointments() {
		return appointments;
	}


	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}


	public AppointmentService getService() {
		return service;
	}


	public void setService(AppointmentService service) {
		this.service = service;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	
	

}
