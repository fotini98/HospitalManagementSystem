package com.ikubinfo.primefaces.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.model.Appointment;
import com.ikubinfo.primefaces.model.Patient;
import com.ikubinfo.primefaces.repository.AppointmentRepository;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository dao;
	
	public List<Appointment> getAll() {
		return dao.getAllAppointments();
	}
	
	public List<Appointment> getAllDoctorAppointment(long doctor_id) {
		return dao.getAllDoctorAppointment(doctor_id);
	}
	
	public boolean create(Appointment appointment) {
		appointment.setCreatedBy("test");
		appointment.setModifiedBy("test");
	    appointment.setStatus("Approved");
		return dao.create(appointment);
	}
	
	public boolean delete(Appointment appointment) {
		return dao.delete(appointment);
	}
	public boolean updateAppointment(Appointment appointment) {
		appointment.setCreatedBy("test");
		appointment.setModifiedBy("test");
		 appointment.setStatus("Approved");
		return dao.updateAppointment(appointment);
	}
	
	public List<Patient> getPatients(){
		return dao.getPatients();
	}
	
	public boolean markAsCompleted(Appointment appointment) {
		appointment.setStatus("Completed");
		return dao.updateAppointmentStatus(appointment);
	}
	public boolean approve(Appointment appointment) {
		appointment.setStatus("Accepted");
		return dao.updateAppointmentStatus(appointment);
	}
	public boolean reject(Appointment appointment) {
		appointment.setStatus("Rejected");
		return dao.updateAppointmentStatus(appointment);
	}
	
	public List<Appointment> getDoctorReqAppointment(long doctor_id) {
		return dao.getDoctorReqAppointment(doctor_id);
		
	}
		
	public boolean appointmentOccupied(Date newAppStart, long doctorId) {
		Timestamp timestamp=new Timestamp(newAppStart.getTime());
		return dao.appointmentOccupied(timestamp, doctorId);
	}
	

}
