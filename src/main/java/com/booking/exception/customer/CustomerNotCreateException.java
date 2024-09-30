package com.booking.exception.customer;

public class CustomerNotCreateException extends RuntimeException{
    public CustomerNotCreateException(String message){
        super(message);
    }
}
