package com.booking.repository.interfaces.jpa;

import com.booking.entiti.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHotelRepositoryJPA extends JpaRepository<Hotel,Long> {
    boolean existsByEmail(String email);
    Hotel findByFullNameHotel(String hotelName);
}
