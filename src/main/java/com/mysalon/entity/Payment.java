package com.mysalon.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_tbl")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long paymentId;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "amount")
	private BigDecimal amount;

	@JsonIgnore
	@OneToOne(mappedBy = "payment")
	private Card card;

	@JsonIgnore
	@OneToOne(mappedBy = "payment")
	private Appointment appointment;
}
