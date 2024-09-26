package com.mysalon.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.entity.Appointment;
import com.mysalon.entity.AppointmentDto;
import com.mysalon.entity.Customer;
import com.mysalon.entity.Order;
import com.mysalon.entity.Payment;
import com.mysalon.entity.PaymentDto;
import com.mysalon.entity.SalonService;
import com.mysalon.exception.BadRequestException;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.repository.AppointmentRepository;
import com.mysalon.repository.CustomerRepository;
import com.mysalon.repository.OrderRepository;
import com.mysalon.repository.SalonServiceRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService{

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private SalonServiceRepository salonServiceRepository;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Appointment addAppointment(Appointment appointment) {
		Optional<Appointment> appointment1 = appointmentRepository.findById(appointment.getAppointmentId());
		if(appointment1.isPresent()) {
			throw new BadRequestException("Appointment already exist with that Appointment Id");
		}//No sense of checking for duplicate appointmentId
		Appointment newAppointment = appointmentRepository.save(appointment);
		return newAppointment;
	}
	
	@Override
	public Appointment bookAppointment(AppointmentDto appointmentDto, PaymentDto paymentDto, Long custCardId, Long salonCardId) {
		
		//Convert DTO to Appointment entity
		Appointment appointment = new Appointment();
		appointment.setLocation(appointmentDto.getLocation());
		appointment.setPreferredDate(appointmentDto.getPreferredDate());
		appointment.setPreferredTime(appointmentDto.getPreferredTime());
		//Fetching SalonService entities by Names
		List<String> serviceNames = appointmentDto.getServiceNames();
		List<SalonService> services = salonServiceRepository.findByServiceNameIn(serviceNames);
		//List<SalonService> services = salonServiceRepository.findByServiceName(appointmentDto.getServiceNames()); 
		appointment.setSalonService(services);
		
        Payment doPayment = paymentService.makePayment(paymentDto, custCardId, salonCardId);
        appointment.setPayment(doPayment);
        
		Appointment newAppointment = appointmentRepository.save(appointment);
		
		//Create an Order
		Order order = new Order();
		order.setBookingDate(LocalDate.now());
		order.setBookingTime(LocalTime.now());
		order.setAmountPaid(paymentDto.getAmount());
		orderRepository.save(order); //Directly saving data from Repo
		
		return newAppointment;
	}
	

	@Override
	public List<Appointment> getAppointmentListByCustomerId(long custId) throws NoCustomerFoundException, BadRequestException{
		Optional<Customer> customer1 = customerRepository.findById(custId);
		if(customer1.isEmpty()) {
			throw new NoCustomerFoundException("Customer with UserID " +custId +" does not exist");
		}
		List<Appointment> appointmentList = appointmentRepository.findByCustomer_CustId(custId);
		if(appointmentList.isEmpty()) {
			throw new BadRequestException("No Appointments found");
		}
		return appointmentList;
	}

	@Override
	public Appointment getAppointmentByAppointmentId(long appointmentId) throws BadRequestException{
		Optional<Appointment> appointment1 = appointmentRepository.findById(appointmentId);
		if(appointment1.isEmpty()) {
			throw new BadRequestException("No such appointment found");
		}
		Appointment newAppointment = appointment1.get();
		return newAppointment;
	}
	
	@Override
	public List<Appointment> getAllAppointment() throws BadRequestException{
		List<Appointment> appointmentList = appointmentRepository.findAll();
		if(appointmentList.isEmpty()) {
			throw new BadRequestException("Appointment list is empty");
		}
		return appointmentList;
	}

	@Override
	public void deleteAppointment(long appointmentId) throws BadRequestException{
		Optional<Appointment> appointment1 = appointmentRepository.findById(appointmentId);
		if(appointment1.isEmpty()) {
			throw new BadRequestException("No such appointment found");
		}
		appointmentRepository.deleteById(appointmentId);
	}

	@Override
	public Appointment updateAppointment(long appointmentId, Appointment appointment) {
		Optional<Appointment> appointment1 = appointmentRepository.findById(appointmentId);
		if(appointment1.isEmpty()) {
			throw new BadRequestException("No such appointment found");
		}
		Appointment existingAppointment = appointment1.get();
		if(appointment.getLocation() != null) {
			existingAppointment.setLocation(appointment.getLocation());
		}
		if (appointment.getPreferredDate() != null) {
			existingAppointment.setPreferredDate(appointment.getPreferredDate());
		}
		if (appointment.getPreferredTime() != null) {
			existingAppointment.setPreferredTime(appointment.getPreferredTime());
		}
//		if (appointment.getServiceName() != null) {
//			existingAppointment.setServiceName(appointment.getServiceName());
//		}
		Appointment newAppointment = appointmentRepository.save(existingAppointment);
		return newAppointment;
	}

	@Override
	public List<Appointment> getAppointmentListByLocation(String location) {
		List<Appointment> appointmentList = appointmentRepository.findByLocation(location);
		if(appointmentList.isEmpty()) {
			throw new BadRequestException("No appointments found at this location");
		}
		return appointmentList;
	}

	@Override
	public List<Appointment> getAppointmentListByDate(LocalDate preferredDate) {
		List<Appointment> appointmentList = appointmentRepository.findByPreferredDate(preferredDate);
		if(appointmentList.isEmpty()) {
			throw new BadRequestException("No appointments are there on the provided date");
		}
		return appointmentList;
	}

	
}
