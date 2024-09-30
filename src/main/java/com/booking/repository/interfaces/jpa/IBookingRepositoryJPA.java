package com.booking.repository.interfaces.jpa;

import com.booking.entiti.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepositoryJPA extends JpaRepository<Booking,Long> {
}
