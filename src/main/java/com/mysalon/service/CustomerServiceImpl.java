package com.mysalon.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.entity.Address;
import com.mysalon.entity.Customer;
import com.mysalon.exception.DuplicateCustomerException;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Override
	public Customer addCustomer(Customer customer) throws DuplicateCustomerException{
		Optional<Customer> customer1 = customerRepository.findById(customer.getUserId());
		if(customer1.isPresent()) {
			throw new DuplicateCustomerException();
		}
		Customer newCustomer = customerRepository.save(customer);
		return newCustomer;
	}

	@Override
	public void removeCustomer(long userId) throws NoCustomerFoundException {
		Optional<Customer> customer1 = customerRepository.findById(userId);
		if(customer1.isPresent()) {
			customerRepository.deleteById(userId);
		} else {
			
		}
		
	}

	@Override
	public Customer updateCustomer(long userId, Customer customer) throws NoCustomerFoundException{
		Optional<Customer> customer1 = customerRepository.findById(userId);
		if(customer1.isEmpty()) {
			throw new NoCustomerFoundException();
		}
		Customer existingCustomer = customer1.get();
		
		existingCustomer.setName(customer.getName());
		existingCustomer.setContactNo(customer.getContactNo());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setDob(customer.getDob());
		
		Address updatedAddress = customer.getAddress();
		
		if(updatedAddress != null) {
			Address existingAddress = existingCustomer.getAddress();
			if (existingAddress != null) {
				existingAddress.setFullAddress(updatedAddress.getFullAddress());
				existingAddress.setState(updatedAddress.getState());
				existingAddress.setPincode(updatedAddress.getPincode());
			} else {
				existingCustomer.setAddress(updatedAddress);
				}
		}
			Customer newCustomer = customerRepository.save(existingCustomer);
			return newCustomer;
	}

	@Override
	public Customer getCustomer(long userId) throws NoCustomerFoundException {
		Optional<Customer> customer1 = customerRepository.findById(userId);
		if(customer1.isEmpty()) {
			throw new NoCustomerFoundException();
		}
		Customer newCustomer = customer1.get();
		return newCustomer;
	}

	@Override
	public List<Customer> getAllCustomers() throws NoCustomerFoundException {
		List<Customer> customerList = customerRepository.findAll();
		if(customerList.isEmpty()) {
			throw new NoCustomerFoundException();
		}
		return customerList;
	}

}
