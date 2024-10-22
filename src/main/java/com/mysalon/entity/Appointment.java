package com.mysalon.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "appointment_tbl")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="appointment_id")
	private Long appointmentId;
	
	@NotBlank(message="Enter the location")
	@Column(name="location")
	private String location;
	
	@FutureOrPresent(message = "The preferred date must be today or a future date.")
	@Column(name="preferred_date")
	private LocalDate preferredDate;
	
	@NotBlank(message="Enter the preferred time")
	@Column(name="preferred_time")
	private LocalTime preferredTime;

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
	//@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
	private Order order;
	
	@ManyToMany//cascade
    @JoinTable(
        name = "appointment_salonservice",
        joinColumns = @JoinColumn(name = "appointment_id"),
        inverseJoinColumns = @JoinColumn(name = "salonservice_id")
    )
    private List<SalonService> salonServices;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    private FinalPriceReceipt finalPriceReceipt;
}
