package com.booking.entiti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idroom;
    @Column(nullable = false, length = 255)
    private String description;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Double pricefordayrent;
    @Column(nullable = false)
    private boolean isAvailable;
    @Column(nullable = false)
    private String roomtype;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    @OneToMany(mappedBy = "room",fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<Booking> bookingroom;

    public void setBookingroom(Booking bookingroom) {
        this.bookingroom.add(bookingroom);
    }
}

