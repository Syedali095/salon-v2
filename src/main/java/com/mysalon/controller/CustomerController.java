package com.mysalon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.entity.Customer;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.service.CustomerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/save")
	public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
		Customer newCustomer = customerService.addCustomer(customer);
		ResponseEntity<Customer> responseEntity = new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
		return responseEntity;
	}

	@DeleteMapping("/delete/{custId}")
	public ResponseEntity<String> removeCustomer(@PathVariable Long custId) throws NoCustomerFoundException {
		customerService.removeCustomer(custId);
		ResponseEntity<String> responseEntity = new ResponseEntity<>("Customer deleted successfully",
				HttpStatus.NO_CONTENT);// before I used .OK
		return responseEntity;
	}

	@PutMapping("/update/{custId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable long custId, @Valid @RequestBody Customer customer)
			throws NoCustomerFoundException {
		Customer updatedCustomer = customerService.updateCustomer(custId, customer);
		ResponseEntity<Customer> responseEntity = new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
		return responseEntity;
	}

	// PATCH for partial update
	@PatchMapping("/patch/{custId}")
	public ResponseEntity<Customer> patchCustomer(@PathVariable long custId, @Valid @RequestBody Customer customer)
			throws NoCustomerFoundException {
		Customer updatedCustomer = customerService.updateCustomer(custId, customer); // Reuses the same method
		return new ResponseEntity<>(updatedCustomer, HttpStatus.OK); // 200 OK
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long userId) throws NoCustomerFoundException {
		Customer newCustomer = customerService.getCustomer(userId);
		ResponseEntity<Customer> responseEntity = new ResponseEntity<>(newCustomer, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Customer>> getAllCustomers() throws NoCustomerFoundException {
		List<Customer> allCustomers = customerService.getAllCustomers();
		ResponseEntity<List<Customer>> responseEntity = new ResponseEntity<>(allCustomers, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/search")
	public ResponseEntity<Customer> getCustomerByNameAndEmail(@RequestParam String name,
			@RequestParam String email) throws NoCustomerFoundException {
		Customer newCustomer = customerService.getCustomerByNameAndEmail(name, email);
		ResponseEntity<Customer> responseEntity = new ResponseEntity<>(newCustomer, HttpStatus.OK);
		return responseEntity;
	}
	// In Postman use define name and email in params

	@GetMapping("/search1")
	public ResponseEntity<Customer> getCustomerByNameAndState(@RequestParam String name,
			@RequestParam String state) throws NoCustomerFoundException {
		Customer newCustomer = customerService.getCustomerByNameAndAddress_State(name, state);
		ResponseEntity<Customer> responseEntity = new ResponseEntity<>(newCustomer, HttpStatus.OK);
		return responseEntity;
	}
}

//Notes: Always use DTO. Avoid using Customer Entity directly.
