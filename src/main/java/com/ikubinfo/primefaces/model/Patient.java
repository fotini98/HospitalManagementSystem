package com.ikubinfo.primefaces.model;

public class Patient extends Person{
	
	private long patientId;
	private long personId;
	private long bloodId;
	
	private Blood blood;
	
	
	public long getPatientId() {
		return patientId;
	}





	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}





	public long getPersonId() {
		return personId;
	}





	public void setPersonId(long personId) {
		this.personId = personId;
	}





	public long getBloodId() {
		return bloodId;
	}





	public void setBloodId(long bloodId) {
		this.bloodId = bloodId;
	}





	public Blood getBlood() {
		return blood;
	}





	public void setBlood(Blood blood) {
		this.blood = blood;
	}





	@Override
	public String toString() {
		return "Patient [getPersonId()=" + getPersonId() + ", getFullName()=" + getFullName() + ", getEmail()="
				+ getEmail() + ", getAddress()=" + getAddress() + ", getPhone()=" + getPhone() + ", getGender()="
				+ getGender() + ", getBirthDate()=" + getBirthDate() + ", getAge()=" + getAge() + ", getPassword()="
				+ getPassword() + ", getCreatedBy()=" + getCreatedBy() + ", getLastUpdated()=" + getLastUpdated()
				+ ", getModifiedBy()=" + getModifiedBy() + ", isDeleted()=" + isDeleted() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	

}
