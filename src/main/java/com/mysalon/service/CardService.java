package com.mysalon.service;

import java.util.List;

import com.mysalon.entity.Card;

public interface CardService {
	
	Card saveCard(Long custId, Card card);
	
	Card getCardById(Long cardId);
	
	Card getCardDetails(String cardNumber);
	
	Card getCardDetailsByCustomerId(Long custId);
	
	void deleteCard(Long cardId);
	
	List<Card> getAll();
}
