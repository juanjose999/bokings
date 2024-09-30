package com.booking.entiti;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHotel;

    @Column(nullable = false, length = 80)
    @Size(min = 6, max = 80, message = "The name of the hotel should be longer")
    private String fullNameHotel;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 60)
    private String location;

    @Column(nullable = false, length = 120,unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    @Size(min = 10, max = 10, message = "The phone number must be exactly 10 characters long")
    private String phone;

    @OneToMany(mappedBy = "hotel",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Room> roomList = new ArrayList<>();

    public void setRoomList(Room roomList) {
        this.roomList.add(roomList);
    }

    public void removeRoom(Room room){
        this.roomList.remove(room);
    }
}
