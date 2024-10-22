package com.mysalon.service;

import java.util.List;
import com.mysalon.entity.Customer;

public interface CustomerService {
	
	Customer addCustomer(Customer customer);

	Customer getCustomer(Long userId);

	Customer getCustomerByNameAndEmail(String name, String email);
	
	Customer getCustomerByNameAndAddress_State(String name, String email);
	
	List<Customer> getAllCustomers();
	
	Customer updateCustomer(Long custId, Customer customer);

	void removeCustomer(Long custId);
}
