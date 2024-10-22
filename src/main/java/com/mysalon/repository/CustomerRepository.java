package com.mysalon.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mysalon.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	Optional<Customer> findByNameAndEmail(String name, String email);
	
	Optional<Customer> findByNameAndAddress_State(String name, String state);
	
	Optional<Customer> findByContactNo(String contactNo);
}