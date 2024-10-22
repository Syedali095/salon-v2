package com.mysalon.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class OrderDto {
	private LocalDate bookingDate;
	private LocalTime bookingTime;
	private BigDecimal amountPaid;
}
