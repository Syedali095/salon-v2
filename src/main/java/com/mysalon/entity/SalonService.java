package com.mysalon.entity;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="service_tbl")
@Data
public class SalonService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_id")
	private Long serviceId;
	
	@Column(name = "service_name")
	private String serviceName;
	
	@Column(name = "service_price")
	private BigDecimal servicePrice;
	
	@Column(name = "service_duration")
	private String serviceDuration;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "salonServices")
    private List<Appointment> appointments;
}
