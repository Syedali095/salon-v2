package com.mysalon.entity;

import java.time.LocalDate;
import java.util.List;
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

	@NotBlank(message = "Please enter the Name") // First Add validations dependency in pom.xml
	@Size(min = 3, max = 15, message = "Enter the Name with atleast 3 Alphabets") // message is optional in all validators
	@Column(name = "name")
	private String name;

	@NotBlank(message = "Please enter the Contact")
	@Pattern(regexp = "[789]{1}[0-9]{9}", message = "Please enter a valid Contact number") // or without message @Max(10)
	@Column(name = "contact_no")
	private String contactNo;

	@NotBlank(message = "Please enter the Email")
	@Email
	@Column(name = "email")
	private String email;

	@NotNull
	@Past
	@Column(name = "dob")
	private LocalDate dob;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	@Valid // ensures nested validation And triggers validation of the associated Address entity
	private Address address;

	// One customer can have multiple appointments
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Appointment> appointments;

	// One Customer can have 1 card
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "card_id") // This column will be the foreign key in customer_tbl
	private Card card;

	// One customer can have multiple orders
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> order;

	// For Test cases
	public Customer(Long custId,
			@NotBlank(message = "Please enter the Name") @Size(min = 3, max = 15, message = "Please enter the Name") String name,
			@NotBlank(message = "Please enter the Contact") @Pattern(regexp = "[789]{1}[0-9]{9}", message = "Please enter a valid Contact number") String contactNo,
			@NotBlank(message = "Please enter the Email") @Email String email, @NotNull @Past LocalDate dob,
			@Valid Address address) {
		super();
		this.custId = custId;
		this.name = name;
		this.contactNo = contactNo;
		this.email = email;
		this.dob = dob;
		this.address = address;
	}
}
