package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.ikubinfo.primefaces.model.Test;

public class TestRowMapper implements RowMapper<Test> {

	@Override
	public Test mapRow(ResultSet result, int rowNum) throws SQLException {
		Test category = new Test();
	
		return category;
	}

}
