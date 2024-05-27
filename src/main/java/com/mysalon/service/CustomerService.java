package com.mysalon.service;

import java.util.List;

import com.mysalon.entity.Customer;

public interface CustomerService {
	
	Customer addCustomer(Customer customer);

	void removeCustomer(long userId);

	Customer updateCustomer(long userId, Customer customer);

	Customer getCustomer(long userId);

	List<Customer> getAllCustomers();
	
}
