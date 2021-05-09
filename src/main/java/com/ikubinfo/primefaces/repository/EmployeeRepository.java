package com.ikubinfo.primefaces.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import com.ikubinfo.primefaces.repository.mapper.EmployeeRowMapper;

@Repository
public class EmployeeRepository {
	Logger logger = LoggerFactory.getLogger(AppointmentRepository.class);

	private static final String GET_EMPLOYEES_QUERY = "select person.person_id, employee.employee_id, person.full_name, person.email, person.address, person.phone, person.birth_date,"
			+ " person.age, person.created_by,department.name as department, role.role_name "
			+ "	from person inner join employee on person.person_id=employee.person_id "
			+ " inner join department on employee.department_id=department.department_id "
			+ "	inner join role on employee.role_id=role.role_id "
			+ "	where role.role_name= :roleName and person.deleted='false'";
	private static final String DELETE_QUERY = "UPDATE person SET deleted=true WHERE person_id= :personId ";
	private static final String GET_DEPARTMENTS = "select * from department";
	private static final String GET_DEPARTMENT_ID = "select department_id from department where department.name= ?";
	private static final String GET_ROLE_ID = "select role_id from role where role.role_name= ?";
	private static final String INSERT_EMPLOYEE_QUERY = "INSERT INTO employee(person_id, role_id, department_id)VALUES ( :personId, :roleId, :departmentId)";
	private static final String UPDATE_PERSON_QUERY = "UPDATE person SET full_name=:fullName, email=:email, address=:address, phone=:phone, birth_date=:birthDate, "
			+ "  password=:password, modified_by=:modifiedBy WHERE person_id=:personId;";
	private static final String UPDATE_EMPLOYEE_QUERY = "UPDATE employee SET  department_id= :departmentId "
			+ "	WHERE person_id= :personId";
    private static final String COUNT_EMPLOYEE_BY_ROLE="select count(employee_id)employee_id from employee inner join role on role.role_id=employee.role_id "
    		+ "inner join person on person.person_id=employee.person_id "
    		+ "where role.role_name=? and person.deleted=false";
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertPersonQuery;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public EmployeeRepository(DataSource datasource) {
		super();
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.jdbcTemplate = new JdbcTemplate(datasource);
		insertPersonQuery = new SimpleJdbcInsert(datasource).withTableName("person")
				.usingGeneratedKeyColumns("person_id");
	}

	public List<Employee> getAllEmployees(String roleName, String fullName) {
		
		String query=GET_EMPLOYEES_QUERY;
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		if (!Objects.isNull(fullName) && !fullName.equals("")) {
			fullName=fullName.replace(fullName, "%" + fullName + "%");
			System.out.println(fullName+"from getAllEmployeess repo");
			query = query.concat("and  LOWER(full_name) LIKE  LOWER(:fullName)");
			namedParameters.addValue("fullName", fullName);	
		}
		
		namedParameters.addValue("roleName", roleName);
		return namedParameterJdbcTemplate.query(query, namedParameters, new EmployeeRowMapper());
		
	}

	public boolean saveEmployee(Employee employee) {

		//int personId = savePerson(employee);
		int departmentId;
		int roleId;
		System.out.println("From saveEmployee person id " + employee.getPersonId());
		if (employee.getPersonId() > 0) {
			departmentId = jdbcTemplate.queryForObject(GET_DEPARTMENT_ID,
					new Object[] { employee.getDepartment().getName() }, Integer.class);
			System.out.println(departmentId);
			if (departmentId > 0) {
				roleId = jdbcTemplate.queryForObject(GET_ROLE_ID, new Object[] { employee.getRole().getName() },
						Integer.class);
				if (roleId > 0) {
					MapSqlParameterSource namedParameters = new MapSqlParameterSource();
					System.out.println("saving person with person id: " + employee.getPersonId());
					namedParameters.addValue("personId", employee.getPersonId());
					namedParameters.addValue("departmentId", departmentId);
					namedParameters.addValue("roleId", roleId);
					int employeeInserted = this.namedParameterJdbcTemplate.update(INSERT_EMPLOYEE_QUERY,
							namedParameters);
					return employeeInserted > 0;
				}
			}
		}
		return false;
	}

	public int savePerson(Employee employee) {
		System.out.println("saving person...........");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("full_name", employee.getFullName());
		parameters.put("email", employee.getEmail());
		parameters.put("address", employee.getAddress());
		parameters.put("phone", employee.getPhone());
		parameters.put("birth_date", employee.getBirthDate());
		parameters.put("password", employee.getPassword());
		parameters.put("created_by", employee.getCreatedBy());

		int personId = (int) insertPersonQuery.executeAndReturnKey(parameters);
		return personId;
	}

	public boolean deleteEmployee(Employee employee) {
		System.out.println(employee.getPersonId() + " from repository");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("personId", employee.getPersonId());
		int deletedPerson = this.namedParameterJdbcTemplate.update(DELETE_QUERY, namedParameters);
		return deletedPerson > 0;

	}

	public List<Department> getAllDepartments() {
		RowMapper<Department> rowMapper = (rs, rowNum) -> {
			Department department = new Department(rs.getInt("department_id"), rs.getString("name"),
					rs.getString("description"));
			return department;
		};
		return namedParameterJdbcTemplate.query(GET_DEPARTMENTS, rowMapper);

	}

	public boolean updateEmployee(Employee employee) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		int departmentId = jdbcTemplate.queryForObject(GET_DEPARTMENT_ID,
				new Object[] { employee.getDepartment().getName() }, Integer.class);
		namedParameters.addValue("departmentId", departmentId);
		namedParameters.addValue("personId", employee.getPersonId());
		int updatedEmployee = this.namedParameterJdbcTemplate.update(UPDATE_EMPLOYEE_QUERY, namedParameters);
		return updatedEmployee > 0;

	}

	public boolean updatePerson(Employee employee) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("fullName", employee.getFullName());
		namedParameters.addValue("email", employee.getEmail());
		namedParameters.addValue("address", employee.getAddress());
		namedParameters.addValue("phone", employee.getPhone());
		namedParameters.addValue("birthDate", employee.getBirthDate());
		namedParameters.addValue("password", employee.getPassword());
		namedParameters.addValue("modifiedBy", employee.getModifiedBy());
		namedParameters.addValue("personId", employee.getPersonId());
		int updatedPerson = this.namedParameterJdbcTemplate.update(UPDATE_PERSON_QUERY, namedParameters);
		return updatedPerson > 0;
	}
	
	public int getEmployeeCountByRole(String role) {
		int count = jdbcTemplate.queryForObject(COUNT_EMPLOYEE_BY_ROLE,
				new Object[] { role }, Integer.class);
		System.out.println(count);
		return count;
	}

}
