package com.mysalon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysalon.entity.SalonService;
import com.mysalon.exception.BadRequestException;
import com.mysalon.repository.SalonServiceRepository;

@Service
public class SalonServiceClassImpl implements SalonServiceClass{

	@Autowired
	private SalonServiceRepository salonServiceRepository;
	@Override
	public SalonService addSalonService(SalonService salonService) {
		Optional<SalonService> optional = salonServiceRepository.findById(salonService.getServiceId());
		if(optional.isPresent()) {
			throw new BadRequestException("Service already exist with that ID");
		}
		SalonService newSalonService = salonServiceRepository.save(salonService);
		return newSalonService;
	}

	@Override
	public SalonService getSalonServiceById(Long serviceId) {
		Optional<SalonService> optional = salonServiceRepository.findById(serviceId);
		if(optional.isEmpty()) {
			throw new BadRequestException("Service does not exist");
		}
		SalonService newSalonService = optional.get();
		return newSalonService;
	}

	@Override
	public List<SalonService> getAllSalonServices() {
		List<SalonService> salonServiceList = salonServiceRepository.findAll();
		if(salonServiceList.isEmpty()) {
			throw new BadRequestException("No Services found");
		}
		return salonServiceList;
	}

	@Override
	public SalonService getSalonServiceByName(String serviceName) {
		Optional<SalonService> optional = salonServiceRepository.findByServiceName(serviceName);
		if(optional.isEmpty()) {
			throw new BadRequestException("Service does not exist");
		}
		SalonService newSalonService = optional.get();
		return newSalonService;
	}

	@Override
	public List<SalonService> getSalonServiceByPrice(String servicePrice) {
		List<SalonService> salonServiceList = salonServiceRepository.findByServicePrice(servicePrice);
		if(salonServiceList.isEmpty()) {
			throw new BadRequestException("Service does not exist");
		}
		return salonServiceList;
	}

	@Override
	public SalonService updateSalonServiceById(Long serviceId, SalonService salonservice) {
		Optional<SalonService> optional = salonServiceRepository.findById(serviceId);
		if(optional.isEmpty()) {
			throw new BadRequestException("Service does not exist");
		}
		SalonService existingSalonService = optional.get();
		if(salonservice.getServiceName() != null) {
			existingSalonService.setServiceName(salonservice.getServiceName());
		}
		if(salonservice.getServicePrice() != null) {
			existingSalonService.setServicePrice(salonservice.getServicePrice());
		}
		if(salonservice.getServiceDuration() != null) {
			existingSalonService.setServiceDuration(salonservice.getServiceDuration());
		}
		SalonService updatedSalonService = salonServiceRepository.save(existingSalonService);
		return updatedSalonService;
	}

	@Override
	public void deleteSalonServiceById(Long serviceId) {
		Optional<SalonService> optional = salonServiceRepository.findById(serviceId);
		if(optional.isEmpty()) {
			throw new BadRequestException("Service does not exist");
		}
		salonServiceRepository.deleteById(serviceId);
	}

	@Override
	public List<SalonService> getSalonServiceByAppointmentId(Long appointmentId) {
		List<SalonService> salonServiceList = salonServiceRepository.findByAppointments_AppointmentId(appointmentId);
		if(salonServiceList.isEmpty()) {
			throw new BadRequestException("Service does not exist for this appointmentId");
		}
		return salonServiceList;
	}

}
