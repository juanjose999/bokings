package com.booking.service.request;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
@Builder
public record RegisterBooking(String nameHotel,
                              String location,
                              String description,
                              String dateStart,
                              String dateEnd,
                              Double price){

}
