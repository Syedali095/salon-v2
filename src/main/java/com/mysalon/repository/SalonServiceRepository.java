package com.mysalon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mysalon.entity.SalonService;

public interface SalonServiceRepository extends JpaRepository<SalonService, Long>{
	
	Optional<SalonService> findByServiceName(String serviceName);
	
	List<SalonService> findByServicePrice(String servicePrice);
	
	List<SalonService> findByAppointments_AppointmentId(Long appointmentId);
	
	//Used for AppointmentDto - To Fetch all the services with service name provided by the user.
	List<SalonService> findByServiceNameIn(List<String> serviceName);
	//"In" tells Spring Data JPA to create a query that matches any of the serviceName values in the provided list.
}
