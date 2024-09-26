package com.mysalon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private String servicePrice;
	
	@Column(name = "service_duration")
	private String serviceDuration;
	
	// Many Services can be associated with one Appointment
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
	private Appointment appointments;
    //If we have used @ManyToMany or @OneToMany then we should have used List<Appointment> in above line
}
