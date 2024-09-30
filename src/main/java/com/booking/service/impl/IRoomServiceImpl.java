package com.booking.service.impl;

import com.booking.entiti.Hotel;
import com.booking.entiti.Room;
import com.booking.exception.room.RoomNotCreate;
import com.booking.repository.interfaces.IHotelRepository;
import com.booking.repository.interfaces.IRoomRepository;
import com.booking.service.interfaces.IRoomService;
import com.booking.service.request.RegisterRoom;
import com.booking.service.response.room.RoomMapper;
import com.booking.service.response.room.RoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IRoomServiceImpl implements IRoomService {

    private final IRoomRepository repository;
    private final IHotelRepository hotelRepository;
    public List<RoomResponseDto> allRooms(){
        return repository.allRooms().stream()
                .map(r -> RoomMapper.entityToDto(r))
                .toList();
    }

    public RoomResponseDto findRoomById(Long id){
        return RoomMapper.entityToDto(repository.findRoomById(id));
    }

    public RoomResponseDto saveRoom(RegisterRoom room){
        try {
            if(room.idHotel()==null){
                throw new RoomNotCreate("Failed to create room, hotel id is null");
            }
            Hotel hotel = hotelRepository.findHotelById(room.idHotel());
            if(hotel==null){
                throw new RoomNotCreate("Failed to create room, hotel not exist ");
            }
            Room savedRoom = repository.saveRoom(RoomMapper.registerToEntity(room));
            savedRoom.setHotel(hotel);
            repository.updateRoom(savedRoom.getIdroom(),savedRoom);
            hotel.setRoomList(savedRoom);
            return RoomMapper.entityToDto(savedRoom);

        } catch (DataIntegrityViolationException ex) {
            throw new RoomNotCreate("Failed to create room, invalid data: " + ex.getRootCause().getMessage());
        }
    }

    public RoomResponseDto updateRoom(long id, RegisterRoom room){
        return RoomMapper.entityToDto(repository.updateRoom(id, RoomMapper.registerToEntity(room)));
    }

    public boolean deleteRoom(long idRoom) {
        return repository.deleteRoom(idRoom);
    }

}
