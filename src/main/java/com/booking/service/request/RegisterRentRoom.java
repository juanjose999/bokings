package com.booking.service.request;

public record RegisterRentRoom(long idClient,
                               long idHotel,
                               long idRoom,
                               int totalDaysRent,
                               String dateStart,
                               String dateEnd) {
}

