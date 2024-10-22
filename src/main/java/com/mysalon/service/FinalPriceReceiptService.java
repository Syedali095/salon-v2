package com.mysalon.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mysalon.dto.FinalPriceReceiptDto;
import com.mysalon.entity.Appointment;
import com.mysalon.entity.FinalPriceReceipt;

public interface FinalPriceReceiptService {
	
	FinalPriceReceipt saveReceipt(Long custId, List<String> serviceNames, Appointment appointment);
	
	FinalPriceReceiptDto getReceiptById(Long receiptId);
	
	FinalPriceReceiptDto getReceiptByAppointmentId(Long appointmentId);
	
	List<FinalPriceReceiptDto> getReceiptListById(Long custId);
	
	List<FinalPriceReceiptDto> getAllReceipt();
	
	FinalPriceReceipt updateReceipt(List<String> serviceNames, Appointment appointment);
	
	void deleteReceipt(Long appointmentId);
	
	BigDecimal totalPriceCalculator(List<String> serviceNames, List<BigDecimal> priceList);
	
	Map<String, BigDecimal> serviceDetailsMapCreator(List<String> serviceNames, List<BigDecimal> priceList);

}
