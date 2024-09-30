package com.booking.service.interfaces;

import com.booking.service.request.RegisterBooking;
import com.booking.service.response.booking.BookingResponseDto;

import java.util.List;

public interface IBookingService {
    List<BookingResponseDto> allBookings();
    BookingResponseDto findBookingById(long id);
    BookingResponseDto saveBooking(RegisterBooking registerBooking);
    BookingResponseDto updateBooking(long idBookingToUpdate, RegisterBooking booking);
    boolean deleteBookingById(long idBooking);
    boolean deleteAll();
}
