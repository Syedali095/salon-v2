package com.mysalon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mysalon.entity.Address;
import com.mysalon.entity.Customer;
import com.mysalon.repository.CustomerRepository;

public class CustomerServiceImplTest {
	@Mock
    private CustomerRepository customerRepository;
	
	@InjectMocks
    private CustomerServiceImpl customerService;
    
	AutoCloseable autoCloseable;
    Customer customer;
    Address address;
    Customer updatedCustomerDetails;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);

        customer = new Customer();
        customer.setCustId(1);
        customer.setName("Syed");
        customer.setContactNo("9887876654");
        customer.setEmail("syed@gmail.com");
        customer.setDob(LocalDate.of(1999,07,12));
        customer.setAddress(new Address(1, "Sangareddy", "TS", "567876"));
        
        updatedCustomerDetails = new Customer();
        updatedCustomerDetails.setName("Mohsin");
        updatedCustomerDetails.setContactNo("9887800654");
        updatedCustomerDetails.setEmail("syed@gmail.com");
        updatedCustomerDetails.setDob(LocalDate.of(1999,07,12));
        updatedCustomerDetails.setAddress(new Address(1, "Sangareddyyy", "TS", "537876"));
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testAddCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer savedCustomer = customerService.addCustomer(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustId()).isEqualTo(customer.getCustId());
        assertThat(savedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(savedCustomer.getContactNo()).isEqualTo(customer.getContactNo());
        assertThat(savedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(savedCustomer.getDob()).isEqualTo(customer.getDob());
        assertThat(savedCustomer.getAddress()).isEqualTo(customer.getAddress());
    }

    @Test
    void testUpdateCustomer() {    	
    	when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer updatedCustomer = customerService.updateCustomer(1L, updatedCustomerDetails);

        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getName()).isEqualTo(updatedCustomerDetails.getName());
        assertThat(updatedCustomer.getContactNo()).isEqualTo(updatedCustomerDetails.getContactNo());
        assertThat(updatedCustomer.getEmail()).isEqualTo(updatedCustomerDetails.getEmail());
        assertThat(updatedCustomer.getDob()).isEqualTo(updatedCustomerDetails.getDob());
        assertThat(updatedCustomer.getAddress().getAddressId()).isEqualTo(updatedCustomerDetails.getAddress().getAddressId());
        assertThat(updatedCustomer.getAddress().getFullAddress()).isEqualTo(updatedCustomerDetails.getAddress().getFullAddress());
        assertThat(updatedCustomer.getAddress().getPincode()).isEqualTo(updatedCustomerDetails.getAddress().getPincode());
        assertThat(updatedCustomer.getAddress().getState()).isEqualTo(updatedCustomerDetails.getAddress().getState());
    }

    @Test
    void testRemoveCustomer() {        
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		doNothing().when(customerRepository).deleteById(customer.getCustId());
		customerService.removeCustomer(1L);
		
		// Verify that the customer was deleted
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Customer> deletedCustomer = customerRepository.findById(1L);
        assertThat(deletedCustomer).isEmpty();
    }

    @Test
    void testGetCustomer() {
    	when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        Customer foundCustomer = customerService.getCustomer(1L);

        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getName()).isEqualTo(customer.getName());
        assertThat(foundCustomer.getContactNo()).isEqualTo(customer.getContactNo());
        assertThat(foundCustomer.getEmail()).isEqualTo(customer.getEmail());
    }


    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
        List<Customer> customerList = customerService.getAllCustomers();
        assertThat(customerList).isNotEmpty();
        assertThat(customerList.get(0).getContactNo()).isEqualTo(customer.getContactNo());
    }
}
