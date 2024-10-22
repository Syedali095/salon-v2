package com.mysalon.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysalon.entity.Address;
import com.mysalon.entity.Customer;
import com.mysalon.service.CustomerService;


@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	@Autowired
    private MockMvc mockMvc;

	@Autowired
    private ObjectMapper objectMapper;
	
    @MockBean
    private CustomerService customerService;

    private Customer customer;
    private Customer customer2;
    List<Customer> customerList= new ArrayList<>();
    
    @BeforeEach
    void setUp() {
    	customer = new Customer(1L, "Syed", "8880987765", "Syed@gmail.com", LocalDate.of(1998,07,12), 
        		new Address(1L, "SRD", "TS", "997876"));
        
        customer2 = new Customer(2L, "Aly", "9880987765", "aly@gmail.com", LocalDate.of(1999,07,12), 
        		new Address(2L, "Jog", "MH", "597876"));
        
        customerList.add(customer);
        customerList.add(customer2);
        //Here we are using list and adding more than 1 customer to it, so that we can test for getAllCustomers() method.
    }
    
    
    @Test
    void testAddCustomer() throws Exception {
        given(customerService.addCustomer(any(Customer.class))).willReturn(customer);

        mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(customer.getName())))
                .andExpect(jsonPath("$.contactNo", is(customer.getContactNo())));
    }
    
    
    @Test
    void testUpdateCustomer() throws Exception {
        given(customerService.updateCustomer(anyLong(), any(Customer.class))).willReturn(customer);

        mockMvc.perform(put("/customer/update/{custId}", 1L)
        		.contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(customer.getName())))
                .andExpect(jsonPath("$.contactNo", is(customer.getContactNo())));
    }
    
    @Test
    void testGetCustomer() throws Exception {
        given(customerService.getCustomer(anyLong())).willReturn(customer);

        mockMvc.perform(get("/customer/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(customer.getName())))
                .andExpect(jsonPath("$.contactNo", is(customer.getContactNo())));
    }
    
    @Test
    void testGetAllCustomers() throws Exception {
        //List<Customer> allCustomers = Collections.singletonList(customer);
    	//The above line is used if there is no list in serUp() i.e., there is only one customer
        given(customerService.getAllCustomers()).willReturn(customerList);

        mockMvc.perform(get("/customer/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(customer.getName())))
                .andExpect(jsonPath("$[0].contactNo", is(customer.getContactNo())))
                .andExpect(jsonPath("$[1].name", is(customer2.getName())))
                .andExpect(jsonPath("$[1].contactNo", is(customer2.getContactNo())));
    }
    
    @Test
    void testDeleteCustomer() throws Exception {
        doNothing().when(customerService).removeCustomer(anyLong());

        mockMvc.perform(delete("/customer/delete/{custId}", 1L))
                .andExpect(status().isNoContent());
    }
    
}
