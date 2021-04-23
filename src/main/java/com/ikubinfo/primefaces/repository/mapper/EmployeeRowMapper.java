package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ikubinfo.primefaces.model.Department;
import com.ikubinfo.primefaces.model.Employee;

public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee= new Employee();
		employee.setPersonId(rs.getInt("person_id"));
		employee.setEmployeeId(rs.getInt("employee_id"));
		employee.setFullName(rs.getString("full_name"));
		employee.setEmail(rs.getString("email"));
		employee.setAddress(rs.getString("address"));
		employee.setPhone(rs.getString("phone"));
		employee.setBirthDate(rs.getDate("birth_date"));
		employee.setAge(rs.getInt("age"));
		employee.setCreatedBy(rs.getString("created_by"));
		employee.setDepartment(new Department(rs.getString("department")));
		return employee;
	}
	

}
