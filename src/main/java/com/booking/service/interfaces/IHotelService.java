package com.booking.service.interfaces;

import com.booking.service.request.RegisterHotel;
import com.booking.service.response.hotel.HotelResponseDto;
import com.booking.service.response.hotel.HotelResponseDtoWithBookings;

import java.util.List;
import java.util.Optional;

public interface IHotelService {
    List<HotelResponseDtoWithBookings> allHotel();
    HotelResponseDto findHotelById(Long id);
    HotelResponseDto saveHotel(RegisterHotel registerHotel);
    HotelResponseDto updateHotel(Long idHotelUpdate,RegisterHotel hotelManager);
    boolean deleteHotelById(Long id);
}
