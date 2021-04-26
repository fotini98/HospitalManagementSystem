package com.ikubinfo.primefaces.model;

public class PrescriptionMedicine {
	
	private int prescriptionId;
	private int medicineId;
	private int dose;
	private String duration;
	
	private Prescription prescription;
	private Medicine medicine;
	
	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	public int getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}
	public int getDose() {
		return dose;
	}
	public void setDose(int dose) {
		this.dose = dose;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Prescription getPrescription() {
		return prescription;
	}
	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}
	public Medicine getMedicine() {
		return medicine;
	}
	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
	
	
	

}
