package com.mysalon.service;

import java.time.LocalDate;
import java.util.List;
import com.mysalon.entity.Appointment;
import com.mysalon.entity.AppointmentDto;
import com.mysalon.entity.PaymentDto;

public interface AppointmentService {
	
	Appointment addAppointment(Appointment appointment);
	
	Appointment bookAppointment(AppointmentDto appointmentDto, PaymentDto paymentDto, Long custCardId, Long salonCardId);
	
	List<Appointment> getAppointmentListByCustomerId(long custId);
	
	Appointment getAppointmentByAppointmentId(long appointmentId);
	
	List<Appointment> getAllAppointment();
	
	List<Appointment> getAppointmentListByLocation(String location);
	
	List<Appointment> getAppointmentListByDate(LocalDate preferredDate);
	
	void deleteAppointment(long appointmentId);
	
	Appointment updateAppointment(long appointmentId, Appointment appointment);
}
