package com.booking.repository.impl;

import com.booking.entiti.Booking;
import com.booking.entiti.Hotel;
import com.booking.entiti.Room;
import com.booking.exception.room.RoomListIsEmpty;
import com.booking.exception.room.RoomNotCreate;
import com.booking.exception.room.RoomNotFoundException;
import com.booking.repository.interfaces.IHotelRepository;
import com.booking.repository.interfaces.IRoomRepository;
import com.booking.repository.interfaces.jpa.IRoomRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements IRoomRepository {

    private final IRoomRepositoryJPA jpa;
    private final IHotelRepository hotelRepository;

    @Override
    public List<Room> allRooms() {
        if(jpa.findAll().isEmpty()){
            throw new RoomListIsEmpty("Rooms list is empty");
        }
        return jpa.findAll();
    }

    @Override
    public Room findRoomById(long id) {
        Optional<Room> findRoom = jpa.findById(id);
        if(findRoom.isPresent()){
            return findRoom.get();
        }else{
            throw new RoomNotFoundException("Room with ID: " + id + " not found.");
        }
    }

    @Override
    public Room saveRoom(Room room) {
        try{
            return jpa.save(room);
        }catch (RoomNotCreate e){
            throw new RoomNotFoundException("Fail create new room");
        }
    }

    @Override
    public Room updateRoom(long idRoomUpdate, Room room) {
        Optional<Room>room1 = Optional.ofNullable(findRoomById(idRoomUpdate));
        if(room1.isPresent()){
            Optional<Hotel> findHotel = Optional.ofNullable(room1.get().getHotel());
            if(findHotel.isPresent()){
                Room roomToUpdate = room1.get();
                roomToUpdate.setHotel(findHotel.get());
                roomToUpdate.setDescription(room.getDescription());
                roomToUpdate.setLocation(room.getLocation());
                roomToUpdate.setPricefordayrent(room.getPricefordayrent());
                roomToUpdate.setAvailable(room.isAvailable());
                roomToUpdate.setRoomtype(room.getRoomtype());
                return jpa.save(roomToUpdate);
            }else{
                throw new RoomNotFoundException("Hotel not found");
            }
        } else {
            throw new RoomNotFoundException("Room with ID: " + idRoomUpdate+ " not found.");
        }
    }

    @Override
    public  boolean deleteRoom(long idRoom) {
        Optional<Room> findRoom = jpa.findById(idRoom);
        if (findRoom.isPresent()) {
            Room room = findRoom.get();

            if (room.getHotel() != null) {
                Hotel hotel = room.getHotel();
                hotel.removeRoom(room);
                room.setHotel(null);
            }

            List<Booking> bokings = room.getBookingroom();
            for(Booking b : bokings){
                b.setRoom(null);
            }

            jpa.delete(room);
            return true;
        } else {
            throw new RoomNotFoundException("Room with ID: " + idRoom + " not found.");
        }
    }
}
