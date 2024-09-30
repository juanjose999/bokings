package com.booking.service.response.room;

import lombok.Builder;

@Builder
public record RoomResponseDto(Long idRoom ,
                              Long idHotel,
                              String description,
                              String location,
                              Double price,
                              String roomType) {
}
