package com.booking.service.interfaces;

import com.booking.service.request.RegisterRoom;
import com.booking.service.response.room.RoomResponseDto;

import java.util.List;

public interface IRoomService {
    List<RoomResponseDto> allRooms();
    RoomResponseDto findRoomById(Long id);
    RoomResponseDto saveRoom(RegisterRoom roomSavedForm);
    RoomResponseDto updateRoom(long idRoomUpdate, RegisterRoom roomAvailable);
    boolean deleteRoom(long idRoom);
}
