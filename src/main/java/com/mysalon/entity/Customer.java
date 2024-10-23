package com.mysalon.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_tbl")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cust_id")
	private Long custId;

	@NotBlank(message = "Please enter the Name") 
	@Size(min = 3, max = 30, message = "Enter the Name with atleast 3 Alphabets")
	@Column(name = "name")
	private String name;

	@NotBlank(message = "Please enter the Contact")
	@Pattern(regexp = "[789]{1}[0-9]{9}", message = "Please enter a valid Contact number")
	@Column(name = "contact_no")
	private String contactNo;

	@NotBlank(message = "Please enter the Email")
	@Email
	@Column(name = "email")
	private String email;

	@NotNull //@NotEmpty and @NotBlank are not applicable on LocalDate
	@Past
	@Column(name = "dob")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dob;

	// 1 Customer can have 1 address
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	@Valid // ensures nested validation And triggers validation of the associated Address entity (To handle Exception)
	private Address address;

	// 1 Customer can have 1 card
	//@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "card_id") // This column will be the foreign key
	private Card card;
	
	// 1 customer can have MANY appointments
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Appointment> appointments;
	public void addAppointment(Appointment appointment) {
		appointments.add(appointment);
		appointment.setCustomer(this);
	}
	public void removeAppointment(Appointment appointment) {
		appointments.remove(appointment);
		appointment.setCustomer(null);
	}

	// 1 customer can have MANY orders
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders;
	public void addOrder(Order order) {
		orders.add(order);
		order.setCustomer(this);
	}
	public void removeOrder(Order order) {
		orders.remove(order);
		order.setCustomer(null);
	}
	
	// For Test cases
	public Customer(Long custId, String name,String contactNo, String email, LocalDate dob, Address address) {
		super();
		this.custId = custId;
		this.name = name;
		this.contactNo = contactNo;
		this.email = email;
		this.dob = dob;
		this.address = address;
	}

	public Customer() {
		super();
	}
}
