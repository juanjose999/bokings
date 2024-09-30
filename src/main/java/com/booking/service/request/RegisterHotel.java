package com.booking.service.request;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public record RegisterHotel(String fullNameHotel,
                            String city,
                            String location,
                            String email,
                            String password,
                            String phone) {
}
