package com.booking.service.response.booking;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookingResponseDto( Long idRoom,
                                  String diaDeEntrada,
                                  String diaDesalida,
                                  String descripcionHabitacion) {
}
