package com.ikubinfo.primefaces.model;

public class Patient extends Person{

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
