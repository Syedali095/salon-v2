package com.mysalon.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.entity.Card;
import com.mysalon.entity.Customer;
import com.mysalon.exception.BadRequestException;
import com.mysalon.exception.NoCardFoundException;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.repository.CardRepository;
import com.mysalon.repository.CustomerRepository;

@Service
public class CardServiceImpl implements CardService{
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Card saveCard(Long custId, Card card) {
		// Checks if customer exists
		Customer customer = customerRepository.findById(custId)
				.orElseThrow(() -> new NoCustomerFoundException("Customer with cust Id " + custId + " not found"));

		// Check if a card already exists for this customer
		Optional<Card> optional = cardRepository.findByCustomer_CustId(custId);
		if(optional.isPresent()) {
			throw new BadRequestException("Card Alredy Exist for this customer");
		}
		
		// Change the name to uppercase.
		card.setCardHolderName(card.getCardHolderName().toUpperCase());
		
		// Link card with the customer
		card.setCustomer(customer);
		
		// Link customer with the card - As it is bidirectional mapping
		customer.setCard(card);
		
		// Save the card
		Card newCard = cardRepository.save(card);
		
		//return the card
		return newCard;
	}
	
	@Override
	public Card getCardById(Long cardId) {
		return cardRepository.findById(cardId)
				.orElseThrow(() -> new NoCardFoundException("No card found with card Id" +cardId));
	}

	@Override
	public Card getCardDetails(String cardNumber) {
		return cardRepository.findByCardNumber(cardNumber)
				.orElseThrow(() -> new NoCardFoundException("No card found with card number " +cardNumber));
	}

	@Override
	public Card getCardDetailsByCustomerId(Long custId) {
		// Check if customer exists
		customerRepository.findById(custId)
		.orElseThrow(() -> new NoCustomerFoundException("Customer with cust Id " +custId +" not found"));
		
		// Retrieve card details by customer ID
		Card card = cardRepository.findByCustomer_CustId(custId)
		.orElseThrow(() -> new NoCardFoundException("Card Does not Exist for this customer"));
		
		return card;
	}

	@Override
	public void deleteCard(Long custId, Long cardId) {
		Card card = cardRepository.findById(cardId)
			.orElseThrow(() -> new NoCardFoundException("No card found with card Id" +cardId));
		
		Customer customer = customerRepository.findById(custId)
				.orElseThrow(()-> new NoCustomerFoundException("Customer Not Found with cust Id: " +custId));
		
		if (customer.getCard() != null && customer.getCard().equals(card)) {
			// Break the Relationship
			customer.setCard(null);	// Unlink the card
			customerRepository.save(customer);	// Save the changes
		}else {
	        throw new NoCardFoundException("The card does not belong to the customer with cust Id: " + custId);
	    }
	
		cardRepository.deleteById(cardId);
	}

	@Override
	public List<Card> getAll() {
		List<Card> cards = cardRepository.findAll();
		if(cards == null) {
			return Collections.emptyList();
		}
		return cards;
	}
}
