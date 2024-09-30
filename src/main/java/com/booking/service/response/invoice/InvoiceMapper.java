package com.booking.service.response.invoice;

import com.booking.entiti.Invoice;
import com.booking.service.response.invoice.InvoiceResponseDto;

public class InvoiceMapper {
    public static InvoiceResponseDto entityToDto(Invoice invoice){
        return InvoiceResponseDto.builder()
                .issueDate(invoice.getIssueDate())
                .nameHotel(invoice.getNameHotel())
                .city(invoice.getCity())
                .location(invoice.getLocation())
                .phoneHotel(invoice.getPhoneHotel())
                .startDayBooking(String.valueOf(invoice.getStartDayBooking()))
                .endDayBooking(String.valueOf(invoice.getEndDayBooking()))
                .idRoom(String.valueOf(invoice.getIdRoom()))
                .nameCustomer(invoice.getNameCustomer())
                .identityCardCustomer(invoice.getIdentityCardCustomer())
                .email(invoice.getEmailCustomer())
                .build();
    }
}
