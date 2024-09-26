package com.mysalon.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.entity.Card;
import com.mysalon.entity.Customer;
import com.mysalon.exception.BadRequestException;
import com.mysalon.repository.CardRepository;
import com.mysalon.repository.CustomerRepository;

@Service
public class CardServiceImpl implements CardService{
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Card saveCard(Card card) {
		Optional<Card> optional = cardRepository.findById(card.getCardId());
		if(optional.isPresent()) {
			throw new BadRequestException("Card Alredy Exist");
		}
		Card newCard = cardRepository.save(card);
		return newCard;
	}

	@Override
	public Card getCardDetails(String cardNumber) {
		Optional<Card> optional = cardRepository.findByCardNumber(cardNumber);
		if(optional.isEmpty()) {
			throw new BadRequestException("Card Does not Exist");
		}
		Card newCard = optional.get();
		return newCard;
	}

	@Override
	public void deleteCard(Long cardId) {
		Optional<Card> optional = cardRepository.findById(cardId);
		if(optional.isEmpty()) {
			throw new BadRequestException("Card Does not Exist");
		}
		cardRepository.deleteById(cardId);
	}

	@Override
	public Card getCardDetailsByCustomerId(Long custId) {
		Optional<Customer> optional = customerRepository.findById(custId);
		if(optional.isEmpty()) {
			throw new BadRequestException("Customer Does not Exist");
		}
		
		Optional<Card> optionalCard = cardRepository.findByCustomer_CustId(custId);
		if(optionalCard.isEmpty()) {
			throw new BadRequestException("Card Does not Exist for this customer");
		}
		Card newCard = optionalCard.get();
		return newCard;
	}

}
