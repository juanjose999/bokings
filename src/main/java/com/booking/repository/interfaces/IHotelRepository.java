package com.booking.repository.interfaces;

import com.booking.entiti.Hotel;

import java.util.List;
import java.util.Optional;

public interface IHotelRepository {

    List<Hotel> allHotel();
    Hotel findHotelById(long id);
    Hotel saveHotel(Hotel hotel);
    Hotel updateHotel(long idHotelUpdate,Hotel hotelManager);
    boolean deleteHotelById(long id);
}