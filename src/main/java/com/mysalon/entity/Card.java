package com.mysalon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "card")
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	private Long cardId;
	
	@Column(name = "card_name")
	private String cardName;
	
	@Column(name = "card_number")
	private String cardNumber;
	
	@JsonIgnore
	private String balance;
	
	@Column(name = "expiry_date")
	@Pattern(regexp = "(0[1-9]|1[0-2])/\\d{4}", message = "Expiry date must be in the format MM/YYYY")
	private String expiryDate;
	
	@JsonIgnore
	@OneToOne(mappedBy = "card")
	@JoinColumn(name = "customer_id", nullable = false)
	private Payment payment;
	
	@JsonIgnore
	@OneToOne(mappedBy = "card")
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
}
