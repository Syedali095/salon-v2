package com.mysalon.service;

import java.math.BigDecimal;
import java.util.List;
import com.mysalon.dto.PaymentDto;
import com.mysalon.entity.Payment;

public interface PaymentService {
	
	Payment makePayment(PaymentDto paymentDto, Long cardId, Long salonCardId);
	
	void reversePayment(Long paymentId, Long salonCardId); //write this
	
	Payment getPaymentByAppointmentId(Long appointmentId);
	
	List<Payment> getPaymentListByCustomerId(Long custId);
	
	List<Payment> getAllPayments();
	
	//void deletePayment(Long paymentId);
	
	void transaction(Long payerCardId, Long payeeCardId, BigDecimal amount);
	
}