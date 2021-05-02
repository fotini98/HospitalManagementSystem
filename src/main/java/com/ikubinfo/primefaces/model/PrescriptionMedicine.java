package com.ikubinfo.primefaces.model;

public class PrescriptionMedicine {
	
	private int prescriptionMedicineId;
	private int prescriptionId;
	private int medicineId;
	private String dose;
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
	
	
	public PrescriptionMedicine() {
		super();
	}
	public PrescriptionMedicine(int prescriptionMedicineId, int prescriptionId, int medicineId, String dose,
			String duration, Prescription prescription, Medicine medicine) {
		super();
		this.prescriptionMedicineId = prescriptionMedicineId;
		this.prescriptionId = prescriptionId;
		this.medicineId = medicineId;
		this.dose = dose;
		this.duration = duration;
		this.prescription = prescription;
		this.medicine = medicine;
	}

	public String getDose() {
		return dose;
	}
	public void setDose(String dose) {
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
	public int getPrescriptionMedicineId() {
		return prescriptionMedicineId;
	}
	public void setPrescriptionMedicineId(int prescriptionMedicineId) {
		this.prescriptionMedicineId = prescriptionMedicineId;
	}
	@Override
	public String toString() {
		return "PrescriptionMedicine [prescriptionMedicineId=" + prescriptionMedicineId + ", prescriptionId="
				+ prescriptionId + ", medicineId=" + medicineId + ", dose=" + dose + ", duration=" + duration
				+ ", prescription=" + prescription + ", medicine=" + medicine + "]";
	}
	
	
	

}
