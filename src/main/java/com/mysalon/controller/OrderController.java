package com.mysalon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.entity.Order;
import com.mysalon.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/orderId/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId){
		Order newOrder = orderService.getOrderById(orderId);
		return new ResponseEntity<>(newOrder, HttpStatus.OK);
	}
	
	@GetMapping("/appointmentId/{appointmentId}")
	public ResponseEntity<Order> getOrderByAppointmentId(@PathVariable Long appointmentId){
		Order newOrder = orderService.getOrderByAppointmentId(appointmentId);
		return new ResponseEntity<>(newOrder, HttpStatus.OK);
	}
	
	@GetMapping("/custId/{custId}")
	public ResponseEntity<List<Order>> getOrderByCustomerId(@PathVariable Long custId){
		List<Order> orders = orderService.getOrderByCustomerId(custId);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Order>> getAllOrders(){
		List<Order> orders = orderService.getAllOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
}
