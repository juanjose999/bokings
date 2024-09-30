package com.booking.repository.impl;

import com.booking.entiti.Hotel;
import com.booking.exception.hotel.HotelListIsEmptyException;
import com.booking.exception.hotel.HotelNotCreateException;
import com.booking.exception.hotel.HotelNotFoundException;
import com.booking.exception.room.RoomNotFoundException;
import com.booking.repository.interfaces.IHotelRepository;
import com.booking.repository.interfaces.jpa.IHotelRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HotelRepositoryImpl implements IHotelRepository {

    private final IHotelRepositoryJPA jpa;

    @Override
    public List<Hotel> allHotel() {
        if(jpa.findAll().isEmpty()){
            throw new HotelListIsEmptyException("Hotels list is empty");
        }
        return jpa.findAll();
    }

    @Override
    public Hotel findHotelById(long id) {
        Optional<Hotel> findHotel = jpa.findById(id);
        if(findHotel.isPresent()){
            return findHotel.get();
        }else{
            throw new HotelNotFoundException("Hotel with ID: " + id + " not found.");
        }
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        try{
            return jpa.save(hotel);
        }catch (HotelNotCreateException e){
            throw new RoomNotFoundException("Fail create new hotel");
        }
    }

    @Override
    public Hotel updateHotel(long idHotelUpdate, Hotel hotelManager) {
        Optional<Hotel> existingHotel = jpa.findById(idHotelUpdate);
        if (existingHotel.isPresent()) {
            Hotel hotel = existingHotel.get();
            hotel.setFullNameHotel(hotelManager.getFullNameHotel());
            hotel.setCity(hotelManager.getCity());
            hotel.setLocation(hotelManager.getLocation());
            if (!hotel.getEmail().equals(hotelManager.getEmail())) {
                if (jpa.existsByEmail(hotelManager.getEmail())) {
                    throw new IllegalArgumentException("Email is already in use by another customer");
                }
                hotel.setEmail(hotelManager.getEmail());
            }
            hotel.setPassword(hotelManager.getPassword());
            hotel.setPhone(hotelManager.getPhone());
            return jpa.save(hotel);
        } else {
            throw new HotelNotFoundException("Hotel with ID: " + idHotelUpdate+ " not found.");
        }
    }

    @Override
    public boolean deleteHotelById(long id) {
        Optional<Hotel> existingHotel = jpa.findById(id);
        if(existingHotel.isPresent()){
            jpa.deleteById(id);
            return true;
        }else{
            throw new HotelNotFoundException("Room with ID: " + id + " not found.");
        }
    }
}
