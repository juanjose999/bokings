package com.booking.service.interfaces;

import com.booking.entiti.Invoice;
import com.booking.service.request.RegisterCustomerForm;
import com.booking.service.request.RegisterRentRoom;
import com.booking.service.response.customer.CustomerResponseDto;
import com.booking.service.response.invoice.InvoiceResponseDto;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;

public interface ICustomerService {
    List<CustomerResponseDto> allCustomers();
    CustomerResponseDto findCustomerById(Long id) throws NoSuchFieldException;
    CustomerResponseDto saveCustomer(RegisterCustomerForm registerCustomer) throws NoSuchFieldException, DatatypeConfigurationException;
    InvoiceResponseDto rentRoomAvailable(RegisterRentRoom registerRentRoom) throws NoSuchFieldException;
    CustomerResponseDto updateCustomer(long idCustomerUpdate, RegisterCustomerForm registerCustomerForm) throws NoSuchFieldException;
    boolean deleteCustomerById(Long id);
}
