package com.mysalon.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "card_tbl")
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	private Long cardId;
	
	@NotBlank(message = "Please enter the Name") 
	@Size(min = 3, max = 30, message = "Enter the Name with atleast 3 Alphabets")
	@Column(name = "card_name")
	private String cardHolderName;
	
	@NotBlank(message = "Please enter the Card Number") 
	@Pattern(regexp = "[0-9]{12}", message = "Please enter a valid Contact number")
	@Column(name = "card_number")
	private String cardNumber;
	
	@JsonIgnore
	@Column(name = "balance")
	private BigDecimal balance = new BigDecimal(1000.00);
	
	@NotBlank(message = "Please enter the Card Expiry Date")
	@Pattern(regexp = "(0[1-9]|1[0-2])/\\d{4}", message = "Expiry date must be in the format MM/YYYY")
	@Column(name = "expiry_date")
	private String expiryDate; //Use LocalDate with custom date formatter
	
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
	@JsonIgnore
	@OneToOne(mappedBy = "card")
	private Customer customer;
}
