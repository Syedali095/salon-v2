package com.mysalon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.entity.Card;
import com.mysalon.service.CardService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/card")
public class CardController {
	
	@Autowired
	private CardService cardService;
	
	@PostMapping("/save/{custId}")
	public ResponseEntity<Card> saveCard(@PathVariable Long custId, @Valid @RequestBody Card card){
		Card newCard = cardService.saveCard(custId, card);
		return new ResponseEntity<>(newCard, HttpStatus.CREATED);
	}
	
	@GetMapping("/getByCardId/{cardId}")
	public ResponseEntity<Card> getCardDetailsByCardId(@PathVariable Long cardId){
		Card newCard = cardService.getCardById(cardId);
		return new ResponseEntity<>(newCard, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<Card> getCardDetails(@RequestParam String cardNumber){
		Card newCard = cardService.getCardDetails(cardNumber);
		return new ResponseEntity<>(newCard, HttpStatus.OK);
	}
	
	@GetMapping("/getByCustId/{custId}")
	public ResponseEntity<Card> getCardDetailsByCustId(@PathVariable Long custId){
		Card newCard = cardService.getCardDetailsByCustomerId(custId);
		return new ResponseEntity<>(newCard, HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Card>> getAllCards(){
		List<Card> cards = cardService.getAll();
		return new ResponseEntity<>(cards, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{custId}/{cardId}")
	public ResponseEntity<String> deleteCard(@PathVariable Long custId, @PathVariable Long cardId){
		cardService.deleteCard(custId, cardId);
		return new ResponseEntity<>("Card Deleted Successfully", HttpStatus.OK);
	}
}
