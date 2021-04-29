package com.ikubinfo.primefaces.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.model.Login;
import com.ikubinfo.primefaces.repository.LoginRepository;

@Service
public class LoginService {
	@Autowired
	private LoginRepository dao;
	
	public Employee logInEmployee(String email, String password){
		return dao.logInEmployee(email,password );
	}

}
