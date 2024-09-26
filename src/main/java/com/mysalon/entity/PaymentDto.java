package com.mysalon.entity;

import lombok.Data;

@Data
public class PaymentDto {
	private String paymentType;
	private String amount;
}
