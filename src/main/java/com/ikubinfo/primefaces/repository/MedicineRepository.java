package com.ikubinfo.primefaces.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.Medicine;

@Repository
public class MedicineRepository {
	
    private static final String GET_MEDICINE_ID="select medicine_id from medicine where name= ?";
	
	private static final String GET_ALL_MEDICINES="SELECT medicine_id, name, strength FROM public.medicine";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public MedicineRepository(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	public List<Medicine> getAllMedicines(){
		RowMapper<Medicine> rowMapper = (rs, rowNum) -> {
			Medicine medicine = new Medicine(rs.getInt("medicine_id"), rs.getString("name"),
					rs.getInt("strength"));
			return medicine;
		};
		return namedParameterJdbcTemplate.query(GET_ALL_MEDICINES, rowMapper);
	}
	
	public int getMedicineId(Medicine medicine) {
 	int medicineId = jdbcTemplate.queryForObject(GET_MEDICINE_ID,
				new Object[] { medicine.getName() }, Integer.class);
	return medicineId;
	}

}
