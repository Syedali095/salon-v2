package com.mysalon.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class AppointmentDto {
	private String location;
	private LocalDate preferredDate;
	private LocalTime preferredTime;
	private List<String> serviceNames;
}
