package com.mysalon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mysalon.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Optional<Payment> findByAppointment_AppointmentId(Long appointmentId);
	
	List<Payment> findByAppointment_Customer_CustId(Long custId);
}
