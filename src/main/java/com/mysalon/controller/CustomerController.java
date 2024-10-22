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
		return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long userId) {
		Customer newCustomer = customerService.getCustomer(userId);
		return new ResponseEntity<>(newCustomer, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<Customer> getCustomerByNameAndEmail(@RequestParam String name, @RequestParam String email) {
		Customer newCustomer = customerService.getCustomerByNameAndEmail(name, email);
		return new ResponseEntity<>(newCustomer, HttpStatus.OK);
	}

	@GetMapping("/search1")
	public ResponseEntity<Customer> getCustomerByNameAndState(@RequestParam String name, @RequestParam String state) {
		Customer newCustomer = customerService.getCustomerByNameAndAddress_State(name, state);
		return new ResponseEntity<>(newCustomer, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> allCustomers = customerService.getAllCustomers();
		return new ResponseEntity<>(allCustomers, HttpStatus.OK);
	}

	@PutMapping("/update/{custId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long custId, @Valid @RequestBody Customer customer) {
		Customer updatedCustomer = customerService.updateCustomer(custId, customer);
		return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
	}

	@PatchMapping("/patch/{custId}")
	public ResponseEntity<Customer> patchCustomer(@PathVariable Long custId, @Valid @RequestBody Customer customer) {
		Customer updatedCustomer = customerService.updateCustomer(custId, customer);
		return new ResponseEntity<>(updatedCustomer, HttpStatus.OK); // 200 OK
	}

	@DeleteMapping("/delete/{custId}")
	public ResponseEntity<String> removeCustomer(@PathVariable Long custId) {
		customerService.removeCustomer(custId);
		return new ResponseEntity<>("Customer deleted successfully", HttpStatus.NO_CONTENT);
	}
}
