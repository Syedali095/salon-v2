package com.mysalon.controller;

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
	public ResponseEntity<SalonService> addService(@Valid @RequestBody SalonService salonService){
		SalonService newSalonService = salonServiceClass.addSalonService(salonService);
		ResponseEntity<SalonService> responseEntity = new ResponseEntity<>(newSalonService, HttpStatus.CREATED);
		return responseEntity;
	}
	
	@GetMapping("/get/{serviceId}")
	public ResponseEntity<SalonService> getServiceById(@PathVariable Long serviceId){
		SalonService newSalonService = salonServiceClass.getSalonServiceById(serviceId);
		ResponseEntity<SalonService> responseEntity = new ResponseEntity<>(newSalonService, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/getByName")
	public ResponseEntity<SalonService> getServiceByName(@RequestParam String serviceName){
		SalonService newSalonService = salonServiceClass.getSalonServiceByName(serviceName);
		ResponseEntity<SalonService> responseEntity = new ResponseEntity<>(newSalonService, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/getByPrice")
	public ResponseEntity<List<SalonService>> getServiceByPrice(@RequestParam(required = false, defaultValue = "1000") String servicePrice){
		List<SalonService> newSalonService = salonServiceClass.getSalonServiceByPrice(servicePrice);
		ResponseEntity<List<SalonService>> responseEntity = new ResponseEntity<>(newSalonService, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/getByAppointmentId")
	public ResponseEntity<List<SalonService>> getServiceByAppointmentId(@PathVariable Long appointmentId){
		List<SalonService> newSalonService = salonServiceClass.getSalonServiceByAppointmentId(appointmentId);
		ResponseEntity<List<SalonService>> responseEntity = new ResponseEntity<>(newSalonService, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<SalonService>> getAllServices(){
		List<SalonService> newSalonService = salonServiceClass.getAllSalonServices();
		ResponseEntity<List<SalonService>> responseEntity = new ResponseEntity<>(newSalonService, HttpStatus.OK);
		return responseEntity;
	}
	
	@PutMapping("/update")
	public ResponseEntity<SalonService> updateSalonService(@Valid @PathVariable Long serviceId, @RequestBody SalonService salonService){
		SalonService newSalonService = salonServiceClass.updateSalonServiceById(serviceId, salonService);
		ResponseEntity<SalonService> responseEntity = new ResponseEntity<>(newSalonService, HttpStatus.OK);
		return responseEntity;
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteSalonServiceById(@PathVariable Long serviceId){
		salonServiceClass.deleteSalonServiceById(serviceId);
		ResponseEntity<String> responseEntity = new ResponseEntity<>("Service deleted successfully", HttpStatus.NO_CONTENT);
		return responseEntity;
	}
}
