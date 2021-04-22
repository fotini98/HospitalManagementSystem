package com.ikubinfo.primefaces.repository;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.repository.mapper.AppointmentRowMapper;
@Repository
public class AppointmentRepository {
	
	Logger logger = LoggerFactory.getLogger(AppointmentRepository.class);
	
	private static final String QUERY = "SELECT  person.full_name as patient, date, status, appointment.created_by, appointment.last_updated, appointment.modified_by "
			+ "	FROM appointment "
			+ "	inner join patient on patient.patient_id=appointment.patient_id "
			+ "	inner join person on person.person_id=patient.patient_id "
			+ "	where doctor_id= :doctor_id ";
	private static final String UPDATE_QUERY = "UPDATE appointment "
			+ "	SET , patient_id=:patient_id, doctor_id=:doctor_id, date=:date, status=:status, created_by=:created_by, last_updated=:last_updated, modified_by=:last_updated, deleted=:deleted "
			+ "	WHERE appointment_id= :appointment_id;";
	private static final String DELETE_QUERY = "DELETE FROM public.appointment WHERE appointment_id= :appointment_id ";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertAppointmentQuery;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public AppointmentRepository(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.insertAppointmentQuery = new SimpleJdbcInsert(datasource).withTableName("appointment")
				.usingGeneratedKeyColumns("appointment_id");
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	public List<Appointment> getAllAppointments() {
		

		return namedParameterJdbcTemplate.query(QUERY, new AppointmentRowMapper());

		
	}
	public List<Appointment> getAllDoctorAppointment(long doctor_id) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query=QUERY.concat(" where doctor_id= :doctor_id ;");
		namedParameters.addValue("doctorId", doctor_id);
		return namedParameterJdbcTemplate.query(query, namedParameters, new AppointmentRowMapper());
	}
	
//	public boolean save(Appointment appointment) {
//		
//	}
//	public boolean create(Appointment appointment) {
//		
//	}
//	
//	public void delete(Appointment appointment) {
//		
//	}
	
	

}
