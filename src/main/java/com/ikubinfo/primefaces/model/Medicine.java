package com.ikubinfo.primefaces.model;

import java.io.Serializable;

public class Medicine implements Serializable{
	private int medicineId;
	private String name;
	private int strengeth;
	
	
	
	public Medicine() {
		super();
	}
	public Medicine(int medicineId, String name, int strengeth) {
		super();
		this.medicineId = medicineId;
		this.name = name;
		this.strengeth = strengeth;
	}
	public int getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStrengeth() {
		return strengeth;
	}
	public void setStrengeth(int strengeth) {
		this.strengeth = strengeth;
	}
	@Override
	public String toString() {
		return "Medicine [medicineId=" + medicineId + ", name=" + name + ", strengeth=" + strengeth + "]";
	}
	
	

}
