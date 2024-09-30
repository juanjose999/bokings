package com.booking.controller;

import com.booking.entiti.Invoice;
import com.booking.exception.customer.CustomerNotFoundException;
import com.booking.exception.customer.CustomerRentRoomException;
import com.booking.service.impl.CustomerService;
import com.booking.service.interfaces.ICustomerService;
import com.booking.service.request.RegisterCustomerForm;
import com.booking.service.request.RegisterRentRoom;
import com.booking.service.response.customer.CustomerResponseDto;
import com.booking.service.response.invoice.InvoiceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<CustomerResponseDto>> allCustomers(){
        return ResponseEntity.ok(customerService.allCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findCustomerById(@PathVariable Long id) throws NoSuchFieldException {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @PostMapping("/rent")
    public InvoiceResponseDto rentRoom(@RequestBody RegisterRentRoom registerRentRoom) throws NoSuchFieldException {
        if(registerRentRoom!=null){
            Long currentUserId = customerService.findCustomerById(registerRentRoom.idClient()).id();
            if(!currentUserId.equals(registerRentRoom.idClient())){
                throw new CustomerNotFoundException("Customer not found");
            }
            return customerService.rentRoomAvailable(registerRentRoom);
        }else{
            throw new CustomerRentRoomException("form register rent room is empty");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @RequestBody RegisterCustomerForm customerForm) throws NoSuchFieldException {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerForm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id){
        if(customerService.deleteCustomerById(id)){
            return ResponseEntity.ok("Customer delete is sussefully");
        }else{
            throw new CustomerNotFoundException("Customer not found");
        }
    }


}
