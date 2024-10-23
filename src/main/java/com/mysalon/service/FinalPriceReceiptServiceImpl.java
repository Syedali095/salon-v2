package com.mysalon.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mysalon.exception.BadRequestException;
import com.mysalon.exception.NoCustomerFoundException;
import com.mysalon.exception.NoReceiptFoundException;
import com.mysalon.entity.Appointment;
import com.mysalon.entity.FinalPriceReceipt;
import com.mysalon.constantconfig.AppointmentBookingFee;
import com.mysalon.dto.FinalPriceReceiptDto;
import com.mysalon.repository.CustomerRepository;
import com.mysalon.repository.FinalPriceReceiptRepository;
import com.mysalon.repository.SalonServiceRepository;

@Service
public class FinalPriceReceiptServiceImpl implements FinalPriceReceiptService {
	
	@Autowired
	private FinalPriceReceiptRepository finalPriceReceiptRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SalonServiceRepository salonServiceRepository;
	
	@Override
	public FinalPriceReceipt saveReceipt(Long custId, List<String> serviceNames, Appointment appointment) {
		
		FinalPriceReceipt finalPriceReceipt = new FinalPriceReceipt();
		
		String name = customerRepository.findById(custId).get().getName();
		finalPriceReceipt.setName(name);
		
		List<BigDecimal> priceList = salonServiceRepository.findPricesByServiceNames(serviceNames);
		BigDecimal totalPrice = totalPriceCalculator(serviceNames, priceList);
		finalPriceReceipt.setTotalPrice(totalPrice);
		
		BigDecimal paidAmount = AppointmentBookingFee.getFEE();
		BigDecimal finalPrice = totalPrice.subtract(paidAmount);
		finalPriceReceipt.setFinalPrice(finalPrice);
		
		// Create a Map 
		Map<String, BigDecimal> serviceDetails = new HashMap<>();
		serviceDetails = serviceDetailsMapCreator(serviceNames, priceList);
		finalPriceReceipt.setServiceDetails(serviceDetails);
		
		// Link the Receipt with the Appointment
		finalPriceReceipt.setAppointment(appointment);
		
		/* 
		Link the Appointment with the Receipt
		appointment.setFinalPriceReceipt(finalPriceReceipt); 
		We are linking it in bookAppointment()	
		*/
		
		// Save the Receipt
		finalPriceReceiptRepository.save(finalPriceReceipt);
		
		return finalPriceReceipt;
	}

	@Override
	public FinalPriceReceipt updateReceipt(FinalPriceReceipt oldReceipt, List<String> serviceNames, Appointment existingAppointment) {
		
		List<BigDecimal> priceList = salonServiceRepository.findPricesByServiceNames(serviceNames);
		BigDecimal totalPrice = totalPriceCalculator(serviceNames, priceList);
		oldReceipt.setTotalPrice(totalPrice);
		
		BigDecimal paidAmount = AppointmentBookingFee.getFEE();
		BigDecimal finalPrice = totalPrice.subtract(paidAmount);
		oldReceipt.setFinalPrice(finalPrice);
		
		// Create a Map 
		Map<String, BigDecimal> serviceDetails = new HashMap<>();
		serviceDetails = serviceDetailsMapCreator(serviceNames, priceList);
		oldReceipt.setServiceDetails(serviceDetails);
		
		oldReceipt.setAppointment(existingAppointment);
		
		finalPriceReceiptRepository.save(oldReceipt);
		
		return oldReceipt;
	}
	
	@Override
	public FinalPriceReceiptDto getReceiptById(Long receiptId) {
		FinalPriceReceipt receipt = finalPriceReceiptRepository.findById(receiptId)
				.orElseThrow(() -> new NoReceiptFoundException("No receipt found with Id: " +receiptId));
		
		FinalPriceReceiptDto receiptDto = new FinalPriceReceiptDto();
		receiptDto.setName(receipt.getName());
		receiptDto.setTotalPrice(receipt.getTotalPrice());
		receiptDto.setFinalPrice(receipt.getFinalPrice());
		
		if(receipt.getServiceDetails() != null) {
			receiptDto.setServiceDetails(receipt.getServiceDetails());
		} else {
			receiptDto.setServiceDetails(Collections.emptyMap());
		}
		return receiptDto;
	}

