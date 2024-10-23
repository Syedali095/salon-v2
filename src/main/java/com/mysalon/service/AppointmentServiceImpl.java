package com.mysalon.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.dto.AppointmentDto;
import com.mysalon.dto.PaymentDto;
import com.mysalon.entity.Appointment;
import com.mysalon.entity.Customer;
import com.mysalon.entity.FinalPriceReceipt;
import com.mysalon.entity.Order;
import com.mysalon.entity.Payment;
import com.mysalon.entity.SalonService;
import com.mysalon.exception.BadRequestException;
import com.mysalon.exception.DuplicateAppointmentException;
import com.mysalon.exception.NoAppointmentFoundException;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.exception.NoReceiptFoundException;
import com.mysalon.exception.NoServiceFoundException;
import com.mysalon.repository.AppointmentRepository;
import com.mysalon.repository.CustomerRepository;
import com.mysalon.repository.FinalPriceReceiptRepository;
import com.mysalon.repository.OrderRepository;
import com.mysalon.repository.PaymentRepository;
import com.mysalon.repository.SalonServiceRepository;
import jakarta.transaction.Transactional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private SalonServiceRepository salonServiceRepository;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private FinalPriceReceiptService finalPriceReceiptService;
	@Autowired
	private FinalPriceReceiptRepository finalPriceReceiptRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	@Transactional
	public Appointment bookAppointment(Long custId, AppointmentDto appointmentDto, PaymentDto paymentDto,
			Long custCardId, Long salonCardId) {

		// Get the customer
		Customer customer = customerRepository.findById(custId)
				.orElseThrow(() -> new NoCustomerFoundException("No customer has found by cust Id: " + custId));

		// Convert DTO to Appointment entity
		Appointment appointment = new Appointment();
		appointment.setLocation(appointmentDto.getLocation());
		appointment.setPreferredDate(appointmentDto.getPreferredDate());
		appointment.setPreferredTime(appointmentDto.getPreferredTime());

		// Check if the customer has already booked an appointment for the same day, time, and location
		List<Appointment> existingAppointments = appointmentRepository.findAppointmentByAppointmentDetails(custId,
				appointmentDto.getPreferredDate(), appointmentDto.getPreferredTime(), appointmentDto.getLocation());

		if (!existingAppointments.isEmpty()) {
			throw new DuplicateAppointmentException(
					"Customer has already booked an appointment on this date and time at this location.");
		}

		// Fetching SalonService entities by Names (We can also create a separate method for this)
		List<String> serviceNames = appointmentDto.getServiceNames();
		List<SalonService> services = salonServiceRepository.findByServiceNameIn(serviceNames);

		// Check if at least one service is opted
		if (services.isEmpty()) {
			throw new NoServiceFoundException("Select the services to book an appointment");
		}

		appointment.setSalonServices(services);

		// Handle the Payment
		Payment newPayment = paymentService.makePayment(paymentDto, custCardId, salonCardId);
		appointment.setPayment(newPayment);

		// Link the Appointment with the customer
		appointment.setCustomer(customer);

		// Add the Appointment in Customer's Appointment List
		customer.addAppointment(appointment);
		
		// Save the Appointment
		Appointment newAppointment = appointmentRepository.save(appointment);

		// Create an Order
		BigDecimal amountPaid = paymentDto.getAmount();
		Order newOrder = orderService.createOrder(amountPaid, newAppointment, customer);
		newAppointment.setOrder(newOrder);

		// Create FinalPriceReceipt
		FinalPriceReceipt newReceipt = finalPriceReceiptService.saveReceipt(custId, serviceNames, newAppointment);
		newAppointment.setFinalPriceReceipt(newReceipt);
		
		// Save the Appointment again to update the order and receipt
		appointmentRepository.save(newAppointment);
		
		return newAppointment;
	}

	@Override
	@Transactional
	public Appointment updateAppointment(Long appointmentId, AppointmentDto appointmentDto) {

		Appointment existingAppointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new NoAppointmentFoundException("No such appointment found"));

		if (appointmentDto.getLocation() != null) {
			existingAppointment.setLocation(appointmentDto.getLocation());
		}
		if (appointmentDto.getPreferredDate() != null) {
			LocalDate updatedDate = appointmentDto.getPreferredDate();
			LocalDate oldDate = existingAppointment.getPreferredDate();
			if (updatedDate.isEqual(LocalDate.now()) || updatedDate.isBefore(LocalDate.now()) 
					|| oldDate.isEqual(LocalDate.now()) || oldDate.isBefore(LocalDate.now())) {
				throw new BadRequestException("Select the date properly");
			}
			existingAppointment.setPreferredDate(appointmentDto.getPreferredDate());
		}
		if (appointmentDto.getPreferredTime() != null) {
			existingAppointment.setPreferredTime(appointmentDto.getPreferredTime());
		}

		if (appointmentDto.getServiceNames() != null) {
			List<String> serviceNames = appointmentDto.getServiceNames();
			List<SalonService> services = salonServiceRepository.findByServiceNameIn(serviceNames);
			existingAppointment.setSalonServices(services);

			FinalPriceReceipt oldReceipt = finalPriceReceiptService.getReceiptByAppointmentId(appointmentId);
			FinalPriceReceipt updatedReceipt = finalPriceReceiptService.updateReceipt(oldReceipt, serviceNames, existingAppointment);
			existingAppointment.setFinalPriceReceipt(updatedReceipt);
		}

		Appointment newAppointment = appointmentRepository.save(existingAppointment);
		return newAppointment;
	}

	@Override
	@Transactional
	public void cancelAppointment(Long custId, Long appointmentId, Long salonCardId) {
		
		// Get the Customer
		Customer customer = customerRepository.findById(custId)
				.orElseThrow(() -> new NoCustomerFoundException("No customer has found by cust Id: " + custId));
				
		// Get the Appointment
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new NoAppointmentFoundException(
						"Appointment with Appintment ID:" + appointmentId + " not found"));

		
		LocalDate appointmentDate = appointment.getPreferredDate();
		LocalTime appointmentTime = appointment.getPreferredTime();
		
		if (appointmentDate.isBefore(LocalDate.now())) {
			throw new BadRequestException("Appointment date already exceeded");
		}else if (appointmentDate.isEqual(LocalDate.now()) && appointmentTime.isBefore(LocalTime.now())) {
	        throw new BadRequestException("Appointment time has already passed for today");
	    }

		// Reverse Payment
		Long paymentId = appointment.getPayment().getPaymentId();
		paymentService.reversePayment(paymentId, salonCardId);
			
		// Unlink and Delete the Payment associated to this Appointment
		appointment.setPayment(null); // Unlink the Payment from the Appointment
		appointmentRepository.save(appointment);	// save the changes to the Appointment
		paymentRepository.deleteById(paymentId);	// delete the payment
			
		// Unlink and Delete the Order associated to this Appointment
		Order order = orderService.getOrderByAppointmentId(appointmentId);
		Long orderId = order.getOrderId();
			
		appointment.setOrder(null);
		appointmentRepository.save(appointment);
		orderRepository.deleteById(orderId);

		// Unlink and Delete the Receipt associated to this Appointment
		FinalPriceReceipt receipt = finalPriceReceiptRepository.findByAppointment_AppointmentId(appointmentId)
				.orElseThrow(() -> new NoReceiptFoundException("No receipt found with Appointment Id: " +appointmentId));
		Long receiptId = receipt.getReceiptId();
			
		appointment.setFinalPriceReceipt(null);
		appointmentRepository.save(appointment);
		finalPriceReceiptRepository.deleteById(receiptId);

		// Remove this Appointment from Customer's Appointment List
		if(customer.getAppointments().contains(appointment)) {
			customer.removeAppointment(appointment);
			customerRepository.save(customer); // Save the changes
		}
			
		// Delete the Appointment
		appointmentRepository.deleteById(appointmentId);
	}

	@Override
	public Appointment getAppointmentByAppointmentId(Long appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new NoAppointmentFoundException(
				"Appointment with Appintment ID:" + appointmentId + " not found"));
		return appointment;
	}

	@Override
	public List<Appointment> getAppointmentListByCustomerId(Long custId) {
		customerRepository.findById(custId)
				.orElseThrow(() -> new NoCustomerFoundException("Customer with UserID " + custId + " does not exist"));

		List<Appointment> appointmentList = appointmentRepository.findByCustomer_CustId(custId);
		if (appointmentList.isEmpty()) {
			return Collections.emptyList();
		}
		return appointmentList;
	}

	@Override
	public List<Appointment> getAppointmentListByLocation(String location) {
		List<Appointment> appointmentList = appointmentRepository.findByLocation(location);
		if (appointmentList.isEmpty()) {
			return Collections.emptyList();
		}
		return appointmentList;
	}

	@Override
	public List<Appointment> getAppointmentListByDate(LocalDate preferredDate) {
		List<Appointment> appointmentList = appointmentRepository.findByPreferredDate(preferredDate);
		if (appointmentList.isEmpty()) {
			return Collections.emptyList();
		}
		return appointmentList;
	}

	@Override
	public List<Appointment> getAllAppointment() {
		List<Appointment> appointmentList = appointmentRepository.findAll();
		if (appointmentList.isEmpty()) {
			return Collections.emptyList();
		}
		return appointmentList;
	}

//	@Override
//	public void deleteAppointment(Long appointmentId) {
//		appointmentRepository.findById(appointmentId).orElseThrow(() -> new NoAppointmentFoundException(
//				"Appointment with Appintment ID:" + appointmentId + " not found"));
//
//		appointmentRepository.deleteById(appointmentId);
//	}

}
