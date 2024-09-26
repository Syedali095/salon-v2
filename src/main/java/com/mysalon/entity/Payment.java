package com.mysalon.entity;

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
import lombok.Data;

@Data
@Entity
@Table(name = "payment_tbl")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "payment_id")
	private Long paymentId;
	
	@Column(name= "payment_type")
	private String paymentType;
	
	@Column(name= "amount")
	private String amount;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "card_id")
	private Card card;
	
	@JsonIgnore
	@OneToOne(mappedBy = "payment")
	private Appointment appointment;
	
//	@JsonIgnore
//	@OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
//	private SalonTransactionAccount salonTransactionAccount;
}
