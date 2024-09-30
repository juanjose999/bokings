package com.booking.service.response.invoice;

import lombok.Builder;

@Builder
public record InvoiceResponseDto (String issueDate,
                                  String nameHotel,
                                  String city,
                                  String location,
                                  String phoneHotel,
                                  String startDayBooking,
                                  String endDayBooking,
                                  String idRoom,
                                  String nameCustomer,
                                  String identityCardCustomer,
                                  String email){
}
