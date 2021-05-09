package com.ikubinfo.primefaces.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ikubinfo.primefaces.model.Prescription;
import com.ikubinfo.primefaces.model.PrescriptionMedicine;
import com.ikubinfo.primefaces.service.PrescriptionService;
import com.ikubinfo.primefaces.util.Messages;

@ManagedBean
@ViewScoped
public class DoctorPrescriptionManagedBean {
	
	private Prescription prescription;
	private List<Prescription> prescriptions;
	
	@ManagedProperty(value = "#{prescriptionService}")
	private PrescriptionService prescriptionService;
	
	@ManagedProperty(value = "#{messages}")
	private Messages messages;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	private List<PrescriptionMedicine> prescriptionMedicines;
	@PostConstruct
	public void init() {
		
		prescription=new Prescription();
		prescriptions=prescriptionService.getAllDocPrescriptions(loginBean.getEmployee().getEmployeeId());
		
	}
	
	public void viewPrescription(){
		System.out.println("from view");
		prescriptionMedicines=prescriptionService.getPrescriptionMedicine(prescription.getAppointmentId());
		System.err.println(prescriptionMedicines+"from view");
	}
	

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public PrescriptionService getPrescriptionService() {
		return prescriptionService;
	}

	public void setPrescriptionService(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public List<PrescriptionMedicine> getPrescriptionMedicines() {
		return prescriptionMedicines;
	}

	public void setPrescriptionMedicines(List<PrescriptionMedicine> prescriptionMedicines) {
		this.prescriptionMedicines = prescriptionMedicines;
	}
	
	
	

}
