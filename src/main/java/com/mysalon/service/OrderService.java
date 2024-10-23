package com.mysalon.service;

import java.math.BigDecimal;
import java.util.List;
import com.mysalon.entity.Appointment;
import com.mysalon.entity.Customer;
import com.mysalon.entity.Order;

public interface OrderService {
	
	Order createOrder(BigDecimal paidAmount, Appointment appointment, Customer customer);
	
	Order getOrderById(Long orderId);
	
	Order getOrderByAppointmentId(Long appointmentId);
	
	List<Order> getOrderByCustomerId(Long custId);
	
	List<Order> getAllOrders();
	
	void deleteOrder(Customer customer, Long orderId);

}
