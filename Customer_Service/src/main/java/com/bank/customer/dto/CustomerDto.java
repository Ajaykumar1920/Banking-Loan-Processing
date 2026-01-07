package com.bank.customer.dto;

import java.time.LocalDate;

public class CustomerDto {
    private String customerName;
    private LocalDate dateOfBirth;
    private int age;
    private String gender;
    private String mobileNumber;
    private String email;
    private String address;
    private String maritalStatus;
    private String nationality;
    private String bankAccountNumber;
    private String ifscCode;
	public CustomerDto(String customerName, LocalDate dateOfBirth,int age, String gender, String mobileNumber, String email,
			String address, String maritalStatus, String nationality, String bankAccountNumber, String ifscCode) {
		this.customerName = customerName;
		this.dateOfBirth = dateOfBirth;
		this.age=age;
		this.gender = gender;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.address = address;
		this.maritalStatus = maritalStatus;
		this.nationality = nationality;
		this.bankAccountNumber = bankAccountNumber;
		this.ifscCode = ifscCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	

    
}

