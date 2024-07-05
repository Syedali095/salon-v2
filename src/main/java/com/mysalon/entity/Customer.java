package com.mysalon.entity;

import java.time.LocalDate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customer_tbl")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cust_id")
	private long custId;
	
	@NotBlank(message = "Please enter the Name") //First Add validations dependency in pom.xml
	@Size(min = 3, max = 15, message = "Please enter the Name") //message is optional in all validators
	@Column(name="name")
	private String name;
	
	@NotBlank(message = "Please enter the Contact")
	@Pattern(regexp = "[789]{1}[0-9]{9}", message = "Please enter a valid Contact number") //or without message @Max(10)
	@Column(name="contact_no")
	private String contactNo;
	
	@NotBlank(message = "Please enter the Email")
	@Email
	@Column(name="email")
	private String email;
	
	@NotNull
	@Past
	@Column(name="dob")
	private LocalDate dob;
	
	public long getCustId() {
		return custId;
	}
	public void setCustId(long custId) {
		this.custId = custId;
	}
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
	@Valid //ensures nested validation
	private Address address;

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
