package com.mysalon.service;

import java.time.LocalDate;
import java.util.List;
import com.mysalon.dto.AppointmentDto;
import com.mysalon.dto.PaymentDto;
import com.mysalon.entity.Appointment;

public interface AppointmentService {
	
	Appointment bookAppointment(Long custId, AppointmentDto appointmentDto, PaymentDto paymentDto, Long custCardId, Long salonCardId); //C
	
	void cancelAppointment(Long appointmentId, Long salonCardId); //C
	
	Appointment getAppointmentByAppointmentId(Long appointmentId); //C
	
	List<Appointment> getAppointmentListByCustomerId(Long custId); //C
	
	List<Appointment> getAppointmentListByLocation(String location); //C
	
	List<Appointment> getAppointmentListByDate(LocalDate preferredDate); //C
	
	List<Appointment> getAllAppointment(); //C
	
	Appointment updateAppointment(Long appointmentId, AppointmentDto appointmentDto); //C
	
	//void deleteAppointment(Long appointmentId);
}

//Cancel appointment (Delete the Order also)