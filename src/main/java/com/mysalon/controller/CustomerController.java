package com.mysalon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.entity.Customer;
import com.mysalon.exception.DuplicateCustomerException;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/save")
	public ResponseEntity<Customer> addCustomerC(@Valid @RequestBody Customer customer) throws DuplicateCustomerException{
		Customer newCustomer = customerService.addCustomer(customer);
		ResponseEntity<Customer> responseEntity = new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
		return responseEntity;
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> removeCustomerC(@Valid @PathVariable long userId) throws NoCustomerFoundException{
		customerService.removeCustomer(userId);
		ResponseEntity<String> responseEntity = new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
		return responseEntity;
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<Customer> updateCustomerC(@Valid @PathVariable long userId, @RequestBody Customer customer) throws NoCustomerFoundException{
		Customer newCustomer = customerService.updateCustomer(userId, customer);
		ResponseEntity<Customer> responseEntity = new ResponseEntity<>(newCustomer, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Customer> getCustomerC(@Valid @PathVariable long userId) throws NoCustomerFoundException{
		Customer newCustomer = customerService.getCustomer(userId);
		ResponseEntity<Customer> responseEntity = new ResponseEntity<>(newCustomer, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Customer>> getAllCustomerC() throws NoCustomerFoundException{
		List<Customer> allCustomers = customerService.getAllCustomers();
		ResponseEntity<List<Customer>> responseEntity = new ResponseEntity<>(allCustomers, HttpStatus.OK);
		return responseEntity;
	}
}
