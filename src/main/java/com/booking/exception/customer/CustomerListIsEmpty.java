package com.booking.exception.customer;

public class CustomerListIsEmpty extends RuntimeException {
    public CustomerListIsEmpty(String message) {
        super(message);
    }
}
