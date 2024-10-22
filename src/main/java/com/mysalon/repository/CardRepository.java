package com.mysalon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mysalon.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long>{
	
	Optional<Card> findByCardNumber(String cardNumber);
	
	Optional<Card> findByCustomer_CustId(Long custId);
}
