package com.mysalon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mysalon.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
