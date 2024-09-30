package com.booking.entiti;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idinvoice;
    @Column(nullable = false, length = 100)
    private String issueDate;
    @Column(nullable = false)
    private String nameHotel;
    @Column(nullable = false,length = 60)
    private String city;
    @Column(nullable = false,length = 60)
    private String location;
    @Column(nullable = false,length = 60)
    private String phoneHotel;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDayBooking;
    @Column(nullable = false,length = 60)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDayBooking;
    @Column(nullable = false,length = 60)
    private Long idRoom;
    @Column(nullable = false,length = 60)
    private String nameCustomer;
    @Column(nullable = false,length = 60)
    private String identityCardCustomer;
    @Column(nullable = false,length = 60)
    private String emailCustomer;
    @ManyToOne()
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
