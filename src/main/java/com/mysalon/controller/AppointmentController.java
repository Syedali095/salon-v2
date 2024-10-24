package com.mysalon.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.dto.AppointmentDto;
import com.mysalon.dto.BookingRequestDto;
import com.mysalon.dto.PaymentDto;
import com.mysalon.entity.Appointment;
import com.mysalon.service.AppointmentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@PostMapping("/book/{custId}")
	public ResponseEntity<Appointment> bookAppointment(@PathVariable Long custId, 
			@RequestBody BookingRequestDto bookingRequestDto, @RequestParam Long custCardId, @RequestParam Long salonCardId) {
		
		AppointmentDto appointmentDto = bookingRequestDto.getAppointmentDto();
	    PaymentDto paymentDto = bookingRequestDto.getPaymentDto();
		Appointment newAppointment = appointmentService.bookAppointment(custId, appointmentDto, paymentDto, custCardId,
				salonCardId);
		return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
	}

	@GetMapping("/customer/{custId}")
	public ResponseEntity<List<Appointment>> getAppointmentsByCustomerId(@PathVariable Long custId) {
		List<Appointment> appointmentList = appointmentService.getAppointmentListByCustomerId(custId);
		return new ResponseEntity<>(appointmentList, HttpStatus.OK);
	}

	@GetMapping("/appointment/{appointmentId}")
	public ResponseEntity<Appointment> getAppointmentByAppointmentId(@PathVariable Long appointmentId) {
		Appointment appointment = appointmentService.getAppointmentByAppointmentId(appointmentId);
		return new ResponseEntity<>(appointment, HttpStatus.OK);
	}

	@GetMapping("/location")
	public ResponseEntity<List<Appointment>> getAppointmentsByLocation(@RequestParam String location) {
		List<Appointment> appointmentList = appointmentService.getAppointmentListByLocation(location);
		return new ResponseEntity<>(appointmentList, HttpStatus.OK);
	}

	@GetMapping("/date")
	public ResponseEntity<List<Appointment>> getAppointmentsByDate(@RequestParam LocalDate preferredDate) {
		List<Appointment> appointmentList = appointmentService.getAppointmentListByDate(preferredDate);
		return new ResponseEntity<>(appointmentList, HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Appointment>> getAllAppointments() {
		List<Appointment> appointmentList = appointmentService.getAllAppointment();
		return new ResponseEntity<>(appointmentList, HttpStatus.OK);
	}

	@PutMapping("/update/{custId}/{appointmentId}")
	public ResponseEntity<Appointment> updateAppointment(@PathVariable Long custId, @PathVariable Long appointmentId,
			@RequestBody AppointmentDto appointmentDto) {
		Appointment newAppointment = appointmentService.updateAppointment(appointmentId, appointmentDto);
		return new ResponseEntity<>(newAppointment, HttpStatus.OK);
	}

	@PatchMapping("/patch/{custId}")
	public ResponseEntity<Appointment> patchAppointment(@Valid @PathVariable Long custId,
			@RequestBody AppointmentDto appointmentDto) {
		Appointment newAppointment = appointmentService.updateAppointment(custId, appointmentDto);
		return new ResponseEntity<>(newAppointment, HttpStatus.OK);
	}

	@DeleteMapping("/cancel/{custId}/{appointmentId}/{salonCardId}")
	public ResponseEntity<String> deleteAppointmentById(@PathVariable Long custId,@PathVariable Long appointmentId, @PathVariable Long salonCardId) {
		appointmentService.cancelAppointment(custId, appointmentId, salonCardId);
		return new ResponseEntity<>("Appointment deleted successfully", HttpStatus.OK);
	}
}
