package com.mysalon.service;

import java.util.List;

import com.mysalon.entity.Payment;
import com.mysalon.entity.PaymentDto;

public interface PaymentService {
	Payment savePayment(Payment payment);
	
	Payment getPaymentByAppointmentId(Long appointmentId);
	
	List<Payment> getPaymentListByCustomerId(Long custId);
	
	List<Payment> getAllPayments();
	
	Payment makePayment(PaymentDto paymentDto, Long cardId, Long salonCardId);
}
