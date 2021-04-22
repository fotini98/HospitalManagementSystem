package com.ikubinfo.primefaces.model;

import java.sql.Timestamp;
import java.util.Date;

public class Person {

	
	private long personId;
	private String fullName;
	private String email;
	private String address;
	private String phone;
	private Gender gender;
	private Date birthDate;
	private int age;
	private String password;
	private String createdBy;
	private Timestamp lastUpdated;
	private String modifiedBy;
	private boolean deleted; 
	

	
	public Person() {
		super();
	}
	public Person(String fullName, String email, String address, String phone, Gender gender, Date birthDate, int age,
			String password, String createdBy, Timestamp lastUpdated, String modifiedBy) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.gender = gender;
		this.birthDate = birthDate;
		this.age = age;
		this.password = password;
		this.createdBy = createdBy;
		this.lastUpdated = lastUpdated;
		this.modifiedBy = modifiedBy;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	@Override
	public String toString() {
		return "Person [personId=" + personId + ", fullName=" + fullName + ", email=" + email + ", address=" + address
				+ ", phone=" + phone + ", gender=" + gender + ", birthDate=" + birthDate + ", age=" + age
				+ ", password=" + password + ", createdBy=" + createdBy + ", lastUpdated=" + lastUpdated
				+ ", modifiedBy=" + modifiedBy + ", deleted=" + deleted + "]";
	}
	
	
	
	
	
	

}
