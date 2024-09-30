package com.booking;

import com.booking.controller.HotelController;
import com.booking.entiti.*;
import com.booking.exception.hotel.HotelNotFoundException;
import com.booking.repository.interfaces.IHotelRepository;
import com.booking.service.impl.HotelService;
import com.booking.service.request.RegisterHotel;
import com.booking.service.response.hotel.HotelMapper;
import com.booking.service.response.hotel.HotelResponseDto;
import com.booking.service.response.hotel.HotelResponseDtoWithBookings;
import com.booking.service.response.invoice.InvoiceMapper;
import com.booking.service.response.invoice.InvoiceResponseDto;
import com.booking.service.response.room.RoomResponseDtoWithBookingInside;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HotelTest {

    @Mock
    private HotelService hotelService;

    @Mock
    private IHotelRepository IHotelRepository;
    @InjectMocks
    private HotelController hotelController;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(new HotelController(hotelService)).build();
    }

    @Test
    public void testSavedHotel() throws Exception {
        // Datos de prueba para el registro del hotel
        RegisterHotel registerHotel = RegisterHotel.builder()
                .fullNameHotel("Casona")
                .city("Pamplona")
                .email("casona@gmail.com")
                .location("Calle 3# 23-42")
                .password("1234")
                .phone("13412414")
                .build();

        // Convertir el registro a JSON
        String registerHotelToJson = objectMapper.writeValueAsString(registerHotel);

        // Crear un HotelResponseDto para la respuesta esperada
        Hotel hotel = HotelMapper.formRegisterToEntity(registerHotel);
        HotelResponseDto hotelResponseDto = HotelMapper.formEntityToResponse(hotel);

        // Simular el servicio
        when(hotelService.saveHotel(any(RegisterHotel.class))).thenReturn(hotelResponseDto);

        // Realizar la solicitud y verificar la respuesta
        mockMvc.perform(post("/hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerHotelToJson))
                .andExpect(status().isOk()) // Verificar que el estado es 200 OK
                .andExpect(content().json(objectMapper.writeValueAsString(hotelResponseDto))); // Verificar el contenido
    }

    @Test
    public void testAllHotels() throws Exception {
        Hotel hotel1 = Hotel.builder()
                .idHotel(1L)
                .fullNameHotel("Casona")
                .city("Pamplona")
                .location("Calle 3# 23-42")
                .email("casona@gmail.com")
                .password("1234")
                .phone("13412414")
                .build();

        Invoice invoice = Invoice.builder()
                .idinvoice(1L)
                .issueDate("2024-10-01")
                .nameHotel("Casona")
                .city("Bogota")
                .location("Calle 3# 23-42")
                .phoneHotel("13412414")
                .idRoom(1L)
                .nameCustomer("diego")
                .identityCardCustomer("1234456789")
                .emailCustomer("diego@gmail.com")
                .build();

        InvoiceResponseDto invoiceResponseDto = InvoiceMapper.entityToDto(invoice);
        List<InvoiceResponseDto> invoiceResponseDtos = Collections.singletonList(invoiceResponseDto);

        Room room = Room.builder()
                .idroom(1L)
                .description("Habitacion individual con cama doble y baño privado")
                .location("Bogota")
                .pricefordayrent(40000.0)
                .isAvailable(true)
                .roomtype("individual")
                .hotel(hotel1)
                .build();



        RoomResponseDtoWithBookingInside roomsDto = RoomResponseDtoWithBookingInside.builder()
                .idRoom(1l)
                .description(room.getDescription())
                .location(room.getLocation())
                .priceForDay(room.getPricefordayrent())
                .roomType(room.getRoomtype())
                .isAvailable(room.isAvailable())
                .bookings(invoiceResponseDtos)
                .build();
        List<RoomResponseDtoWithBookingInside> roomResponseDtoWithBookingInsides = Collections.singletonList(roomsDto);

        HotelResponseDtoWithBookings hotelsResponseDtos = HotelResponseDtoWithBookings.builder()
                .idHotel(hotel1.getIdHotel())
                .fullNameHotel(hotel1.getFullNameHotel())
                .rooms(roomResponseDtoWithBookingInsides)
                .build();

        when(hotelService.allHotel()).thenReturn(Collections.singletonList(hotelsResponseDtos));

        mockMvc.perform(get("/hotel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(hotelsResponseDtos))));
    }

    @Test
    public void testFindHotelById_Success() throws Exception {
        Long hotelId = 1L;
        HotelResponseDto hotelResponseDto = new HotelResponseDto(1L,"Casona", "casona@gmail.com");

        when(hotelService.findHotelById(hotelId)).thenReturn(hotelResponseDto);

        mockMvc.perform(get("/hotel/{id}", hotelId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(hotelResponseDto)));
    }

    public String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteHotelById_Success() throws Exception {
        Long hotelId = 1L;

        Hotel hotel = Hotel.builder()
                .idHotel(hotelId)
                .fullNameHotel("Residencial El Sol")
                .city("Barcelona")
                .location("Carrer del Sol 45")
                .email("residencialelsol@gmail.com")
                .password("password123")
                .phone("987654321")
                .build();

        when(hotelService.deleteHotelById(hotelId)).thenReturn(true);

        mockMvc.perform(delete("/hotel/{id}", hotelId))
                .andExpect(status().isOk()) // Verificar estado 200 OK
                .andExpect(content().string("Hotel deleted successfully"));

        // Verificar que el servicio fue invocado con el ID correcto
        verify(hotelService).deleteHotelById(hotelId);
    }

}
