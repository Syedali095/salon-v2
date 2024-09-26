package com.mysalon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.entity.Card;
import com.mysalon.service.CardService;

@RestController
@RequestMapping("/card")
public class CardController {
	@Autowired
	private CardService cardService;
	
	@PostMapping("/save")
	public ResponseEntity<Card> saveCard(@RequestBody Card card){
		Card newCard = cardService.saveCard(card);
		ResponseEntity<Card> responseEntity = new ResponseEntity<>(newCard, HttpStatus.CREATED);
		return responseEntity;
	}
	
	@GetMapping("/get")
	public ResponseEntity<Card> getCardDetails(@PathVariable String cardNumber){
		Card newCard = cardService.getCardDetails(cardNumber);
		ResponseEntity<Card> responseEntity = new ResponseEntity<>(newCard, HttpStatus.OK);
		return responseEntity;
	}
}
