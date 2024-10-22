package com.mysalon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysalon.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	Optional<Order> findByAppointment_AppointmentId(Long appointmentId);
	
	List<Order> findByCustomer_CustId(Long custId);
}
