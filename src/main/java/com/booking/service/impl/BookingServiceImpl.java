package com.booking.service.impl;

import com.booking.repository.interfaces.IBookingRepository;
import com.booking.service.interfaces.IBookingService;
import com.booking.service.request.RegisterBooking;
import com.booking.service.response.booking.BookingMapper;
import com.booking.service.response.booking.BookingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService{

    private final IBookingRepository bookingRepository;

    @Override
    public List<BookingResponseDto> allBookings() {
        return bookingRepository.allBookings().stream()
                .map(b -> BookingMapper.entityToDto(b))
                .toList();
    }

    @Override
    public BookingResponseDto findBookingById(long id) {
        return BookingMapper.entityToDto(bookingRepository.findBookingById(id));
    }

    @Override
    public BookingResponseDto saveBooking(RegisterBooking registerBooking) {
        return BookingMapper.entityToDto(bookingRepository.saveBooking(BookingMapper.registerToEntity(registerBooking)));
    }

    @Override
    public BookingResponseDto updateBooking(long idBookingToUpdate, RegisterBooking booking) {
        return BookingMapper.entityToDto(bookingRepository.updateBooking(idBookingToUpdate, BookingMapper.registerToEntity(booking)));
    }

    @Override
    public boolean deleteBookingById(long idBooking) {
        return bookingRepository.deleteBookingById(idBooking);
    }

    @Override
    public boolean deleteAll() {
        return bookingRepository.deleteAll();
    }
}
