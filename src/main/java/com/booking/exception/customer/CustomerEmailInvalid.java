package com.booking.exception.customer;

public class CustomerEmailInvalid extends RuntimeException{
    public CustomerEmailInvalid(String message){
        super(message);
    }
}
