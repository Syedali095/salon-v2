package com.mysalon.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CardDto {
	private String cardHolderName;
	private String cardNumber;
	private String expiryDate;
	private BigDecimal balance; //*This should not be shown
}
