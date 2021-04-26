package com.ikubinfo.primefaces.managedbean;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


import com.ikubinfo.primefaces.model.Appointment;
import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.service.AppointmentService;
import com.ikubinfo.primefaces.util.Messages;

@ManagedBean
@ViewScoped
public class DoctorReqAppointmentManagedBean {	
		private Employee doctor;
		private Appointment appointment;
		private List<Appointment> appointments;
		
		@ManagedProperty(value = "#{appointmentService}")
		private AppointmentService service;
		
		@ManagedProperty(value="#{messages}")
		private Messages message;
		
		
		@PostConstruct
		public void init() {
			doctor=new Employee();
			appointment=new Appointment();
			appointments=service.getDoctorReqAppointment(5);
			
		}
		
		public void approve() {
			appointment.setDoctorId(5);
			if(service.approve(appointment)) {
				appointments=service.getDoctorReqAppointment(5);
				message.showInfoMessage("Appointment was Approved!");
			}else {
				message.showFatalMessage("Something went wrong!!");
			}
			
		}
		public void reject() {
			System.out.println("method invoked");
			appointment.setDoctorId(5);
			if(service.reject(appointment)) {
				appointments=service.getDoctorReqAppointment(5);
				message.showWarningMessage("Appointment was Rejected!!");
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


		public Messages getMessage() {
			return message;
		}


		public void setMessage(Messages message) {
			this.message = message;
		}
		
		
}
