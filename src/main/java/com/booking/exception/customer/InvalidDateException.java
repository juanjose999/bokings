package com.booking.exception.customer;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String message){
        super(message);
    }
}
