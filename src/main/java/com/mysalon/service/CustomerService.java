package com.mysalon.service;

import java.util.List;

import com.mysalon.entity.Customer;
import com.mysalon.exception.DuplicateCustomerException;
import com.mysalon.exception.NoCustomerFoundException;

public interface CustomerService {
	
	Customer addCustomer(Customer customer) throws DuplicateCustomerException;

	void removeCustomer(long userId) throws NoCustomerFoundException;

	Customer updateCustomer(long userId, Customer customer) throws NoCustomerFoundException;

	Customer getCustomer(long userId) throws NoCustomerFoundException;

	List<Customer> getAllCustomers() throws NoCustomerFoundException;
	
}
