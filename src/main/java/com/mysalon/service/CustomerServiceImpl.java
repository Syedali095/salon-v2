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
	
	//For Testing
	public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
	
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
	/*	Customer existingCustomer = customerRepository.findById(custId)
        .orElseThrow(() -> new NoCustomerFoundException("Customer does not exist"));	*/
	//customerRepository.findById(custId) returns an Optional<Customer>
	//The above code can be used to replace below 5 lines.
		
		Optional<Customer> customer1 = customerRepository.findById(custId);
		if(customer1.isEmpty()) {
			throw new NoCustomerFoundException("Customer does not exist");
		}
		Customer existingCustomer = customer1.get();
		
		if(customer.getName() != null) {
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
		
		if(updatedAddress != null) {
			Address existingAddress = existingCustomer.getAddress();
			
			if(updatedAddress.getFullAddress() != null) {
				existingAddress.setFullAddress(updatedAddress.getFullAddress());
			}
			if(updatedAddress.getState() != null) {
				existingAddress.setState(updatedAddress.getState());
			}
			if(updatedAddress.getPincode() != null) {
				existingAddress.setPincode(updatedAddress.getPincode());
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

	@Override
	public Customer getCustomerByNameAndEmail(String name, String email) throws NoCustomerFoundException {
		Optional<Customer> customer1 = customerRepository.findByNameAndEmail(name, email);
		if(customer1.isEmpty()) {
			throw new NoCustomerFoundException("Customer with Name " +name +" and Email " +email +" does not exist");
		}
		Customer newCustomer = customer1.get();
		return newCustomer;
	}

	@Override
	public Customer getCustomerByNameAndAddress_State(String name, String state) throws NoCustomerFoundException {
		Optional<Customer> customer1 = customerRepository.findByNameAndAddress_State(name, state);
		if(customer1.isEmpty()) {
			throw new NoCustomerFoundException("Customer with Name " +name +" and State " +state +" does not exist");
		}
		Customer newCustomer = customer1.get();
		return newCustomer;
	}
}
