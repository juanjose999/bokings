package com.booking.service.response.room;

import com.booking.entiti.Invoice;
import com.booking.service.response.invoice.InvoiceResponseDto;
import lombok.Builder;

import java.util.List;
@Builder
public record RoomResponseDtoWithBookingInside(Long idRoom,
                                               String description,
                                               String location,
                                               Double priceForDay,
                                               String roomType,
                                               boolean isAvailable,
                                               List<InvoiceResponseDto> bookings) {
}
