package com.mysalon.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.entity.Appointment;
import com.mysalon.entity.AppointmentDto;
import com.mysalon.entity.PaymentDto;
import com.mysalon.service.AppointmentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@PostMapping("/save")
	public ResponseEntity<Appointment> saveAppointment(@Valid @RequestBody Appointment appointment){
		Appointment newAppointment = appointmentService.addAppointment(appointment);
		ResponseEntity<Appointment> responseEntity = new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
		return responseEntity;
	}
	
	@PostMapping("/book")
	public ResponseEntity<Appointment> bookAppointment(@Valid @RequestBody AppointmentDto appointmentDto, @RequestBody PaymentDto paymentDto, Long custCardId, Long salonCardId){
		Appointment newAppointment = appointmentService.bookAppointment(appointmentDto, paymentDto, custCardId, salonCardId);
		ResponseEntity<Appointment> responseEntity = new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
		return responseEntity;
	}
	
	@GetMapping("/{custId}")
	public ResponseEntity<List<Appointment>> getAppointmentListByCustomerId(@PathVariable long custId){
		List<Appointment> appointmentList = appointmentService.getAppointmentListByCustomerId(custId);
		ResponseEntity<List<Appointment>> responseEntity = new ResponseEntity<>(appointmentList, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/{appointmentId}")
	public ResponseEntity<Appointment> getAppointmentByAppointmentId(@PathVariable long appointmentId){
		Appointment appointment = appointmentService.getAppointmentByAppointmentId(appointmentId);
		ResponseEntity<Appointment> responseEntity = new ResponseEntity<>(appointment, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Appointment>> getAllAppointments(){
		List<Appointment> appointmentList = appointmentService.getAllAppointment();
		ResponseEntity<List<Appointment>> responseEntity = new ResponseEntity<>(appointmentList, HttpStatus.OK);
		return responseEntity;
	}
	
	@DeleteMapping("/{appointmentId}")
	public ResponseEntity<String> DeleteAppointmentByAppointmentId(@PathVariable long appointmentId){
		appointmentService.deleteAppointment(appointmentId);
		ResponseEntity<String> responseEntity = new ResponseEntity<>("Appointment deleted successfully", HttpStatus.NO_CONTENT);
		return responseEntity;
	}
	
	@PutMapping("/update/{custId}")
	public ResponseEntity<Appointment> updateAppointment(@Valid @PathVariable long custId, @RequestBody Appointment appointment){
		Appointment newAppointment = appointmentService.updateAppointment(custId, appointment);
		ResponseEntity<Appointment> responseEntity = new ResponseEntity<>(newAppointment, HttpStatus.OK);
		return responseEntity;
	}
	
	@PatchMapping("/patch/{custId}")
	public ResponseEntity<Appointment> patchAppointment(@Valid @PathVariable long custId, @RequestBody Appointment appointment){
		Appointment newAppointment = appointmentService.updateAppointment(custId, appointment);
		ResponseEntity<Appointment> responseEntity = new ResponseEntity<>(newAppointment, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/location")
	public ResponseEntity<List<Appointment>> getAppointmentListByLocation(@RequestParam String location){
		List<Appointment> appointmentList = appointmentService.getAppointmentListByLocation(location);
		ResponseEntity<List<Appointment>> responseEntity = new ResponseEntity<>(appointmentList, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/{preferredDate}")
	public ResponseEntity<List<Appointment>> getAppointmentListByDate(@PathVariable LocalDate preferredDate){
		List<Appointment> appointmentList = appointmentService.getAppointmentListByDate(preferredDate);
		ResponseEntity<List<Appointment>> responseEntity = new ResponseEntity<>(appointmentList, HttpStatus.OK);
		return responseEntity;
	}
}
