package com.ikubinfo.primefaces.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.repository.mapper.EmployeeRowMapper;

@Repository
public class EmployeeRepository {
Logger logger = LoggerFactory.getLogger(AppointmentRepository.class);
	
	private static final String GET_EMPLOYEES_QUERY = "select person.person_id, person.full_name, person.email, person.address, person.phone, person.birth_date,"
			+ " person.age, person.created_by,department.name as department, role.role_name "
			+ "	from person inner join employee on person.person_id=employee.person_id "
			+ " inner join department on employee.department_id=department.department_id "
			+ "	inner join role on employee.role_id=role.role_id "
			+ "	where role.role_name= :roleName ";
	
//	private static final String INSERT_PERSON_QUERY="INSERT INTO public.person("
//			+ "	 full_name, email, address, phone, gender, birth_date, password, created_by)"
//			+ "	VALUES ( :full_name, :email,:address, :phone, :gender, :birth_date,  :password, :created_by);";

	private static final String DELETE_QUERY = "UPDATE public.person SET deleted=true WHERE person_id= :personId ";
	private static final String GET_DEPARTMENT_ID="select department_id from department where department.name= ?";
	private static final String GET_ROLE_ID="select role_id from role where role.role_name= :roleName";
	private static final String INSERT_EMPLOYEE_QUERY="INSERT INTO employee(person_id, role_id, department_id)VALUES ( :personId, roleId, departmentId)";
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertPersonQuery;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public EmployeeRepository(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.jdbcTemplate = new JdbcTemplate(datasource);
		insertPersonQuery = new SimpleJdbcInsert(datasource)
		          .withTableName("person").usingGeneratedKeyColumns("person_id");
	}
	
	public List<Employee> getAllEmployees(String roleName) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("roleName",roleName);

		return namedParameterJdbcTemplate.query(GET_EMPLOYEES_QUERY, namedParameters, new EmployeeRowMapper());
	}
	
	public boolean saveEmployee(Employee employee) {
		
		int personId=savePerson(employee);
		int departmentId;
		int roleId;
			if(personId>0) {
				 departmentId =jdbcTemplate.queryForObject(
						GET_DEPARTMENT_ID, new Object[]{employee.getDepartment().getName()}, Integer.class);
				 if(departmentId>0) {
					 roleId =jdbcTemplate.queryForObject(
							 GET_ROLE_ID, new Object[]{employee.getRole().getName()}, Integer.class);
					 if(roleId>0) {
						 MapSqlParameterSource namedParameters = new MapSqlParameterSource();
						 namedParameters.addValue("personId", personId);
						 namedParameters.addValue("departmentId", departmentId);
						 namedParameters.addValue("roleId", roleId);
						 int employeeInserted = this.namedParameterJdbcTemplate.update(INSERT_EMPLOYEE_QUERY, namedParameters);
						 return employeeInserted>0; 
					 }
				 }
		}
		
		  return false;
	}
	public int savePerson(Employee employee) {
		
		Map<String, Object> parameters = new HashMap<>();
	
		parameters.put("full_name",employee.getFullName());
		parameters.put("email", employee.getEmail());
		parameters.put("address", employee.getAddress());
		parameters.put("phone", employee.getPhone());
		parameters.put("gender", employee.getGender());
		parameters.put("birth_date", employee.getBirthDate());
		parameters.put("password", employee.getPassword());
		parameters.put("created_by", employee.getCreatedBy());
	    
	    int personId = (int) insertPersonQuery.executeAndReturnKey(parameters);
	    return  personId;
	}
	

	
	public boolean deleteEmployee(Employee employee) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	
		namedParameters.addValue("personId", employee.getEmployeeId());
		int deletedPerson = this.namedParameterJdbcTemplate.update(DELETE_QUERY, namedParameters);
		 return deletedPerson>0; 
		
	}
	
	
}
