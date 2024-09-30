package com.booking.entiti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idbooking;
    @Column(nullable = false, length = 40)
    private String nameHotel;

    @Column(nullable = false,length = 40)
    private String location;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private String datestart;

    @Column(nullable = false)
    private String dateend;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String roomtype;

    @ManyToMany(mappedBy = "bookings")
    @JsonManagedReference
    private List<Customer> customers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoiceList = new ArrayList<>();
    {
        // Bloque de inicialización
        if (invoiceList == null) {
            invoiceList = new ArrayList<>();
        }
    }

    public Booking(String nameHotel, String location, String description, String datestart, String dateend, Double price, String roomtype, List<Customer> customers, Room room) {
        this.nameHotel = nameHotel;
        this.location = location;
        this.description = description;
        this.datestart = datestart;
        this.dateend = dateend;
        this.price = price;
        this.roomtype = roomtype;
        this.customers = customers;
        this.room = room;
        this.invoiceList = new ArrayList<>();
    }

    public void setInvoiceList(Invoice invoice) {
        if (invoiceList == null) {
            invoiceList = new ArrayList<>();  // Inicializamos si es nula
        }
        this.invoiceList.add(invoice);
        invoice.setBooking(this);  // Asegura la relación bidireccional
    }




}
