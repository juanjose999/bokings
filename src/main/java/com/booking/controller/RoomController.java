package com.booking.controller;

import com.booking.exception.room.RoomNotFoundException;
import com.booking.service.interfaces.IRoomService;
import com.booking.service.request.RegisterRoom;
import com.booking.service.response.room.RoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService roomService;

    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        return ResponseEntity.ok(roomService.allRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findRoomById(id));
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@RequestBody RegisterRoom registerRoom) {
        return ResponseEntity.ok(roomService.saveRoom(registerRoom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RegisterRoom registerRoom) {;
        return ResponseEntity.ok(roomService.updateRoom(id,registerRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        try {
            boolean isDeleted = roomService.deleteRoom(id);
            if (isDeleted) {
                return ResponseEntity.ok("Room deleted successfully");
            } else {
                throw new RoomNotFoundException("Room with ID: " + id + " not found.");
            }
        } catch (RoomNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("Error deleting room", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}