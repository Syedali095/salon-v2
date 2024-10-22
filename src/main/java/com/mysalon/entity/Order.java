package com.mysalon.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_tbl")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long orderId;
	
	@Column(name="booking_date")
	private LocalDate bookingDate;
	
	@Column(name="booking_time")
	private LocalTime bookingTime;
	
	@Column(name="amount_paid")
	private BigDecimal amountPaid;
	
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cust_id", nullable = false)
	private Customer customer;
	
	@JsonIgnore
	@OneToOne(mappedBy = "order")
	private Appointment appointment;
}
