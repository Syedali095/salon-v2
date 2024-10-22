package com.mysalon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.entity.Payment;
import com.mysalon.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/appointmentId/{appointmentId}")
	public ResponseEntity<Payment> getPaymentByAppointmentId(@PathVariable Long appointmentId){
		Payment newPayment = paymentService.getPaymentByAppointmentId(appointmentId);
		return new ResponseEntity<>(newPayment, HttpStatus.OK);
	}
	
	@GetMapping("/custId/{custId}")
	public ResponseEntity<List<Payment>> getPaymentByCustId(@PathVariable Long custId){
		List<Payment> newPayment = paymentService.getPaymentListByCustomerId(custId);
		return new ResponseEntity<>(newPayment, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Payment>> getAllPaymentList(){
		List<Payment> newPayment = paymentService.getAllPayments();
		return new ResponseEntity<>(newPayment, HttpStatus.OK);
	}
}
//add getPaymentById