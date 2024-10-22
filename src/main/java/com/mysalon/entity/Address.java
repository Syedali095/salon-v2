package com.mysalon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysalon.validations.ValidateState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "address_tbl")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Long addressId;

	@NotBlank(message = "Please enter the Address")
	@Size(min = 5, max = 30)
	@Column(name = "full_address")
	private String fullAddress;

	@NotBlank(message = "Please enter the State")
	@ValidateState
	@Column(name = "state")
	private String state;

	@NotBlank(message = "Please enter the Pincode")
	@Pattern(regexp = "\\d{6}", message = "Enter valid pincode")
	@Column(name = "pincode")
	private String pincode;

	@JsonIgnore
	@OneToOne(mappedBy = "address")
	private Customer customer;

	// Constructor without Customer field, used for Test class
	public Address(Long addressId, String fullAddress, String state,String pincode) {
		super();
		this.addressId = addressId;
		this.fullAddress = fullAddress;
		this.state = state;
		this.pincode = pincode;
	}

	public Address() {
		super();
	}
}
