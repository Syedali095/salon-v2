package com.mysalon.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.entity.Address;
import com.mysalon.entity.Customer;
import com.mysalon.exception.BadRequestException;
import com.mysalon.exception.DuplicateCustomerException;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Override
	public Customer addCustomer(Customer customer) throws DuplicateCustomerException{
		Optional<Customer> customer1 = customerRepository.findById(customer.getCustId());
		if(customer1.isPresent()) {
			throw new DuplicateCustomerException("Customer already exists");
		}
		Customer newCustomer = customerRepository.save(customer);
		return newCustomer;
	}

//	@Override
//	public void removeCustomer(long custId) throws NoCustomerFoundException {
//		Optional<Customer> customer1 = customerRepository.findById(custId);
//		if(customer1.isPresent()) {
//			customerRepository.deleteById(custId);
//		} else {
//			throw new NoCustomerFoundException("Customer does not exist");
//		}	
//	}
	
	@Override
	public void removeCustomer(long custId) throws BadRequestException {
		Optional<Customer> customer1 = customerRepository.findById(custId);
		if(customer1.isPresent()) {
			customerRepository.deleteById(custId);
		} else {
			throw new BadRequestException("No Customer exists with Customer ID " +custId);
		}	
	}

	@Override
	public Customer updateCustomer(long custId, Customer customer) throws NoCustomerFoundException{
		Optional<Customer> customer1 = customerRepository.findById(custId);
		if(customer1.isEmpty()) {
			throw new NoCustomerFoundException("Customer does not exist");
		}
		Customer existingCustomer = customer1.get();
		
		if(customer.getName() != null) {
			existingCustomer.setName(customer.getName());
		} else {
			existingCustomer.setName(existingCustomer.getName());
			}
		//Similar if-else for below 3
		existingCustomer.setContactNo(customer.getContactNo());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setDob(customer.getDob());
		
		Address updatedAddress = customer.getAddress();
		
		if(updatedAddress != null) {
			Address existingAddress = existingCustomer.getAddress();
			
			if(updatedAddress.getFullAddress() != null) {
				existingAddress.setFullAddress(updatedAddress.getFullAddress());
			} else {
				existingAddress.setFullAddress(existingAddress.getFullAddress());
				}

			if(updatedAddress.getState() != null) {
				existingAddress.setState(updatedAddress.getState());
			} else {
				existingAddress.setState(existingAddress.getState());
				}

			if(updatedAddress.getPincode() != null) {
				existingAddress.setPincode(updatedAddress.getPincode());
			} else {
				existingAddress.setPincode(existingAddress.getPincode());
				}
			}
		Customer newCustomer = customerRepository.save(existingCustomer);
		return newCustomer;
	}
	
	
	@Override
	public Customer getCustomer(long userId) throws NoCustomerFoundException {
		Optional<Customer> customer1 = customerRepository.findById(userId);
		if(customer1.isEmpty()) {
			throw new NoCustomerFoundException("Customer with UserID " +userId +" does not exist");
		}
		Customer newCustomer = customer1.get();
		return newCustomer;
	}

	@Override
	public List<Customer> getAllCustomers() throws NoCustomerFoundException {
		List<Customer> customerList = customerRepository.findAll();
		if(customerList.isEmpty()) {
			throw new NoCustomerFoundException("Customer does not exist");
		}
		return customerList;
	}

}
