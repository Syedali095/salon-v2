package com.mysalon.service;

import java.math.BigDecimal;
import java.util.List;

import com.mysalon.entity.SalonService;

public interface SalonServiceClass {
	
	SalonService addSalonService(SalonService salonservice);
	
	SalonService getSalonServiceById(Long serviceId);
	
	SalonService getSalonServiceByName(String serviceName);
	
	List<SalonService> getSalonServiceByPrice(BigDecimal servicePrice);
	
	List<SalonService> getSalonServiceByAppointmentId(Long appointmentId);
	
	List<SalonService> getAllSalonServices();
	
	SalonService updateSalonServiceById(Long serviceId, SalonService salonservice);
	
	void deleteSalonServiceById(Long serviceId);
	
}
