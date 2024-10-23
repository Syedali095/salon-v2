package com.mysalon.dto;

import lombok.Data;

@Data
public class BookingRequestDto {
	private AppointmentDto appointmentDto;
    private PaymentDto paymentDto;
}
