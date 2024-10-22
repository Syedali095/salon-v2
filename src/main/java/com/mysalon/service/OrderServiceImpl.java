package com.mysalon.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.entity.Appointment;
import com.mysalon.entity.Customer;
import com.mysalon.entity.Order;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.exception.NoOrderFoundException;
import com.mysalon.repository.CustomerRepository;
import com.mysalon.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Order createOrder(BigDecimal paidAmount, Appointment appointment, Customer customer) {
		Order order = new Order();
		order.setBookingDate(LocalDate.now());
		order.setBookingTime(LocalTime.now());
		order.setAmountPaid(paidAmount);
		
		// Link the Order with the Appointment
		order.setAppointment(appointment);
		
		// Link the Appointment with Order
		//appointment.setOrder(order);
		// We are linking it in bookAppointment()
		
		// Link the Order with the Customer
		order.setCustomer(customer);
		
		// Add the Order in Customer's list of Orders
		customer.addOrder(order);
		
		//Save the Order
		orderRepository.save(order);
		
		return order;
	}
	
	@Override
	public Order getOrderById(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new NoOrderFoundException("No order found with Order Id: " +orderId));
	}

	@Override
	public Order getOrderByAppointmentId(Long appointmentId) {
		return orderRepository.findByAppointment_AppointmentId(appointmentId)
				.orElseThrow(() -> new NoOrderFoundException("No order found with Appointment Id: " +appointmentId));
	}

	@Override
	public List<Order> getOrderByCustomerId(Long custId) {
		customerRepository.findById(custId)
			.orElseThrow(() -> new NoCustomerFoundException("No customer found with customer Id: " +custId));
		
		List<Order> orders = orderRepository.findByCustomer_CustId(custId);
		if(orders.isEmpty()) {
			return Collections.emptyList();
		}
		return orders;
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		if(orders.isEmpty()) {
			return Collections.emptyList();
		}
		return orders;
	}

	@Override
	public void deleteOrder(Long orderId) {
		orderRepository.findById(orderId)
			.orElseThrow(() -> new NoOrderFoundException("No order found with Order Id: " +orderId));
		orderRepository.deleteById(orderId);
	}

}
