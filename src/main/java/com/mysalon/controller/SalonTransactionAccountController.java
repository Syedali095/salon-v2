//package com.mysalon.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.mysalon.entity.SalonTransactionAccount;
//import com.mysalon.service.SalonTransactionAccountService;
//
//@RestController
//@RequestMapping("/transactions")
//public class SalonTransactionAccountController {
//	
//	@Autowired
//	private SalonTransactionAccountService salonTransactionAccountService;
//	
//	public ResponseEntity<List<SalonTransactionAccount>> getAllTransactions(){
//		List<SalonTransactionAccount> transactionList = salonTransactionAccountService.getAllTransactions();
//		ResponseEntity<List<SalonTransactionAccount>> responseEntity = new ResponseEntity<>(transactionList, HttpStatus.OK);
//		return responseEntity;
//	}
//}
