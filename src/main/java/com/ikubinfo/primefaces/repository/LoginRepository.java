package com.ikubinfo.primefaces.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.model.Login;
import com.ikubinfo.primefaces.model.Patient;
import com.ikubinfo.primefaces.repository.mapper.EmployeeRowMapper;

@Repository
public class LoginRepository {
	
	private static final String LogInQuery="SELECT person.person_id, person.full_name, person.email, person.password, employee.employee_id, role.role_id\r\n"
			+ "from person inner join employee on employee.person_id=person.person_id\r\n"
			+ "inner join role on employee.role_id=role.role_id\r\n"
			+ "where person.email=:email and person.password=:password";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public LoginRepository(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	

	public Employee logInEmployee(String email, String password){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("email", email);
		namedParameters.addValue("password", password);
		
		RowMapper<Employee> rowMapper = (rs, rowNum) -> {
			Employee employee= new Employee();
			employee.setPersonId(rs.getInt("person_id"));
			employee.setEmployeeId(rs.getInt("employee_id"));
			employee.setFullName(rs.getString("full_name"));
			employee.setEmail(rs.getString("email"));
			employee.setRoleId(rs.getInt("role_id"));
			return employee;
		};
		Employee employee;
		try{
			 employee=this.namedParameterJdbcTemplate.queryForObject(LogInQuery, namedParameters, rowMapper);
			 return employee;
		}catch(EmptyResultDataAccessException  e) {
			System.err.println("No record found in database for! "+email);	
			return null;
		}
		
		}
	
	
	
	
}