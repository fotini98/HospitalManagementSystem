package com.ikubinfo.primefaces.repository;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.*;
import com.ikubinfo.primefaces.repository.mapper.AppointmentRowMapper;
@Repository
public class AppointmentRepository {
	
	Logger logger = LoggerFactory.getLogger(AppointmentRepository.class);
	
	private static final String QUERY = "SELECT appointment_id, person.full_name as patient, appointment.patient_id, date, status, appointment.deleted, appointment.created_by, appointment.last_updated, appointment.modified_by "
			+ "	FROM appointment "
			+ "	inner join patient on patient.patient_id=appointment.patient_id "
			+ "	inner join person on person.person_id=patient.patient_id "
			+ " where appointment.deleted=false ";
	private static final String UPDATE_QUERY = "UPDATE appointment "
			+ "	SET  patient_id=:patient_id, date=:date, status=:status, created_by=:createdBy, "
			+ " modified_by=:modifiedBy "
			+ "	WHERE appointment_id= :appointmentId;";
	private static final String DELETE_QUERY = "UPDATE public.appointment SET deleted= true WHERE appointment_id= :appointmentId ";
	
	private static final String INSERT_QUERY="INSERT INTO appointment (patient_id, doctor_id, date, status, created_by, modified_by)\r\n"
			+ "	VALUES ( :patientId, :doctorId, :date, :status, :createdBy, :modifiedBy); ";
	
	private static final String GET_PATIENTS="SELECT patient_id, patient.person_id, full_name  FROM patient inner join person on person.person_id=patient.person_id ";
	
	private static final String GET_PATIENT_ID="select patient_id from patient inner join person on person.person_id=patient.person_id\r\n"
			+ "where person.full_name= ?";
	
	private static final String UPDATE_APP_STATUS="UPDATE appointment set status=:status where appointment_id= :appointmentId";
	
	private static final String GET_APP_AVAILABILITY="select count(*) as count from appointment where appointment.date <= ? "
			+ "and end_time between ? and ? "
			+ "and appointment.status='Approved'  and appointment.doctor_id=?";
	
	/*
	 * private static final String
	 * GET_APP_BY_ID="SELECT appointment_id, person.full_name as patient, appointment.patient_id, date, status, appointment.deleted, appointment.created_by, appointment.last_updated, appointment.modified_by "
	 * + "	FROM appointment " +
	 * "	inner join patient on patient.patient_id=appointment.patient_id " +
	 * "	inner join person on person.person_id=patient.patient_id " +
	 * " where appointment.deleted=false ";
	 */
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	//private SimpleJdbcInsert insertAppointmentQuery;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public AppointmentRepository(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
	//	this.insertAppointmentQuery = new SimpleJdbcInsert(datasource).withTableName("appointment")
		//		.usingGeneratedKeyColumns("appointment_id");
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	public List<Appointment> getAllAppointments() {
		

		return namedParameterJdbcTemplate.query(QUERY, new AppointmentRowMapper());

		
	}
	
	public List<Appointment> getAllDoctorAppointment(long doctor_id) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query=QUERY.concat(" and doctor_id= :doctorId;");
		namedParameters.addValue("doctorId", doctor_id);
		System.out.println(query);
		try {
		return namedParameterJdbcTemplate.query(query, namedParameters, new AppointmentRowMapper());
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public List<Appointment> getDoctorReqAppointment(long doctor_id) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query=QUERY.concat(" and doctor_id= :doctorId and  appointment.status='Requested';");
		namedParameters.addValue("doctorId", doctor_id);
		System.out.println(query);
		try {
		return namedParameterJdbcTemplate.query(query, namedParameters, new AppointmentRowMapper());
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	
	public List<Patient> getPatients(){
		RowMapper<Patient> rowMapper = (rs, rowNum) -> {
			Patient patient = new Patient();
			patient.setPersonId(rs.getInt("person_id"));
			patient.setPersonId(rs.getInt("patient_id"));
			patient.setFullName(rs.getString("full_name"));
			return patient;
		};
		return jdbcTemplate.query(GET_PATIENTS, rowMapper);
		
	}
	public boolean create(Appointment appointment) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		int patientId = jdbcTemplate.queryForObject(GET_PATIENT_ID,
				new Object[] { appointment.getPatient().getFullName() }, Integer.class);
		namedParameters.addValue("patientId", patientId);
		namedParameters.addValue("doctorId", appointment.getDoctorId());
		namedParameters.addValue("date", appointment.getDate());
		namedParameters.addValue("status", appointment.getStatus());
		namedParameters.addValue("createdBy", appointment.getCreatedBy());
		namedParameters.addValue("modifiedBy", appointment.getModifiedBy());
		
		return namedParameterJdbcTemplate.update(INSERT_QUERY, namedParameters)>0;
		
	}
	
	public boolean delete(Appointment appointment) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("appointmentId", appointment.getAppointmentId());
		return namedParameterJdbcTemplate.update(DELETE_QUERY, namedParameters)>0;
	}
	
	public boolean updateAppointment(Appointment appointment) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		int patientId = jdbcTemplate.queryForObject(GET_PATIENT_ID,
				new Object[] { appointment.getPatient().getFullName() }, Integer.class);
		namedParameters.addValue("appointmentId", appointment.getAppointmentId());
		namedParameters.addValue("patient_id", patientId);
		namedParameters.addValue("doctorId", appointment.getDoctorId());
		namedParameters.addValue("date", appointment.getDate());
		namedParameters.addValue("status", appointment.getStatus());
		namedParameters.addValue("createdBy", appointment.getCreatedBy());
		namedParameters.addValue("modifiedBy", appointment.getModifiedBy());
		
		return namedParameterJdbcTemplate.update(UPDATE_QUERY, namedParameters)>0;
	}
	
	public boolean updateAppointmentStatus(Appointment appointment) {
		
		MapSqlParameterSource namedParameters=new MapSqlParameterSource();
		
		namedParameters.addValue("appointmentId", appointment.getAppointmentId());
		namedParameters.addValue("status", appointment.getStatus());
		
		
		return namedParameterJdbcTemplate.update(UPDATE_APP_STATUS, namedParameters)>0;
	}
	
	public boolean appointmentOccupied(Timestamp newAppStart, long doctorId) {
		Timestamp newAppEnd=new Timestamp(newAppStart.getTime()+(20*60*1000));
        int count;
		try {
        	  count = jdbcTemplate.queryForObject(GET_APP_AVAILABILITY,new Object[] { newAppStart, newAppStart, newAppEnd, doctorId }, Integer.class);
        	 System.out.println(newAppStart  +" "+ newAppStart+" "+newAppEnd +" "+doctorId);
        	  System.out.println("count query result "+count);
         }catch(EmptyResultDataAccessException e) {
        	 count=0;
         }
					
		return count>0;
	}

	
	

}
