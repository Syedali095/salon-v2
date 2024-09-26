package com.mysalon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysalon.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Payment findByAppointment_AppointmentId(Long appointmentId);
	
	List<Payment> findByAppointment_Customer_CustId(Long custId);
	
}
