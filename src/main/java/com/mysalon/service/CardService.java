package com.mysalon.service;

import com.mysalon.entity.Card;

public interface CardService {
	Card saveCard(Card card);
	
	Card getCardDetails(String cardNumber);
	
	void deleteCard(Long cardId);
	
	Card getCardDetailsByCustomerId(Long custId);
}
