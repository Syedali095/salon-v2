package com.mysalon.dto;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class FinalPriceReceiptDto {
	private String name;
	private Map<String, BigDecimal> serviceDetails;
	private BigDecimal totalPrice;
	private BigDecimal finalPrice;
	
}
