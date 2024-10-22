package com.mysalon.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.entity.SalonService;
import com.mysalon.exception.BadRequestException;
import com.mysalon.exception.NoServiceFoundException;
import com.mysalon.repository.SalonServiceRepository;

@Service
public class SalonServiceClassImpl implements SalonServiceClass{

	@Autowired
	private SalonServiceRepository salonServiceRepository;
	
	@Override
	public SalonService addSalonService(SalonService salonService) {
		Optional <SalonService> optional = salonServiceRepository.findByServiceName(salonService.getServiceName());
		if(optional.isPresent()) {
			throw new BadRequestException("Service already exist with that Name");
		}
		SalonService newService = salonServiceRepository.save(salonService);
		return newService;
	}

	@Override
	public SalonService getSalonServiceById(Long serviceId) {
		return salonServiceRepository.findById(serviceId)
				.orElseThrow(() -> new NoServiceFoundException("Service with Service Id " +serviceId +" does not exist"));
	}

	@Override
	public SalonService getSalonServiceByName(String serviceName) {
		return salonServiceRepository.findByServiceName(serviceName)
				.orElseThrow(() -> new NoServiceFoundException("Service with Service Name " +serviceName +" does not exist"));
	}

	@Override
	public List<SalonService> getSalonServiceByPrice(BigDecimal servicePrice) {
		List<SalonService> salonServiceList = salonServiceRepository.findByServicePrice(servicePrice);
		if(salonServiceList.isEmpty()) {
			return Collections.emptyList();
		}
		return salonServiceList;
	}
	
	@Override
	public List<SalonService> getSalonServiceByAppointmentId(Long appointmentId) {
		List<SalonService> salonServiceList = salonServiceRepository.findByAppointments_AppointmentId(appointmentId);
		if(salonServiceList.isEmpty()) {
			return Collections.emptyList();
		}
		return salonServiceList;
	}
	
	@Override
	public List<SalonService> getAllSalonServices() {
		List<SalonService> salonServiceList = salonServiceRepository.findAll();
		if(salonServiceList.isEmpty()) {
			return Collections.emptyList();
		}
		return salonServiceList;
	}

	@Override
	public SalonService updateSalonServiceById(Long serviceId, SalonService salonservice) {
		SalonService existingSalonService = salonServiceRepository.findById(serviceId)
				.orElseThrow(() -> new NoServiceFoundException("Service with Service Id " +serviceId +" does not exist"));

		if(salonservice.getServiceName() != null) {
			existingSalonService.setServiceName(salonservice.getServiceName());
		}
		if(salonservice.getServicePrice() != null) {
			existingSalonService.setServicePrice(salonservice.getServicePrice());
		}
		if(salonservice.getServiceDuration() != null) {
			existingSalonService.setServiceDuration(salonservice.getServiceDuration());
		}
		return salonServiceRepository.save(existingSalonService);
	}

	@Override
	public void deleteSalonServiceById(Long serviceId) {
		salonServiceRepository.findById(serviceId)
			.orElseThrow(() -> new NoServiceFoundException("Service with Service Id " +serviceId +" does not exist"));
		salonServiceRepository.deleteById(serviceId);
	}

}
