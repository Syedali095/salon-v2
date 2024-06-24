package com.mysalon.service;

import java.util.List;
import com.mysalon.entity.Customer;
import com.mysalon.exception.BadRequestException;
import com.mysalon.exception.DuplicateCustomerException;
import com.mysalon.exception.NoCustomerFoundException;

public interface CustomerService {
	
	Customer addCustomer(Customer customer) throws DuplicateCustomerException;

	void removeCustomer(long custId) throws BadRequestException;

	Customer updateCustomer(long custId, Customer customer) throws NoCustomerFoundException;

	Customer getCustomer(long userId) throws NoCustomerFoundException;

	List<Customer> getAllCustomers() throws NoCustomerFoundException;
	
}
