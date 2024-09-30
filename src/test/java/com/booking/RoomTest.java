package com.booking;

import com.booking.controller.RoomController;
import com.booking.entiti.Hotel;
import com.booking.entiti.Invoice;
import com.booking.entiti.Room;
import com.booking.repository.impl.RoomRepositoryImpl;
import com.booking.repository.interfaces.IRoomRepository;
import com.booking.service.impl.IRoomServiceImpl;
import com.booking.service.interfaces.IRoomService;
import com.booking.service.request.RegisterRoom;
import com.booking.service.response.invoice.InvoiceMapper;
import com.booking.service.response.invoice.InvoiceResponseDto;
import com.booking.service.response.room.RoomMapper;
import com.booking.service.response.room.RoomResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IRoomService roomService;

    @Mock
    private IRoomRepository repository;

    @InjectMocks
    private RoomController roomController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new RoomController(roomService)).build();
    }

    @Test
    public void testAllRooms() {
        Hotel hotel1 = Hotel.builder()
                .idHotel(1L)
                .fullNameHotel("Casona")
                .city("Pamplona")
                .location("Calle 3# 23-42")
                .email("casona@gmail.com")
                .password("1234")
                .phone("13412414")
                .build();
        Room room = Room.builder()
                .idroom(1L)
                .description("Habitacion individual con cama doble y baño privado")
                .location("Bogota")
                .pricefordayrent(40000.0)
                .isAvailable(true)
                .roomtype("individual")
                .hotel(hotel1)
                .build();
        List<RoomResponseDto> responseDto = Collections.singletonList(RoomMapper.entityToDto(room));
        when(roomService.allRooms()).thenReturn(responseDto); // Simula habitaciones

        assertEquals(1, responseDto.size());
        assertNotNull(responseDto);
    }

    @Test
    public void testFindRoomById_Success() throws Exception {
        Long roomId = 1L;

        // Crear un objeto Room y su correspondiente DTO
        Hotel hotel1 = Hotel.builder()
                .idHotel(1L)
                .fullNameHotel("Casona")
                .city("Pamplona")
                .location("Calle 3# 23-42")
                .email("casona@gmail.com")
                .password("1234")
                .phone("13412414")
                .build();
        Room room = Room.builder()
                .idroom(roomId)
                .description("Habitacion individual con cama doble y baño privado")
                .location("Bogota")
                .pricefordayrent(40000.0)
                .isAvailable(true)
                .roomtype("individual")
                .hotel(hotel1)
                .build();

        RoomResponseDto roomResponseDto = RoomMapper.entityToDto(room);

        // Simular el comportamiento del servicio
        when(roomService.findRoomById(roomId)).thenReturn(roomResponseDto);

        // Ejecutar la solicitud con MockMvc y validar el resultado
        mockMvc.perform(get("/rooms/{id}", roomId))
                .andExpect(status().isOk()) // Verificar que el estatus HTTP sea 200 (OK)
                .andExpect(jsonPath("$.idRoom").value(roomId)) // Verificar que el id de la respuesta sea el esperado
                .andExpect(jsonPath("$.description").value("Habitacion individual con cama doble y baño privado")) // Verificar la descripción
                .andExpect(jsonPath("$.price").value(40000.0)); // Verificar el precio por día
    }

    @Test
    public void testSaveRoom() throws Exception {
        Hotel hotel1 = Hotel.builder()
                .idHotel(1L)
                .fullNameHotel("Casona")
                .city("Pamplona")
                .location("Calle 3# 23-42")
                .email("casona@gmail.com")
                .password("1234")
                .phone("13412414")
                .build();
        Hotel hotel = Hotel.builder()
                .idHotel(1L)
                .fullNameHotel("Casona")
                .city("Pamplona")
                .location("Calle 3# 23-42")
                .email("casona@gmail.com")
                .password("1234")
                .phone("13412414")
                .build();
        Room room = Room.builder()
                .idroom(1l)
                .description("Habitacion individual con cama doble y baño privado")
                .location("Bogota")
                .pricefordayrent(40000.0)
                .isAvailable(true)
                .roomtype("individual")
                .hotel(hotel)
                .build();
        RegisterRoom registerRoom = RegisterRoom.builder()
                .idHotel(hotel1.getIdHotel())
                .descripcion(room.getDescription())
                .location(room.getLocation())
                .priceRoomForDay(room.getPricefordayrent())
                .roomType(room.getRoomtype())
                .build();
        RoomResponseDto roomResponseDto = RoomMapper.entityToDto(room);
        String jsonContent = objectMapper.writeValueAsString(roomResponseDto);

        when(roomService.saveRoom(any(RegisterRoom.class))).thenReturn(roomResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/rooms") // Cambia esta URL a la ruta correcta
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk()) // Asegúrate de que el estado sea 200 OK
                .andExpect(jsonPath("$.idRoom").value(1L));
    }

    @Test
    public void testUpdateRoom_Success() throws Exception {
        Long roomId = 1L;
        RegisterRoom registerRoom = RegisterRoom.builder()
                .idHotel(1l)
                .descripcion("habitacion individual")
                .location("Bogota")
                .priceRoomForDay(83.000)
                .roomType("INDIVIDUAL")
                .build();
        Hotel hotel1 = Hotel.builder()
                .idHotel(1L)
                .fullNameHotel("Casona")
                .city("Pamplona")
                .location("Calle 3# 23-42")
                .email("casona@gmail.com")
                .password("1234")
                .phone("13412414")
                .build();
        Room room = Room.builder()
                .idroom(1l)
                .description("habitacion individual")
                .location("Bogota")
                .pricefordayrent(32.000)
                .hotel(hotel1)
                .roomtype("PERSONAL")
                .build();
        RoomResponseDto roomResponseDto = RoomMapper.entityToDto(room);

        when(roomService.updateRoom(roomId,registerRoom)).thenReturn(roomResponseDto);
        String jsonContent = objectMapper.writeValueAsString(registerRoom);

        mockMvc.perform(put("/rooms/{id}", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());
    }

}
