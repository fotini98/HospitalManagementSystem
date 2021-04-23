package com.ikubinfo.primefaces.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikubinfo.primefaces.model.Department;
import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository dao;
	
	public List<Employee> getAllEmployees(String roleName) {
		return dao.getAllEmployees(roleName);
	}
	public boolean saveEmployee(Employee employee) {
		return dao.saveEmployee(employee);
	}
	public boolean deleteEmployee(Employee employee) {
		return dao.deleteEmployee(employee);
	}
	public List<Department> getAllDepartments() {
		return dao.getAllDepartments();
	}
	@Transactional
	public boolean updateEmployee(Employee employee) {
		    dao.updatePerson(employee);
		return dao.updateEmployee(employee);
	}
	
	

}
