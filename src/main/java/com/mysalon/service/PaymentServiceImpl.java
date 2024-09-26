package com.mysalon.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.entity.Appointment;
import com.mysalon.entity.Card;
import com.mysalon.entity.Customer;
import com.mysalon.entity.Payment;
import com.mysalon.entity.PaymentDto;
import com.mysalon.exception.BadRequestException;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.repository.AppointmentRepository;
import com.mysalon.repository.CardRepository;
import com.mysalon.repository.CustomerRepository;
import com.mysalon.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CardRepository cardRepository;
	
	@Override
	public Payment savePayment(Payment payment) {
		Optional<Payment> optional = paymentRepository.findById(payment.getPaymentId());
		if (optional.isPresent()) {
			throw new BadRequestException("Already Exist");
		}
		Payment newPayment = paymentRepository.save(payment);
		return newPayment;
	}

	@Override
	public Payment getPaymentByAppointmentId(Long appointmentId) {
		Optional<Appointment> appointment1 = appointmentRepository.findById(appointmentId);
		if (appointment1.isEmpty()) {
			throw new BadRequestException("No such appointment found");
		}
		Payment payment = paymentRepository.findByAppointment_AppointmentId(appointmentId);
		if (payment == null) { // To use isEmpty, make it optional here, PaymentService and PaymentRepository
			throw new BadRequestException("No Payment found");
		}
		return payment;
	}

	// Doubtful
	@Override
	public List<Payment> getPaymentListByCustomerId(Long custId) {
		Optional<Customer> customer1 = customerRepository.findById(custId);
		if (customer1.isEmpty()) {
			throw new NoCustomerFoundException("Customer with UserID " + custId + " does not exist");
		}
		List<Payment> paymentList = paymentRepository.findByAppointment_Customer_CustId(custId);
		if (paymentList.isEmpty()) {
			throw new BadRequestException("No Payment found");
		}
		return paymentList;
	}

	@Override
	public List<Payment> getAllPayments() {
		List<Payment> paymentList = paymentRepository.findAll();
		if (paymentList.isEmpty()) {
			throw new BadRequestException("Payment list is Empty");
		}
		return paymentList;
	}

	@Override
	public Payment makePayment(PaymentDto paymentDto, Long custCardId, Long salonCardId) {
		// Convert DTO to Payment entity
		Payment payment = new Payment();
		payment.setPaymentType(paymentDto.getPaymentType());
		
		final BigDecimal REQUIRED_FEE_AMOUNT = new BigDecimal("500");
		
		// Parse the payment amount from DTO. Exception if the Amount entered consist of a letter.
	    BigDecimal enteredAmount;
	    try {
	    	enteredAmount = new BigDecimal(paymentDto.getAmount());
	    } catch (NumberFormatException e) {
	        throw new BadRequestException("Invalid amount format: " + paymentDto.getAmount());
	    }
	    
	    // To check if the amount entered is less than the mentioned amount
	    if (enteredAmount.compareTo(REQUIRED_FEE_AMOUNT) != 0) {
	        throw new BadRequestException("Please enter the exact amount of 500/-");
	    }
		payment.setAmount(enteredAmount.toString());

		//Fetch customer's card ID
		Optional<Card> optionalCard = cardRepository.findById(custCardId);
		if (optionalCard.isEmpty()) {
			throw new BadRequestException("card doesnot exist");
		}
		Card customerCard = optionalCard.get();
		
		// Fetch the salon's main card
	    Optional<Card> optionalSalonCard = cardRepository.findById(salonCardId);
	    if (optionalSalonCard.isEmpty()) {
	        throw new BadRequestException("Salon card does not exist");
	    }
	    Card salonCard = optionalSalonCard.get();
		
		//Check for sufficient balance
		BigDecimal availableBalance = new BigDecimal(customerCard.getBalance());
		
		if (availableBalance.compareTo(enteredAmount) < 0) {
	        throw new BadRequestException("Insufficient balance in the card!");
	    }
		
		//Deduct the balance
		BigDecimal newAvailableBalance = availableBalance.subtract(enteredAmount);
		customerCard.setBalance(newAvailableBalance.toString());
		
		// Add the payment amount to the salon's main card
	    BigDecimal salonBalance = new BigDecimal(salonCard.getBalance());
	    BigDecimal newSalonBalance = salonBalance.add(enteredAmount);
	    salonCard.setBalance(newSalonBalance.toString());

	    // Update the cards' balances in the database
	    cardRepository.save(customerCard);
	    cardRepository.save(salonCard);
	    
		payment.setCard(customerCard);
		
		// Save the payment
		Payment newPayment = paymentRepository.save(payment);
		
		return newPayment;
	}

}
