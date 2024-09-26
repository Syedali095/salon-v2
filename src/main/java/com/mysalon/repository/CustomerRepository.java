package com.mysalon.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mysalon.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	Optional<Customer> findByNameAndEmail(String name, String email);
	
	//Using Nested Property in Method Name
	Optional<Customer> findByNameAndAddress_State(String name, String state);
	
	Optional<Customer> findByContactNo(String contactNo);
}