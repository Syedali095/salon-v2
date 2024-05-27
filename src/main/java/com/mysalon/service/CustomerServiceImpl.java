package com.mysalon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.entity.Customer;
import com.mysalon.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Override
	public Customer addCustomer(Customer customer) {
		Customer newCustomer = customerRepository.save(customer);
		return newCustomer;
	}

	@Override
	public void removeCustomer(long userId) {
		customerRepository.deleteById(userId);
	}

	@Override
	public Customer updateCustomer(long userId, Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomer(long userId) {
		Optional<Customer> ocustomer =customerRepository.findById(userId);
		Customer customer = ocustomer.get();
		return customer;
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customer = customerRepository.findAll();
		return customer;
	}

}
