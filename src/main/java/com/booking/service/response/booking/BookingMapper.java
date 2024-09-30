package com.booking.service.response.booking;

import com.booking.entiti.Booking;
import com.booking.service.request.RegisterBooking;

public class BookingMapper {

    public static BookingResponseDto entityToDto(Booking booking){
        return BookingResponseDto.builder()
                .idRoom(booking.getIdbooking())
                .diaDeEntrada(booking.getDatestart())
                .diaDesalida(booking.getDateend())
                .descripcionHabitacion(booking.getDescription())
                .build();
    }
    public static Booking registerToEntity(RegisterBooking registerBooking){
        return Booking.builder()
                .nameHotel(registerBooking.nameHotel())
                .location(registerBooking.location())
                .description(registerBooking.description())
                .datestart(registerBooking.dateStart())
                .dateend(registerBooking.dateEnd())
                .price(registerBooking.price())
                .build();
    }

}
