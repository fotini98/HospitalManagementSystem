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
import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.model.Medicine;
import com.ikubinfo.primefaces.model.Prescription;
import com.ikubinfo.primefaces.model.PrescriptionMedicine;
import com.ikubinfo.primefaces.repository.mapper.PrescriptionMedicineRowMapper;
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
			+ "	prescription_id, medicine_id, dose, duration)\r\n"
			+ "	VALUES (:prescription_id, :medicine_id, :dose, :duartion);";

	private static final String PRESCRIPTION_EXISTS= "select prescription_id from prescription where appointment_id=?";
	
	private static final String GET_PRESCRIPTION_MEDICINE="SELECT  medicine.medicine_id, dose, duration, prescription_medicine.prescription_medicine_id, medicine.name, prescription.appointment_id "
			+ "FROM public.prescription_medicine inner join medicine on medicine.medicine_id=prescription_medicine.medicine_id "
			+ "inner join prescription on prescription.prescription_id=prescription_medicine.prescription_id "
			+ "where prescription.appointment_id=:appointment_id ";
//	private static final String UPDATE_PRESCRIPTION_MEDICINE="UPDATE public.prescription_medicine "
//			+ "	SET  medicine_id=:medicine_id,  duration=:duration, dose=:dose "
//			+ "	WHERE prescription_medicine_id=prescription_medicine_id;";
	private static final String DELETE_PRESCRIPTION_MEDICINE="DELETE FROM public.prescription_medicine\r\n"
			+ "	WHERE prescription_medicine_id=:prescription_medicine_id";
	
	private static final String GET_PRESCRIPTION_ID="SELECT prescription_id "
			+ "	FROM public.prescription where appointment_id=?";
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
        parameters.put("appointment_id", appointmentId);		
		int perscriptionId = (int) insertPrescriptionQuery.executeAndReturnKey(parameters);
		return perscriptionId;
	}
	
	
	
	public List<PrescriptionMedicine> getPrescriptionMedicine(long appointmentId){
		long prescriptionId = jdbcTemplate.queryForObject(PRESCRIPTION_EXISTS, new Object[] { appointmentId },
				Integer.class);
		System.out.println("prescription id "+ prescriptionId);
		if(prescriptionId!=0) {
			MapSqlParameterSource namedParameters=new MapSqlParameterSource();
			namedParameters.addValue("appointment_id", appointmentId);
			return namedParameterJdbcTemplate.query(GET_PRESCRIPTION_MEDICINE,namedParameters, new PrescriptionMedicineRowMapper());
			};
			
		return null;
	}
	public int getPrescriptionId(int appointmentId) {
	 	int prescriptionId = jdbcTemplate.queryForObject(GET_PRESCRIPTION_ID,
					new Object[] { appointmentId}, Integer.class);
		return prescriptionId;
		}
	public boolean updatePrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
		MapSqlParameterSource namedParameters=new MapSqlParameterSource();
		namedParameters.addValue("prescription_id", prescriptionMedicine.getPrescriptionId());
		namedParameters.addValue("medicine_id", prescriptionMedicine.getMedicineId());
		namedParameters.addValue("dose", prescriptionMedicine.getDose());
		namedParameters.addValue("duartion", prescriptionMedicine.getDuration());
		return namedParameterJdbcTemplate.update(INSERT_MEDICINE_PRESCRIPTION, namedParameters)>0;
		
	}
	
	public boolean deletePrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
		System.out.println("from deletePrescription "+prescriptionMedicine);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		System.out.println(prescriptionMedicine.getPrescriptionMedicineId());
		namedParameters.addValue("prescription_medicine_id", prescriptionMedicine.getPrescriptionMedicineId());
		int deletePrescriptionMedicine = this.namedParameterJdbcTemplate.update(DELETE_PRESCRIPTION_MEDICINE, namedParameters);
		return deletePrescriptionMedicine > 0;
	}
		

	
	

}
