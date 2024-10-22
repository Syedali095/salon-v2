package com.mysalon.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mysalon.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

	List<Appointment> findByCustomer_CustId(Long custId);
	
	List<Appointment> findByLocation(String location);
	
	List<Appointment> findByPreferredDate(LocalDate preferredDate);
	
	@Query(value = "SELECT * FROM appointment_tbl a WHERE a.customer_id = :custId AND a.preferred_date = :preferredDate "
			+ "AND a.preferred_time = :preferredTime AND a.location = :location", nativeQuery = true)
	List<Appointment> findAppointmentByAppointmentDetails(Long custId, LocalDate preferredDate, 
														LocalTime preferredTime, String location);
	
	/*
	If the above "Native SQL" approach is not used then by JPA naming convention, the below approach can be used:
	List<Appointment> findByCustomer_CustIdAndPreferredDateAndPreferredTimeAndLocation(
        Long custId, LocalDate preferredDate, LocalTime preferredTime, String location);
	*/
}