package com.mysalon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mysalon.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long>{
	Optional<Card> findByCardNumber(String cardNumber);
	
	Optional<Card> findByCustomer_CustId(Long custId);
	/*	If we want to get the card by custId. This is used when there is only 1 card for 1 customer.
		If there are multiple cards associated with one customer, we need to fetch the card by cardId while meking the payment */
}
