package com.mysalon.dto;

import lombok.Data;

@Data
public class AddressDto {
	private String fullAddress;
	private String state;
	private String pincode;
}
