package com.booking.repository.interfaces;

import com.booking.entiti.Room;
import com.booking.service.request.RegisterRoom;

import java.util.List;

public interface IRoomRepository {
    List<Room> allRooms();
    Room findRoomById(long id);
    Room saveRoom(Room room);
    Room updateRoom(long idRoomUpdate, Room roomAvailable);
    boolean deleteRoom(long idRoom);
}
