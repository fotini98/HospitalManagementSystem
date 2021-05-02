package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ikubinfo.primefaces.model.Appointment;
import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.model.Patient;

public class AppointmentRowMapper  implements RowMapper<Appointment> {

	@Override
	public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Appointment appointment = new Appointment();
		appointment.setAppointmentId(rs.getLong("appointment_id"));
		appointment.setPatientId(rs.getLong("patient_id"));
		//appointment.setDoctorId(rs.getLong("doctor_id"));
		appointment.setStatus(rs.getString("status"));
		Patient patient = new Patient();
		patient.setFullName(rs.getString("patient"));
		appointment.setPatient(patient);
		appointment.setDate(rs.getTimestamp("date"));
		appointment.setCreatedBy(rs.getString("created_by"));
		appointment.setLastUpdated(rs.getTimestamp("last_updated"));
		appointment.setModifiedBy(rs.getString("modified_by"));
		appointment.setDeleted(rs.getBoolean("deleted"));
		return appointment;
	}

}
