package com.booking.service.request;

import lombok.Builder;

@Builder
public record RegisterRoom(Long idHotel,
                           String location,
                           String descripcion,
                           Double priceRoomForDay,
                           String roomType) {
}
