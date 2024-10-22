package com.mysalon.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mysalon.entity.SalonService;

public interface SalonServiceRepository extends JpaRepository<SalonService, Long>{
	
	Optional<SalonService> findByServiceName(String serviceName);
	
	List<SalonService> findByServicePrice(BigDecimal servicePrice);
	
	List<SalonService> findByAppointments_AppointmentId(Long appointmentId);
	
	List<SalonService> findByServiceNameIn(List<String> serviceName);
	
	List<BigDecimal> findServicePriceByServiceNameIn(List<String> serviceNames);
}
