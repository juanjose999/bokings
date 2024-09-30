package com.booking.service.request;

import lombok.Builder;

@Builder
public record RegisterCustomerForm(String fullName,
                                   String identification,
                                   String email,
                                   String password) {
}
