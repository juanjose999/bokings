package com.booking.service.response.room;

import com.booking.entiti.Room;
import com.booking.service.request.RegisterRoom;

public class RoomMapper {
    public static RoomResponseDto entityToDto(Room room){
        return RoomResponseDto.builder()
                .idRoom(room.getIdroom())
                .idHotel(room.getHotel().getIdHotel())
                .description(room.getDescription())
                .location(room.getLocation())
                .price(room.getPricefordayrent())
                .roomType(room.getRoomtype())
                .build();
    }

    public static Room registerToEntity(RegisterRoom room){
        return Room.builder()
                .description(room.descripcion())
                .location(room.location())
                .pricefordayrent(room.priceRoomForDay())
                .roomtype(room.roomType())
                .build();
    }


}
