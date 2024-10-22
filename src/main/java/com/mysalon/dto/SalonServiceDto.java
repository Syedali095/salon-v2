package com.mysalon.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SalonServiceDto {
	private String serviceName;
	private BigDecimal servicePrice;
	private String serviceDuration;
}
