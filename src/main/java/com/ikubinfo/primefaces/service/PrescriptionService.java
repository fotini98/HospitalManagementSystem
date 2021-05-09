package com.ikubinfo.primefaces.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikubinfo.primefaces.model.Appointment;
import com.ikubinfo.primefaces.model.Medicine;
import com.ikubinfo.primefaces.model.Prescription;
import com.ikubinfo.primefaces.model.PrescriptionMedicine;
import com.ikubinfo.primefaces.repository.AppointmentRepository;
import com.ikubinfo.primefaces.repository.MedicineRepository;
import com.ikubinfo.primefaces.repository.PrescriptionRepository;

@Service
public class PrescriptionService {
	
	@Autowired
	PrescriptionRepository dao;
	
	@Autowired
	AppointmentRepository appointmentDao;
	
	@Autowired
	MedicineRepository medicineDao;
	
	public List<Prescription> getAllPrescriptions(){
		return dao.getAllPrescriptions();
	}
	
	public List<Prescription> getAllDocPrescriptions(long doctorId){
		return dao.getAllDocPrescriptions(doctorId);
	}
	@Transactional
	public boolean insertPrescription(List<PrescriptionMedicine> prescriptionMedicines, int appointmentId) {
		if(appointmentId!=0 && prescriptionMedicines!=null) {
			 int prescriptionId=dao.addPrescription(appointmentId);	
			    
				for(PrescriptionMedicine prescriptionMedicine: prescriptionMedicines ) {
					prescriptionMedicine.setPrescriptionId(prescriptionId);
					prescriptionMedicine.setMedicineId(medicineDao.getMedicineId(prescriptionMedicine.getMedicine()));
					 dao.insertPrescriptionMedicine(prescriptionMedicine);
				}
		
				appointmentDao.updateAppointmentStatus(new Appointment(appointmentId, "Completed"));
				return true;
		}
	    
		return false;
		 
	}
	public List<Medicine> getAllMedicines(){
		return medicineDao.getAllMedicines();
	}
	
	public List<PrescriptionMedicine> getPrescriptionMedicine(long appointmentId){
		
		try {
			return dao.getPrescriptionMedicine(appointmentId);
			
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public void updatePrescriptionMedicine(List<PrescriptionMedicine> prescriptionMedicines, int appointmentId) {
		int prescriptionId=dao.getPrescriptionId(appointmentId);
		System.out.println(prescriptionId+"from update prescription Medicine"+prescriptionMedicines);
		for(PrescriptionMedicine prescriptionMedicine: prescriptionMedicines ) {
			prescriptionMedicine.setPrescriptionId(prescriptionId);
			prescriptionMedicine.setMedicineId(medicineDao.getMedicineId(prescriptionMedicine.getMedicine()));
			 dao.updatePrescriptionMedicine(prescriptionMedicine);
		}
}

	public boolean deletePrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
		return dao.deletePrescriptionMedicine(prescriptionMedicine);
	}
	public int getPrescriptionId(int appointmentId) {
		try {
			return dao.getPrescriptionId(appointmentId);
		}catch(EmptyResultDataAccessException e) {
			return 0;
		}
		
	}

}
