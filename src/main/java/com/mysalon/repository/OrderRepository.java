package com.mysalon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysalon.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
