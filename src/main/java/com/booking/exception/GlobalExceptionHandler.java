package com.booking.exception;

import com.booking.exception.customer.*;
import com.booking.exception.hotel.HotelListIsEmptyException;
import com.booking.exception.hotel.HotelNotCreateException;
import com.booking.exception.hotel.HotelNotFoundException;
import com.booking.exception.room.RoomListIsEmpty;
import com.booking.exception.room.RoomNotCreate;
import com.booking.exception.room.RoomNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<String> handleInvalidException(InvalidDateException i){
        return new ResponseEntity<>(i.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerRentRoomException.class)
    public ResponseEntity<String> handleCustomerRentException(CustomerRentRoomException c){
        return new ResponseEntity<>(c.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomerNotCreateException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotCreateException c){
        return new ResponseEntity<>(c.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerListIsEmpty.class)
    public ResponseEntity<String> handleCustomerListEmpty(CustomerListIsEmpty c){
        return new ResponseEntity<>(c.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException c){
        return new ResponseEntity<>(c.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<String> handleHotelFoundException(HotelNotFoundException h){
        return new ResponseEntity<>(h.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HotelListIsEmptyException.class)
    public ResponseEntity<String> handleHotelListIsEmpty(HotelListIsEmptyException h){
        return new ResponseEntity<>(h.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HotelNotCreateException.class)
    public ResponseEntity<String> handleHotelNotCreate(HotelNotCreateException h){
        return new ResponseEntity<>(h.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<String> handleRoomNotFoundException(RoomNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);  // Devuelve 404 con el mensaje de error
    }

    @ExceptionHandler(RoomNotCreate.class)
    public ResponseEntity<String> handleRoomNotCreate(RoomNotCreate h){
        return new ResponseEntity<>(h.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoomListIsEmpty.class)
    public ResponseEntity<String> handleRoomListIsEmpty(RoomListIsEmpty r){
        return new ResponseEntity<>(r.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return new ResponseEntity<>("Error while creating: " + ex.getRootCause().getMessage(), HttpStatus.BAD_REQUEST);
    }
}
