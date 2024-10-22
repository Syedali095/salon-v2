package com.mysalon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysalon.dto.FinalPriceReceiptDto;
import com.mysalon.service.FinalPriceReceiptService;

@RestController
@RequestMapping("/receipt")
public class FinalPriceReceiptController {

	@Autowired
	private FinalPriceReceiptService finalPriceReceiptService;
	
	@GetMapping("/{receiptId}")
	public ResponseEntity<FinalPriceReceiptDto> getReceiptById(@PathVariable Long receitId){
		FinalPriceReceiptDto finalPriceReceiptDto = finalPriceReceiptService.getReceiptById(receitId);
		return new ResponseEntity<>(finalPriceReceiptDto, HttpStatus.OK);
	}
	
	@GetMapping("/{appointmentId}")
	public ResponseEntity<FinalPriceReceiptDto> getReceiptByAppointmentId(@PathVariable Long appointmentId){
		FinalPriceReceiptDto finalPriceReceiptDto = finalPriceReceiptService.getReceiptByAppointmentId(appointmentId);
		return new ResponseEntity<>(finalPriceReceiptDto, HttpStatus.OK);
	}
	
	@GetMapping("/{custId}")
	public ResponseEntity<List<FinalPriceReceiptDto>> getReceiptListByCustId(@PathVariable Long custId){
		List<FinalPriceReceiptDto> finalPriceReceiptDto = finalPriceReceiptService.getReceiptListById(custId);
		return new ResponseEntity<>(finalPriceReceiptDto, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<FinalPriceReceiptDto>> getAllReceiptList(){
		List<FinalPriceReceiptDto> finalPriceReceiptDto = finalPriceReceiptService.getAllReceipt();
		return new ResponseEntity<>(finalPriceReceiptDto, HttpStatus.OK);
	}
}
