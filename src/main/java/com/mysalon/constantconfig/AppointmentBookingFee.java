package com.mysalon.constantconfig;

import java.math.BigDecimal;

public class AppointmentBookingFee {
	final static BigDecimal FEE = new BigDecimal("500");

	public static BigDecimal getFEE() {
		return FEE;
	}
}
/*
We are not writing the below line directly in makePayment method because, writing 
final BigDecimal REQUIRED_FEE_AMOUNT = new BigDecimal("500"); 
will lead to tight-coupling, to avoid it we are saving it in another class.
It is recommended to keep such constants outside the method, ideally in a configuration file. 
This will make it easier to maintain and modify later.
*/