	@Override
	public FinalPriceReceiptDto getReceiptDtoByAppointmentId(Long appointmentId) {
		FinalPriceReceipt receipt = finalPriceReceiptRepository.findByAppointment_AppointmentId(appointmentId)
				.orElseThrow(() -> new NoReceiptFoundException("No receipt found with Id: " +appointmentId));
		
		FinalPriceReceiptDto receiptDto = new FinalPriceReceiptDto();
		receiptDto.setName(receipt.getName());
		receiptDto.setTotalPrice(receipt.getTotalPrice());
		receiptDto.setFinalPrice(receipt.getFinalPrice());
		
		if(receipt.getServiceDetails() != null) {
			receiptDto.setServiceDetails(receipt.getServiceDetails());
		} else {
			receiptDto.setServiceDetails(Collections.emptyMap());
		}
		return receiptDto;
	}

	@Override
	public FinalPriceReceipt getReceiptByAppointmentId(Long appointmentId) {
		FinalPriceReceipt receipt = finalPriceReceiptRepository.findByAppointment_AppointmentId(appointmentId)
				.orElseThrow(() -> new NoReceiptFoundException("No receipt found with Id: " +appointmentId));
		return receipt;
	}
	
	@Override
	public List<FinalPriceReceiptDto> getReceiptListById(Long custId) {
		customerRepository.findById(custId)
				.orElseThrow(() -> new NoCustomerFoundException("No Customer found with Id: " +custId));
		
		List<FinalPriceReceipt> fullReceipts = finalPriceReceiptRepository.findByAppointment_Customer_CustId(custId);
		
		if(fullReceipts.isEmpty()) {
			return Collections.emptyList();
		}
		// Convert List<FinalPriceReceipt> to List<FinalPriceReceiptDto>
	    List<FinalPriceReceiptDto> receiptDtos = new ArrayList<>();
	    for (FinalPriceReceipt receipt : fullReceipts) {
	        FinalPriceReceiptDto dto = new FinalPriceReceiptDto();
	        dto.setName(receipt.getName());
	        dto.setTotalPrice(receipt.getTotalPrice());
	        dto.setFinalPrice(receipt.getFinalPrice());
	        dto.setServiceDetails(receipt.getServiceDetails());
	        receiptDtos.add(dto);
	    }
	    return receiptDtos;
	}

	@Override
	public List<FinalPriceReceiptDto> getAllReceipt() {
		List<FinalPriceReceipt> fullReceipts = finalPriceReceiptRepository.findAll();
		
		if(fullReceipts.isEmpty()) {
			return Collections.emptyList();
		}
		// Convert List<FinalPriceReceipt> to List<FinalPriceReceiptDto>
	    List<FinalPriceReceiptDto> receiptDtos = new ArrayList<>();
	    for (FinalPriceReceipt receipt : fullReceipts) {
	        FinalPriceReceiptDto dto = new FinalPriceReceiptDto();
	        dto.setName(receipt.getName());
	        dto.setTotalPrice(receipt.getTotalPrice());
	        dto.setFinalPrice(receipt.getFinalPrice());
	        dto.setServiceDetails(receipt.getServiceDetails());
	        receiptDtos.add(dto);
	    }
	    return receiptDtos;
	}

	@Override
	public void deleteReceipt(Long appointmentId) {
		FinalPriceReceipt receipt = finalPriceReceiptRepository.findByAppointment_AppointmentId(appointmentId)
				.orElseThrow(() -> new NoReceiptFoundException("No receipt found with Id: " +appointmentId));
		
		finalPriceReceiptRepository.deleteById(receipt.getReceiptId());
	}
	
	@Override
	public BigDecimal totalPriceCalculator(List<String> serviceNames, List<BigDecimal> priceList) {
		BigDecimal totalPrice = BigDecimal.ZERO;
				
		for (BigDecimal price : priceList) {		//streams or for each loop can be used
		    totalPrice = totalPrice.add(price);
		}
		return totalPrice;
	}

	@Override
	public Map<String, BigDecimal> serviceDetailsMapCreator(List<String> serviceNames, List<BigDecimal> priceList) {
		
		// Ensure both lists have the same size
		if (serviceNames.size() != priceList.size()) {
			throw new BadRequestException("The service names list and prices list must have the same size.");
		}

		// Create a Map
		Map<String, BigDecimal> serviceDetails = new HashMap<>();
		
		// Populate the map
		for (int i = 0; i < serviceNames.size(); i++) {
			serviceDetails.put(serviceNames.get(i), priceList.get(i));
		}
		return serviceDetails;
	}

}
