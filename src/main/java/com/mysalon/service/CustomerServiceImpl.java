package com.mysalon.service;

import java.util.Collections;
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
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	// For Testing
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Customer addCustomer(Customer customer) {
		Optional<Customer> optionalCustomer = customerRepository.findByContactNo(customer.getContactNo());
		if (optionalCustomer.isPresent()) {
			throw new DuplicateCustomerException();
		}
		Customer newCustomer = customerRepository.save(customer);
		return newCustomer;
	}
	// ifPresent() or isPresent along with "!" can also be used.

	@Override
	public Customer getCustomer(Long userId) {
		return customerRepository.findById(userId)
				.orElseThrow(() -> new NoCustomerFoundException("Customer with UserID " + userId + " does not exist"));
	}

	@Override
	public Customer getCustomerByNameAndEmail(String name, String email) {
		return customerRepository.findByNameAndEmail(name, email)
				.orElseThrow(() -> new NoCustomerFoundException("Customer with Name " + name + " and Email " + email + " does not exist"));
	}

	@Override
	public Customer getCustomerByNameAndAddress_State(String name, String state) {
		return customerRepository.findByNameAndAddress_State(name, state)
				.orElseThrow(() -> new NoCustomerFoundException("Customer with Name " + name + " and State " + state + " does not exist"));
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customerList = customerRepository.findAll();
		if(customerList.isEmpty()) {
			return Collections.emptyList();
		}
		return customerList;
	}

	@Override
	public Customer updateCustomer(Long custId, Customer customer) {
		Customer existingCustomer = customerRepository.findById(custId)
				.orElseThrow(() -> new NoCustomerFoundException("No Customer exists with Customer ID " + custId));

		if (customer.getName() != null) {
			existingCustomer.setName(customer.getName());
		}
		if (customer.getContactNo() != null) {
			existingCustomer.setContactNo(customer.getContactNo());
		}
		if (customer.getEmail() != null) {
			existingCustomer.setEmail(customer.getEmail());
		}
		if (customer.getDob() != null) {
			existingCustomer.setDob(customer.getDob());
		}

		Address updatedAddress = customer.getAddress();

		if (updatedAddress != null) {
			Address existingAddress = existingCustomer.getAddress();

			if (updatedAddress.getFullAddress() != null) {
				existingAddress.setFullAddress(updatedAddress.getFullAddress());
			}
			if (updatedAddress.getState() != null) {
				existingAddress.setState(updatedAddress.getState());
			}
			if (updatedAddress.getPincode() != null) {
				existingAddress.setPincode(updatedAddress.getPincode());
			}
		}
		Customer newCustomer = customerRepository.save(existingCustomer);
		return newCustomer;
	}

	@Override
	public void removeCustomer(Long custId) {
		customerRepository.findById(custId)
				.orElseThrow(() -> new NoCustomerFoundException("No Customer exists with Customer ID " + custId));
		customerRepository.deleteById(custId);
	}
}
