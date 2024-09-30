package com.booking.controller;

import com.booking.exception.hotel.HotelNotFoundException;
import com.booking.service.impl.HotelService;
import com.booking.service.request.RegisterHotel;
import com.booking.service.response.hotel.HotelResponseDto;
import com.booking.service.response.hotel.HotelResponseDtoWithBookings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping()
    public ResponseEntity<List<HotelResponseDtoWithBookings>> allHotels(){
        return ResponseEntity.ok(hotelService.allHotel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findHotelById(@PathVariable Long id){
        return ResponseEntity.ok(hotelService.findHotelById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseDto> updateHotel(@PathVariable Long id,@RequestBody RegisterHotel registerHotel){
        return ResponseEntity.ok(hotelService.updateHotel(id,registerHotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotelById(@PathVariable Long id) {
        try{
            boolean deleted = hotelService.deleteHotelById(id);
            return ResponseEntity.ok("Hotel deleted successfully");
        }catch (HotelNotFoundException h){
            return new ResponseEntity<>("Hotel not found",HttpStatus.NOT_FOUND);
        }
    }

}
