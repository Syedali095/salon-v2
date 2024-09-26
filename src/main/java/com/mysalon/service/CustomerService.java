package com.mysalon.service;

import java.util.List;
import com.mysalon.entity.Customer;
import com.mysalon.exception.DuplicateCustomerException;
import com.mysalon.exception.NoCustomerFoundException;

public interface CustomerService {
	
	Customer addCustomer(Customer customer) throws DuplicateCustomerException;

	void removeCustomer(Long custId) throws NoCustomerFoundException;

	Customer updateCustomer(Long custId, Customer customer) throws NoCustomerFoundException;

	Customer getCustomer(Long userId) throws NoCustomerFoundException;

	List<Customer> getAllCustomers();
	
	Customer getCustomerByNameAndEmail(String name, String email) throws NoCustomerFoundException;
	
	Customer getCustomerByNameAndAddress_State(String name, String email) throws NoCustomerFoundException;
	
}
