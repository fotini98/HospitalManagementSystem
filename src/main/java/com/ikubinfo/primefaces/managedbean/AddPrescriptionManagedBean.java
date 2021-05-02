package com.ikubinfo.primefaces.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;


import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.ikubinfo.primefaces.model.Appointment;
import com.ikubinfo.primefaces.model.Medicine;
import com.ikubinfo.primefaces.model.Patient;
import com.ikubinfo.primefaces.model.PrescriptionMedicine;
import com.ikubinfo.primefaces.service.PrescriptionService;
import com.ikubinfo.primefaces.util.Messages;

@ManagedBean
@ViewScoped
public class AddPrescriptionManagedBean {
	
	private Appointment appointment;
	
	private Patient patient;
	
	@ManagedProperty(value = "#{prescriptionService}")
	private PrescriptionService prescriptionService;
	
	private List<PrescriptionMedicine> prescriptionMedicines;
	
	private PrescriptionMedicine prescriptionMedicine;
	
	private List<Medicine> medicines;
	
	private Medicine medicine;
	
	private int appointmentId;
	
	@ManagedProperty(value="#{messages}")
	private Messages message;
	
	@PostConstruct
	public void init() {
		appointmentId=Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		patient=new Patient();
		medicines= prescriptionService.getAllMedicines();
		prescriptionMedicine=new PrescriptionMedicine();
		medicine=new Medicine();
		prescriptionMedicines=prescriptionService.getPrescriptionMedicine(appointmentId);
		if(prescriptionMedicines==null) {
			prescriptionMedicines=new ArrayList<PrescriptionMedicine>();
		}
	    System.out.println(appointmentId);
	   
	    
	}
	
	
	public void addPrescription() {
		//System.err.println();
		System.err.println(prescriptionMedicines.toString());
	
		if(prescriptionService.getPrescriptionId(appointmentId)!=0) {
			List <PrescriptionMedicine> toBeDeleted=prescriptionService.getPrescriptionMedicine(appointmentId);
			for(PrescriptionMedicine prescriptionMedicine: toBeDeleted) {
			
				prescriptionService.deletePrescriptionMedicine(prescriptionMedicine);
			}
			prescriptionService.updatePrescriptionMedicine(prescriptionMedicines, appointmentId);
			message.showInfoMessage("Prescription Updated Successfully!");
			return;
		}
	     if(prescriptionService.insertPrescription(prescriptionMedicines, appointmentId)) {
			
			message.showInfoMessage("Prescription added Successfully!");
		}else {
			message.showFatalMessage("Something went wrong!!");
		}
		
	}
	 public void onRowEdit(RowEditEvent<PrescriptionMedicine> event) {
	        FacesMessage msg = new FacesMessage("Product Edited", String.valueOf(event.getObject().getPrescriptionMedicineId()));
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }

	    public void onCellEdit(CellEditEvent event) {
	        Object oldValue = event.getOldValue();
	        Object newValue = event.getNewValue();

	        if (newValue != null && !newValue.equals(oldValue)) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	    }
	    
	    public void addMedicines() {
	    	System.out.println(appointment);
	    	System.out.println(prescriptionMedicine);
	    	prescriptionMedicine.setMedicine(medicine);
	    	System.out.println(medicine.getName());
	    	this.prescriptionMedicines.add(prescriptionMedicine);
	    	this.prescriptionMedicine=new PrescriptionMedicine();
	    	this.medicine=new Medicine();
	    	
	    }
	    
	    public void remove() {
	    	for(PrescriptionMedicine prescriptionMedicine: prescriptionMedicines) {
	    		System.out.println(prescriptionMedicine);
	    		if(prescriptionMedicine.getMedicine().getName().equals(medicine.getName())) {
	    			prescriptionMedicines.remove(prescriptionMedicine);
	    			message.showInfoMessage("Medicine Removed!");
	    		}
	    	}
	    }


	public Appointment getAppointment() {
		return appointment;
	}


	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}


	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public PrescriptionService getPrescriptionService() {
		return prescriptionService;
	}
	public void setPrescriptionService(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}
	public List<PrescriptionMedicine> getPrescriptionMedicines() {
		return prescriptionMedicines;
	}
	public void setPrescriptionMedicines(List<PrescriptionMedicine> prescriptionMedicines) {
		this.prescriptionMedicines = prescriptionMedicines;
	}
	public List<Medicine> getMedicines() {
		return medicines;
	}
	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}
	public PrescriptionMedicine getPrescriptionMedicine() {
		return prescriptionMedicine;
	}
	public void setPrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
		this.prescriptionMedicine = prescriptionMedicine;
	}


	public Medicine getMedicine() {
		return medicine;
	}


	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}


	public int getAppointmentId() {
		return appointmentId;
	}


	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}


	public Messages getMessage() {
		return message;
	}


	public void setMessage(Messages message) {
		this.message = message;
	}
	
	

}
