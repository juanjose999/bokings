package com.booking.service.response.hotel;
import com.booking.entiti.Room;
import com.booking.service.response.room.RoomResponseDtoWithBookingInside;
import lombok.Builder;

import java.util.List;
@Builder
public record HotelResponseDtoWithBookings(
                                           Long idHotel,
                                           String fullNameHotel,
                                           List<RoomResponseDtoWithBookingInside> rooms) {
}
