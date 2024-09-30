package com.booking.exception.hotel;

public class HotelListIsEmptyException extends RuntimeException {
    public HotelListIsEmptyException(String message) {
        super(message);
    }
}
