package com.mysalon.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.constantconfig.AppointmentBookingFee;
import com.mysalon.dto.PaymentDto;
import com.mysalon.entity.Card;
import com.mysalon.entity.Payment;
import com.mysalon.exception.BadRequestException;
import com.mysalon.exception.InsufficientBalanceException;
import com.mysalon.exception.NoCardFoundException;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.exception.NoPaymentFoundException;
import com.mysalon.repository.CardRepository;
import com.mysalon.repository.CustomerRepository;
import com.mysalon.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CardRepository cardRepository;
//	@Autowired - This is a PaymentService Class, it should not be Autowired with itself.
	//private PaymentService paymentService;

	
	@Override
	public Payment makePayment(PaymentDto paymentDto, Long custCardId, Long salonCardId) {
		// Convert DTO to Payment entity
		Payment payment = new Payment();
		payment.setPaymentType(paymentDto.getPaymentType());

		final BigDecimal REQUIRED_FEE_AMOUNT = AppointmentBookingFee.getFEE();

		// Parse the payment amount from DTO. Check the Amount entered is a BigDecimal
		BigDecimal enteredAmount;
		try {
			enteredAmount = paymentDto.getAmount();
		} catch (NumberFormatException e) {
			throw new BadRequestException("Invalid amount format: " + paymentDto.getAmount());
		}

		// To check if the amount entered is less than the mentioned amount
		if (enteredAmount.compareTo(REQUIRED_FEE_AMOUNT) != 0) {
			throw new BadRequestException("Please enter the exact amount of 500/-");
		}
		payment.setAmount(enteredAmount);

		transaction(custCardId, salonCardId, enteredAmount);
		
		Optional<Card> optionalCard = cardRepository.findById(custCardId);
		Card customerCard = optionalCard.get();
		
		// Link the Payment to Card
		payment.setCard(customerCard);

		// Add the Payment to Card's Payment List - Bidirectional along with List
		customerCard.addPayment(payment);
		
		// Save and return the payment
		Payment newPayment = paymentRepository.save(payment);
		return newPayment;
	}

	@Override
	public void reversePayment(Long paymentId, Long salonCardId) {
		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new NoPaymentFoundException("No Payment found with Payment ID " + paymentId));

		BigDecimal paidAmount = payment.getAmount();
		Long custCardId = payment.getCard().getCardId();
		
		transaction(salonCardId, custCardId, paidAmount);
		
		Optional<Card> optionalCard = cardRepository.findById(custCardId);
		Card customerCard = optionalCard.get();
		
		// Remove Payment from Customer-Card's Payment list
		customerCard.removePayment(payment);
		
		paymentRepository.deleteById(paymentId);
	}

	@Override
	public void transaction(Long payerCardId, Long payeeCardId, BigDecimal amount) {
		// Fetch payer's card
		Card payerCard = cardRepository.findById(payerCardId)
				.orElseThrow(() -> new NoCardFoundException("Payer card does not exist"));

		// Fetch payee's card
		Card payeeCard = cardRepository.findById(payeeCardId)
				.orElseThrow(() -> new NoCardFoundException("Payee card does not exist"));

		// Check for sufficient balance in payer's card
		BigDecimal availableBalance = payerCard.getBalance();

		if (availableBalance.compareTo(amount) < 0) {
			throw new InsufficientBalanceException("Insufficient balance in the card!");
		}

		// Deduct the balance from payer card
		BigDecimal newAvailableBalance = availableBalance.subtract(amount);
		payerCard.setBalance(newAvailableBalance);

		// Add the payment amount to the payee card
		//BigDecimal salonBalance = salonCard.getBalance();
		BigDecimal newSalonBalance = payeeCard.getBalance().add(amount);
		payeeCard.setBalance(newSalonBalance);

		// Update the cards' balances in the database
		cardRepository.save(payerCard);
		cardRepository.save(payeeCard);
	}

	@Override
	public Payment getPaymentByAppointmentId(Long appointmentId) {
		return paymentRepository.findByAppointment_AppointmentId(appointmentId)
				.orElseThrow(() -> new NoPaymentFoundException("No Payment found with Appointment ID " + appointmentId));
	}

	@Override
	public List<Payment> getPaymentListByCustomerId(Long custId) {
		customerRepository.findById(custId)
				.orElseThrow(() -> new NoCustomerFoundException("Customer with custID " + custId + " does not exist"));

		List<Payment> paymentList = paymentRepository.findByAppointment_Customer_CustId(custId);
		if (paymentList.isEmpty()) {
			return Collections.emptyList();
		}
		return paymentList;
	}

	@Override
	public List<Payment> getAllPayments() {
		List<Payment> paymentList = paymentRepository.findAll();
		if (paymentList.isEmpty()) {
			return Collections.emptyList();
		}
		return paymentList;
	}

	@Override
	public Payment getPaymentById(Long paymentId) {
		return paymentRepository.findById(paymentId)
				.orElseThrow(() -> new NoPaymentFoundException("No Payment found with Payment ID " + paymentId));
	}

}
