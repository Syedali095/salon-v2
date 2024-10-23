package com.mysalon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

//import com.mysalon.dto.FinalPriceReceiptDto;
import com.mysalon.entity.FinalPriceReceipt;

public interface FinalPriceReceiptRepository extends JpaRepository<FinalPriceReceipt, Long>{
	
	//Optional<FinalPriceReceiptDto> findByAppointment_AppointmentId(Long appointmentId);
	
	List<FinalPriceReceipt> findByAppointment_Customer_CustId(Long custId);
	
	Optional<FinalPriceReceipt> findByAppointment_AppointmentId(Long appointmentId);
	
}
