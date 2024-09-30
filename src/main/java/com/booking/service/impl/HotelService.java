package com.booking.service.impl;

import com.booking.entiti.Booking;
import com.booking.entiti.Hotel;
import com.booking.entiti.Invoice;
import com.booking.entiti.Room;
import com.booking.repository.interfaces.IHotelRepository;
import com.booking.service.interfaces.IHotelService;
import com.booking.service.request.RegisterHotel;
import com.booking.service.response.hotel.HotelMapper;
import com.booking.service.response.hotel.HotelResponseDto;
import com.booking.service.response.hotel.HotelResponseDtoWithBookings;
import com.booking.service.response.invoice.InvoiceMapper;
import com.booking.service.response.invoice.InvoiceResponseDto;
import com.booking.service.response.room.RoomResponseDtoWithBookingInside;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class HotelService implements IHotelService {

    private final IHotelRepository hotelRepository;

    public List<HotelResponseDtoWithBookings> allHotel(){
        List<Hotel> allH = hotelRepository.allHotel();
        List<HotelResponseDtoWithBookings> allHotelsDto = new ArrayList<>();

        for(Hotel h : allH){
            List<Room> rooms = h.getRoomList();
            List<RoomResponseDtoWithBookingInside> roomsDto = new ArrayList<>();

            for (Room r : rooms) {
                List<InvoiceResponseDto> invoiceList = new ArrayList<>();

                if (r.getBookingroom() != null && !r.getBookingroom().isEmpty()) {
                    for (Booking b : r.getBookingroom()) {
                        for (Invoice i : b.getInvoiceList()) {
                            invoiceList.add(InvoiceMapper.entityToDto(i));
                        }
                    }
                }

                RoomResponseDtoWithBookingInside roomDto = RoomResponseDtoWithBookingInside.builder()
                        .idRoom(r.getIdroom())
                        .description(r.getDescription())
                        .location(r.getLocation())
                        .priceForDay(r.getPricefordayrent())
                        .roomType(r.getRoomtype())
                        .bookings(invoiceList)  // lista vacía si no hay bookings
                        .build();

                roomsDto.add(roomDto);
            }

            HotelResponseDtoWithBookings hotelDtoWithBookings = HotelResponseDtoWithBookings.builder()
                    .idHotel(h.getIdHotel())
                    .fullNameHotel(h.getFullNameHotel())
                    .rooms(roomsDto)
                    .build();

            allHotelsDto.add(hotelDtoWithBookings);
        }
        return allHotelsDto;
    }

    public HotelResponseDto findHotelById(Long id){
        return HotelMapper.formEntityToResponse
                (hotelRepository.findHotelById(id));
    }

    public HotelResponseDto saveHotel(RegisterHotel registerHotel){
        Hotel hotelSaved = hotelRepository.saveHotel(HotelMapper.formRegisterToEntity(registerHotel));
        return new HotelResponseDto(hotelSaved.getIdHotel(),hotelSaved.getFullNameHotel(),hotelSaved.getEmail());
    }

    public HotelResponseDto updateHotel(Long id, RegisterHotel registerHotel){
        return HotelMapper.formEntityToResponse(hotelRepository.updateHotel
                (id,HotelMapper.formRegisterToEntity(registerHotel)));
    }

    public boolean deleteHotelById(Long id){
        return hotelRepository.deleteHotelById(id);
    }
}
