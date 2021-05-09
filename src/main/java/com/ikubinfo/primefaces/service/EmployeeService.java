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
	
	@Autowired
	private EmailServiceImpl email;
	
	public List<Employee> getAllEmployees(String roleName, String  fullName) {
		return dao.getAllEmployees(roleName, fullName);
	}
	@Transactional
	public boolean saveEmployee(Employee employee) {
		employee.setPassword(randomPasswordGenerator());
		int personId=dao.savePerson(employee);
		employee.setPersonId(personId);
		email.sendSimpleMessage(employee.getEmail(), "Hospital management system login ceredentials", "email: "+employee.getEmail()+
				" Password: "+employee.getPassword());
		
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
	public int getEmployeeCountByRole(String role) {
		return dao.getEmployeeCountByRole(role);
	}
	
	public String randomPasswordGenerator() {
		String randomPassword = "";
		for(int j = 0; j < 10; j++) {
			//Add a random lowercase or UPPERCASE character to our randomPassword String
			randomPassword += randomCharacter();
		}
		System.out.println(randomPassword);
		return randomPassword;
		
	}
	private static char randomCharacter() {
		//We multiply Math.random() by 62 because there are 26 lowercase letters, 26 uppercase letters, and 10 numbers, and 26 + 26 + 10 = 62
		//This random number has values between 0 (inclusive) and 62 (exclusive)
		int rand = (int)(Math.random()*62);
		
		//0-61 inclusive = all possible values of rand
		//0-9 inclusive = 10 possible numbers/digits
		//10-35 inclusive = 26 possible uppercase letters
		//36-61 inclusive = 26 possible lowercase letters
		//If rand is between 0 (inclusive) and 9 (inclusive), we can say it's a number/digit
		//If rand is between 10 (inclusive) and 35 (inclusive), we can say it's an uppercase letter
		//If rand is between 36 (inclusive) and 61 (inclusive), we can say it's a lowercase letter
		if(rand <= 9) {
			//Number (48-57 in ASCII)
			//To convert from 0-9 to 48-57, we can add 48 to rand because 48-0 = 48
			int number = rand + 48;
			//This way, rand = 0 => number = 48 => '0'
			//Remember to cast our int value to a char!
			return (char)(number);
		} else if(rand <= 35) {
			//Uppercase letter (65-90 in ASCII)
			//To convert from 10-35 to 65-90, we can add 55 to rand because 65-10 = 55
			int uppercase = rand + 55;
			//This way, rand = 10 => uppercase = 65 => 'A'
			//Remember to cast our int value to a char!
			return (char)(uppercase);
		} else {
			//Lowercase letter (97-122 in ASCII)
			//To convert from 36-61 to 97-122, we can add 61 to rand because 97-36 = 61
			int lowercase = rand + 61;
			//This way, rand = 36 => lowercase = 97 => 'a'
			//Remember to cast our int value to a char!
			return (char)(lowercase);
		}
	}

}
