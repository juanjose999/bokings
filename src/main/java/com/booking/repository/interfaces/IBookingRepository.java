package com.booking.repository.interfaces;

import com.booking.entiti.Booking;

import java.util.List;
import java.util.Optional;

public interface IBookingRepository {
    List<Booking> allBookings();
    Booking findBookingById(long id);
    Booking saveBooking(Booking booking);
    Booking updateBooking(long idBookingToUpdate, Booking booking);
    boolean deleteBookingById(long idBooking);
    boolean deleteAll();
}
