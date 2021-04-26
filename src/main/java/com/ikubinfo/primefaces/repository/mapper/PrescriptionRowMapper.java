package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ikubinfo.primefaces.model.Appointment;
import com.ikubinfo.primefaces.model.Medicine;
import com.ikubinfo.primefaces.model.Patient;
import com.ikubinfo.primefaces.model.Prescription;
import com.ikubinfo.primefaces.model.PrescriptionMedicine;

public class PrescriptionRowMapper implements RowMapper<Prescription> {

	@Override
	public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
		Prescription prescription =new Prescription();
		prescription.setPrescriptionId(rs.getInt("prescription_id"));
		prescription.setAppointmentId(rs.getInt("appointment_id"));
		Appointment appointment =new Appointment();
		Patient patient=new Patient();
		patient.setFullName(rs.getString("patient"));
		System.out.println(patient.getFullName());;
		appointment.setAppointmentId(rs.getInt("appointment_id"));
		appointment.setPatient(patient);
		prescription.setAppointment(appointment);
		/*
		 * Medicine medicine=new Medicine();
		 * medicine.setMedicineId(rs.getInt("medicine_id"));
		 * medicine.setName(rs.getString("name")); PrescriptionMedicine prescMed=new
		 * PrescriptionMedicine(); prescMed.setMedicine(medicine);
		 * prescMed.setDose(rs.getInt("dose"));
		 * prescMed.setDuration(rs.getString("duration"));
		 */
		
		return prescription;
	}

}
