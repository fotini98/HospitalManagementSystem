package com.ikubinfo.primefaces.service;

import org.springframework.stereotype.Service;

import com.ikubinfo.primefaces.repository.TestRepository;

@Service
public class TestService {

	private TestRepository testRepository;

	public TestService(TestRepository testRepository) {
		super();
		this.testRepository = testRepository;
	}

}
