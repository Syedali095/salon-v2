package com.mysalon.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.entity.SalonService;
import com.mysalon.service.SalonServiceClass;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/service")
public class SalonServiceController {

	@Autowired
	private SalonServiceClass salonServiceClass;

	@PostMapping("/save")
	public ResponseEntity<SalonService> addService(@Valid @RequestBody SalonService salonService) {
		SalonService newSalonService = salonServiceClass.addSalonService(salonService);
		return new ResponseEntity<>(newSalonService, HttpStatus.CREATED);
	}

	@GetMapping("/get/{serviceId}")
	public ResponseEntity<SalonService> getServiceById(@PathVariable Long serviceId) {
		SalonService newSalonService = salonServiceClass.getSalonServiceById(serviceId);
		return new ResponseEntity<>(newSalonService, HttpStatus.OK);
	}

	@GetMapping("/getByName")
	public ResponseEntity<SalonService> getServiceByName(@RequestParam String serviceName) {
		SalonService newSalonService = salonServiceClass.getSalonServiceByName(serviceName);
		return new ResponseEntity<>(newSalonService, HttpStatus.OK);
	}

	@GetMapping("/getByPrice")
	public ResponseEntity<List<SalonService>> getServiceByPrice(
			@RequestParam(required = false, defaultValue = "100.00") BigDecimal servicePrice) {
		List<SalonService> newSalonService = salonServiceClass.getSalonServiceByPrice(servicePrice);
		return new ResponseEntity<>(newSalonService, HttpStatus.OK);
	}

	@GetMapping("/getByAppointmentId/{appointmentId}")
	public ResponseEntity<List<SalonService>> getServiceByAppointmentId(@PathVariable Long appointmentId) {
		List<SalonService> newSalonService = salonServiceClass.getSalonServiceByAppointmentId(appointmentId);
		return new ResponseEntity<>(newSalonService, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<SalonService>> getAllServices() {
		List<SalonService> newSalonService = salonServiceClass.getAllSalonServices();
		return new ResponseEntity<>(newSalonService, HttpStatus.OK);
	}

	@PutMapping("/update/{serviceId}")
	public ResponseEntity<SalonService> updateSalonService(@PathVariable Long serviceId,
			@Valid @RequestBody SalonService salonService) {
		SalonService newSalonService = salonServiceClass.updateSalonServiceById(serviceId, salonService);
		return new ResponseEntity<>(newSalonService, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{serviceId}")
	public ResponseEntity<String> deleteSalonServiceById(@PathVariable Long serviceId) {
		salonServiceClass.deleteSalonServiceById(serviceId);
		return new ResponseEntity<>("Salon service deleted successfully", HttpStatus.NO_CONTENT);
	}
}
