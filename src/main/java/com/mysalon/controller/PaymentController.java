package com.mysalon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.entity.Payment;
import com.mysalon.service.PaymentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	private PaymentService paymentService;
	
	@RequestMapping("/save")
	public ResponseEntity<Payment> savePayment(@Valid @RequestBody Payment payment){
		Payment newPayment = paymentService.savePayment(payment);
		ResponseEntity<Payment> responseEntity = new ResponseEntity<>(newPayment, HttpStatus.CREATED);
		return responseEntity;
	}
	
	@RequestMapping("/getByAppointmentId")
	public ResponseEntity<Payment> getPaymentByAppointmentId(@PathVariable Long appointmentId){
		Payment newPayment = paymentService.getPaymentByAppointmentId(appointmentId);
		ResponseEntity<Payment> responseEntity = new ResponseEntity<>(newPayment, HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping("/getByCustId")
	public ResponseEntity<List<Payment>> getPaymentByCustId(@PathVariable Long custId){
		List<Payment> newPayment = paymentService.getPaymentListByCustomerId(custId);
		ResponseEntity<List<Payment>> responseEntity = new ResponseEntity<>(newPayment, HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping("/all")
	public ResponseEntity<List<Payment>> getAllPaymentList(){
		List<Payment> newPayment = paymentService.getAllPayments();
		ResponseEntity<List<Payment>> responseEntity = new ResponseEntity<>(newPayment, HttpStatus.OK);
		return responseEntity;
	}
}
