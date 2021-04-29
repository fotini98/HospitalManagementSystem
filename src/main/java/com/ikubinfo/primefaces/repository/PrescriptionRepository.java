package com.ikubinfo.primefaces.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.Department;
import com.ikubinfo.primefaces.model.Medicine;
import com.ikubinfo.primefaces.model.Prescription;
import com.ikubinfo.primefaces.model.PrescriptionMedicine;
import com.ikubinfo.primefaces.repository.mapper.PrescriptionRowMapper;

@Repository
public class PrescriptionRepository {
	
	Logger logger = LoggerFactory.getLogger(AppointmentRepository.class);
	
	private static final String GET_ALL_PRESCRIPTIONS="SELECT prescription.prescription_id, prescription.appointment_id, person.full_name as patient \r\n"
			+ "	FROM public.prescription\r\n"
			+ "	inner join appointment on appointment.appointment_id=prescription.appointment_id\r\n"
			+ "	inner join patient on patient.patient_id=appointment.patient_id \r\n"
			+ "	inner join person on person.person_id=patient.patient_id";
	//private static final String INSERT_PRESCRIPTION="insert into prescription(appointment_id) values(:appointment_id)";
	private static final String INSERT_MEDICINE_PRESCRIPTION="INSERT INTO public.prescription_medicine(\r\n"
			+ "	prescription_id, medicine_id, dose, duartion)\r\n"
			+ "	VALUES (:prescription_id, :medicine_id, :dose, :duartion);";
	
	private static final String GET_ALL_MEDICINES="SELECT medicine_id, name, strength FROM public.medicine";
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertPrescriptionQuery;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public PrescriptionRepository(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.insertPrescriptionQuery = new SimpleJdbcInsert(datasource).withTableName("prescription")
				.usingGeneratedKeyColumns("prescription_id");
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	public List<Prescription> getAllPrescriptions(){
		
	
		return jdbcTemplate.query(GET_ALL_PRESCRIPTIONS, new PrescriptionRowMapper());
		
	}
	public List<Prescription> getAllDocPrescriptions(long doctorId){
		
		String query=GET_ALL_PRESCRIPTIONS;
		query.concat( " where doctor_id= :doctorId ");
		MapSqlParameterSource namedParameters=new MapSqlParameterSource();
		namedParameters.addValue("doctorId", doctorId);
		
		return namedParameterJdbcTemplate.query(query, namedParameters,new PrescriptionRowMapper() );
		
	}
	public boolean insertPrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
		MapSqlParameterSource namedParameters=new MapSqlParameterSource();
		namedParameters.addValue("prescription_id", prescriptionMedicine.getPrescriptionId());
		namedParameters.addValue("medicine_id", prescriptionMedicine.getMedicineId());
		namedParameters.addValue("dose", prescriptionMedicine.getDose());
		namedParameters.addValue("duartion", prescriptionMedicine.getDuration());
		return namedParameterJdbcTemplate.update(INSERT_MEDICINE_PRESCRIPTION, namedParameters)>0;
	}
		
	public int addPrescription(long appointmentId) {
		System.out.println("saving prescription...........");
		Map<String, Object> parameters = new HashMap<>();
        parameters.put("appointment_id", parameters);		
		int perscriptionId = (int) insertPrescriptionQuery.executeAndReturnKey(parameters);
		return perscriptionId;
	}
	
	public List<Medicine> getAllMedicines(){
		RowMapper<Medicine> rowMapper = (rs, rowNum) -> {
			Medicine medicine = new Medicine(rs.getInt("medicine_id"), rs.getString("name"),
					rs.getInt("strength"));
			return medicine;
		};
		return namedParameterJdbcTemplate.query(GET_ALL_MEDICINES, rowMapper);
	}

	
	

}
