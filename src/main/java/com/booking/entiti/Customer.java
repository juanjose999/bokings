package com.booking.entiti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;

    @Column(nullable = false,length = 60)
    private String fullName;

    @Column(nullable = false,unique = true)
    @Size(min = 10, max = 10, message = "The identification number must be exactly 10 characters long")
    private String identification;

    @Column(nullable = false, length = 60, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "customer_bookings",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    @JsonBackReference
    private List<Booking> bookings = new ArrayList<>();

    public void setBookings(Booking bookings) {
        this.bookings.add(bookings);
    }
}
