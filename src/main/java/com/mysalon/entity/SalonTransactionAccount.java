//package com.mysalon.entity;
//
//import java.math.BigDecimal;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//
//@Entity
//@Table(name = "transaction_tbl")
//@Data
//public class SalonTransactionAccount {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "trans_id")
//	private Long transId;
//	
//	@Column(name = "balance")
//	private BigDecimal balance;
//	
//	@OneToOne
//    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
//	private Payment payment;
//}
