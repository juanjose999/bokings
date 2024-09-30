package com.booking.controller;

import com.booking.service.impl.CustomerService;
import com.booking.service.impl.HotelService;
import com.booking.service.request.RegisterCustomerForm;
import com.booking.service.request.RegisterHotel;
import com.booking.service.response.customer.CustomerResponseDto;
import com.booking.service.response.hotel.HotelResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.datatype.DatatypeConfigurationException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomerService customerService;
    private final HotelService hotelService;

    @PostMapping("/customer")
    public ResponseEntity<CustomerResponseDto> saveCustomer(@RequestBody RegisterCustomerForm customerForm) throws NoSuchFieldException, DatatypeConfigurationException {
        return ResponseEntity.ok(customerService.saveCustomer(customerForm));
    }

    @PostMapping("/hotel")
    public ResponseEntity<HotelResponseDto> saveHotel(@RequestBody RegisterHotel registerHotel){
        return ResponseEntity.ok(hotelService.saveHotel(registerHotel));
    }


}
