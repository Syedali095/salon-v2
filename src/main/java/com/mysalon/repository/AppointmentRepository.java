package com.mysalon.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mysalon.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

	List<Appointment> findByCustomer_CustId(Long custId); //Note*
	
	List<Appointment> findByLocation(String location);
	
	List<Appointment> findByPreferredDate(LocalDate preferredDate);
}

/* Note*:
 	findByCustomer_CustId Here we are defining like this because, In Appointment class we are having 
 	private Customer customer. So we want custId of that customer. Therefore we are using Customer_CustId
 */