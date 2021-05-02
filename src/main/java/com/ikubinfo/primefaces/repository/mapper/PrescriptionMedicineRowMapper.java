package com.ikubinfo.primefaces.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ikubinfo.primefaces.model.Medicine;
import com.ikubinfo.primefaces.model.Prescription;
import com.ikubinfo.primefaces.model.PrescriptionMedicine;

public class PrescriptionMedicineRowMapper implements RowMapper<PrescriptionMedicine> {

	@Override
	public PrescriptionMedicine mapRow(ResultSet rs, int rowNum) throws SQLException {
		PrescriptionMedicine pm= new PrescriptionMedicine();
		pm.setPrescriptionMedicineId(rs.getInt("prescription_medicine_id"));
		Medicine medicine=new Medicine();
		medicine.setName(rs.getString("name"));
		pm.setMedicine(medicine);
		pm.setDose(rs.getString("dose"));
		pm.setDuration(rs.getString("duration"));
		Prescription prescription=new Prescription();
		prescription.setAppointmentId(rs.getInt("appointment_id"));
		pm.setPrescription(prescription);
		return pm;
	}
}
