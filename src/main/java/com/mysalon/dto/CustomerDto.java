package com.mysalon.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CustomerDto {
	private String name;
	private String contactNo;
	private String email;
	private LocalDate dob;
	private AddressDto addressDto;
}
