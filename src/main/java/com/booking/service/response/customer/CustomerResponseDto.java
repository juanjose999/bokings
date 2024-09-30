package com.booking.service.response.customer;

import com.booking.service.response.invoice.InvoiceResponseDto;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public record CustomerResponseDto(Long id,
                                  String nombre,
                                  String email,
                                  List<InvoiceResponseDto> invoice) {
}
