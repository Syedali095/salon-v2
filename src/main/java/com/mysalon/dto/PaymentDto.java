package com.mysalon.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentDto {
	private String paymentType;
	private BigDecimal amount;
}
