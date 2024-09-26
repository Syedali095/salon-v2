//package com.mysalon.service;
//
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mysalon.entity.SalonTransactionAccount;
//import com.mysalon.exception.BadRequestException;
//import com.mysalon.repository.SalonTransactionAccountRepository;
//
//@Service
//public class SalonTransactionAccountServiceImpl implements SalonTransactionAccountService {
//
//	@Autowired
//	private SalonTransactionAccountRepository salonTransactionAccountRepository;
//	
//	@Override
//	public List<SalonTransactionAccount> getAllTransactions() {
//		List<SalonTransactionAccount> salonTransactions = salonTransactionAccountRepository.findAll();
//		if(salonTransactions.isEmpty()) {
//			throw new BadRequestException("No transactions available");
//		}
//		return salonTransactions;
//	}
//
//}
