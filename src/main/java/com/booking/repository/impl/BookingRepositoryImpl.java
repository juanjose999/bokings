package com.booking.repository.impl;

import com.booking.entiti.Booking;
import com.booking.repository.interfaces.IBookingRepository;
import com.booking.repository.interfaces.jpa.IBookingRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryImpl implements IBookingRepository {

    private final IBookingRepositoryJPA jpa;
    @Override
    public List<Booking> allBookings() {
        return jpa.findAll();
    }

    @Override
    public Booking findBookingById(long id) {
        Optional<Booking> findBooking = jpa.findById(id);
        if(findBooking.isPresent()){
            return findBooking.get();
        }else{
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return jpa.save(booking);
    }

    @Override
    public Booking updateBooking(long idBookingToUpdate, Booking booking) {
        Optional<Booking> findBooking = jpa.findById(idBookingToUpdate);
        if(findBooking.isPresent()){
            Booking updateBooking = findBooking.get();
            updateBooking.setNameHotel(booking.getNameHotel());
            updateBooking.setLocation(booking.getLocation());
            updateBooking.setDescription(booking.getDescription());
            updateBooking.setDatestart(booking.getDatestart());
            updateBooking.setPrice(booking.getPrice());
            updateBooking.setRoomtype(booking.getRoomtype());
            return jpa.save(updateBooking);
        }else{
            throw new NoSuchElementException("Booking not found");
        }
    }

    @Override
    public boolean deleteBookingById(long idBooking) {
        if(findBookingById(idBooking)!=null){
            jpa.deleteById(idBooking);
            return true;
        }else{
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    public boolean deleteAll() {
        if(!jpa.findAll().isEmpty()){
            jpa.deleteAll();
            return true;
        }else{
            throw new NoSuchElementException("User list is empty");
        }
    }
}
