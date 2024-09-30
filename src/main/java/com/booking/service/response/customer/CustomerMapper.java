package com.booking.service.response.customer;

import com.booking.entiti.Booking;
import com.booking.entiti.Customer;
import com.booking.entiti.Invoice;
import com.booking.service.request.RegisterCustomerForm;
import com.booking.service.response.invoice.InvoiceMapper;
import com.booking.service.response.invoice.InvoiceResponseDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class CustomerMapper {
    public static List<CustomerResponseDto> entitysToDtos(List<Customer> customer){
        if (customer == null) {
            return Collections.emptyList();
        }
        List<CustomerResponseDto> customerResponseDtos = customer.stream()
                .map(c -> {
                    List<InvoiceResponseDto> invoiceDto = c.getBookings() != null
                            ? c.getBookings().stream()
                            .filter(b -> b.getInvoiceList() != null) // Filtra las bookings que tienen una lista de invoices no nula
                            .flatMap(b -> b.getInvoiceList().stream()) // Aplanamos las listas de invoices
                            .map(InvoiceMapper::entityToDto) // Convertimos cada invoice a su DTO
                            .toList() // Convertimos el stream a una lista
                            : new ArrayList<>(); // Si no hay bookings, devuelve una lista vacía

                    // Construimos el DTO de Customer
                    return CustomerResponseDto.builder()
                            .id(c.getIdCustomer())
                            .nombre(c.getFullName())
                            .invoice(invoiceDto) // Asigna la lista de invoices (vacía si no hay)
                            .build();
                })
                .toList(); // Convertimos el stream de CustomerResponseDto a una lista

        return customerResponseDtos;
    }

    public static CustomerResponseDto entityToDto(Customer customer) throws NoSuchFieldException {
        List<InvoiceResponseDto> invoiceDto = customer.getBookings() != null
                ? customer.getBookings().stream() // Stream de bookings
                .filter(b -> b.getInvoiceList() != null) // Filtra bookings con lista de facturas no nula
                .flatMap(b -> b.getInvoiceList().stream()) // Aplana las listas de invoices en un único stream
                .map(InvoiceMapper::entityToDto) // Mapea cada Invoice a su DTO
                .toList() // Convierte el stream de InvoiceResponseDto en una lista
                : new ArrayList<>();

        return CustomerResponseDto.builder()
                .id(customer.getIdCustomer())
                .nombre(customer.getFullName())
                .email(customer.getEmail())
                .invoice(invoiceDto)
                .build();
    }
    public static  Customer registerToEntity(RegisterCustomerForm customerForm){
        return Customer.builder()
                .fullName(customerForm.fullName())
                .identification(customerForm.identification())
                .email(customerForm.email())
                .password(customerForm.password())
                .build();
    }
}
