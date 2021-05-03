package com.ikubinfo.primefaces.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Appointment {

	private long appointmentId;
	private long patientId;
	private long doctorId;
	private Date date;
	private String status;
	private String createdBy;
	private Timestamp lastUpdated;
	private String modifiedBy;
	private boolean deleted;
	
	private Employee doctor;
	private Patient patient;
	
	
	
	public Appointment() {
		super();
	}
	public Appointment(long patientId, long doctorId, Date date, String createdBy, String status,
			Timestamp lastUpdated, String modifiedBy, boolean deleted) {
		super();
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.date = date;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.modifiedBy = modifiedBy;
		this.deleted = deleted;
		this.status= status;

	}
	
	
	public Appointment(long appointmentId, String status) {
		super();
		this.appointmentId = appointmentId;
		this.status = status;
	}
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}



	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Employee getDoctor() {
		return doctor;
	}
	public void setDoctor(Employee doctor) {
		this.doctor = doctor;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	} 
	
	
	
	



}
