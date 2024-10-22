package com.mysalon.entity;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysalon.constantconfig.MapToJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "receipt_tbl")
public class FinalPriceReceipt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "receipt_id")
	private Long receiptId;
	
	@Column(name = "name")
	private String name;
	
	@Convert(converter = MapToJsonConverter.class)
	@Column(name = "service_details")
	private Map<String, BigDecimal> serviceDetails; // serviceName and price
	
	@Column(name = "total_price")
	private BigDecimal totalPrice;
	
	@Column(name = "final_price")
	private BigDecimal finalPrice;
	
	@JsonIgnore
	@OneToOne(mappedBy = "finalPriceReceipt")
	private Appointment appointment;

	public FinalPriceReceipt() {
		super();
	}
}
