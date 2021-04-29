package com.ikubinfo.primefaces.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikubinfo.primefaces.model.Medicine;
import com.ikubinfo.primefaces.model.Prescription;
import com.ikubinfo.primefaces.model.PrescriptionMedicine;
import com.ikubinfo.primefaces.repository.PrescriptionRepository;

@Service
public class PrescriptionService {
	
	@Autowired
	PrescriptionRepository dao;
	
	public List<Prescription> getAllPrescriptions(){
		return dao.getAllPrescriptions();
	}
	
	public List<Prescription> getAllDocPrescriptions(long doctorId){
		return dao.getAllDocPrescriptions(doctorId);
	}
	@Transactional
	public void insertPrescription(List<PrescriptionMedicine> prescriptionMedicines, int appointmentId) {
	     int prescriptionId=dao.addPrescription(appointmentId);	
	    
		for(PrescriptionMedicine prescriptionMedicine: prescriptionMedicines ) {
			prescriptionMedicine.setPrescriptionId(prescriptionId);
			 dao.insertPrescriptionMedicine(prescriptionMedicine);
		}
		//return type boolean
		 
	}
	public List<Medicine> getAllMedicines(){
		return dao.getAllMedicines();
	}

}
