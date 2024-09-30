package com.booking.service.response.room;

import com.booking.entiti.Hotel;
import com.booking.service.response.hotel.HotelResponseDtoWithBookings;
import lombok.Builder;

import java.util.List;
@Builder
public record AllRoomWIthBooking(List<HotelResponseDtoWithBookings> hotel) {
}
