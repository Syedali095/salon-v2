package com.mysalon.entity;

import java.time.LocalDate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customer_tbl")
public class Customer extends User{
	@NotBlank(message = "Please enter the Name") //First Add validations dependency in pom.xml then it will work
	@Column(name="name")
	private String name;
	
	@NotBlank(message = "Please enter the Contact")
	@Column(name="contact_no")
	private String contactNo;
	
	@NotBlank(message = "Please enter the Email")
	@Column(name="email")
	private String email;
	
	@NotNull
	@Column(name="dob")
	private LocalDate dob;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
}
