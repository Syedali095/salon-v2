package com.mysalon.service;

import java.util.List;

import com.mysalon.entity.SalonService;

public interface SalonServiceClass {
	SalonService addSalonService(SalonService salonservice);
	
	SalonService getSalonServiceById(Long serviceId);
	
	List<SalonService> getAllSalonServices();
	
	SalonService getSalonServiceByName(String serviceName);
	
	List<SalonService> getSalonServiceByPrice(String servicePrice);
	
	List<SalonService> getSalonServiceByAppointmentId(Long appointmentId);
	
	SalonService updateSalonServiceById(Long serviceId, SalonService salonservice);
	
	void deleteSalonServiceById(Long serviceId);
	
	//List of services of an appointment
}
