package com.booking.service.response.hotel;

import com.booking.entiti.Hotel;
import com.booking.service.request.RegisterHotel;

public class HotelMapper {
    public static Hotel formRegisterToEntity(RegisterHotel registerHotel){
        return Hotel.builder()
                .fullNameHotel(registerHotel.fullNameHotel())
                .city(registerHotel.city())
                .location(registerHotel.location())
                .email(registerHotel.email())
                .password(registerHotel.password())
                .phone(registerHotel.phone())
                .build();
    }

    public static HotelResponseDto formEntityToResponse(Hotel hotel){
        return new HotelResponseDto(hotel.getIdHotel(),hotel.getFullNameHotel(), hotel.getEmail());
    }


}
