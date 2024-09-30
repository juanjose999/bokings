package com.booking;

import com.booking.controller.CustomerController;
import com.booking.entiti.*;
import com.booking.repository.impl.RoomRepositoryImpl;
import com.booking.repository.interfaces.ICustomerRepository;
import com.booking.repository.interfaces.IHotelRepository;
import com.booking.repository.interfaces.IRoomRepository;
import com.booking.repository.interfaces.jpa.IInvoiceRepositoryJPA;
import com.booking.service.interfaces.IBookingService;
import com.booking.service.interfaces.ICustomerService;
import com.booking.service.request.RegisterRentRoom;
import com.booking.service.response.invoice.InvoiceMapper;
import com.booking.service.response.invoice.InvoiceResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CustomerRentRoomTest {

    @Mock
    private ICustomerService customerService;
    @Mock
    private IBookingService bookingService;

    @Mock
    private IHotelRepository hotelRepository;

    @Mock
    private RoomRepositoryImpl roomRepository;

    @Mock
    private ICustomerRepository customerRepository;

    @Mock
    private IInvoiceRepositoryJPA invoiceRepository;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testRentRoomAndGenerateInvoice() throws Exception {
        // Datos de prueba
        Hotel hotel = Hotel.builder()
                .idHotel(1L)
                .fullNameHotel("Hotel Example")
                .city("City A")
                .location("Location A")
                .phone("123456789")
                .build();

        Room room = Room.builder()
                .idroom(1L)
                .description("Room with sea view")
                .location("Location A")
                .pricefordayrent(100.0)
                .isAvailable(true)
                .hotel(hotel)
                .build();

        Customer customer = Customer.builder()
                .idCustomer(1L)
                .fullName("John Doe")
                .identification("123456789")
                .email("john@example.com")
                .password("password")
                .build();

        RegisterRentRoom rentRequest = new RegisterRentRoom(1L, 1L, 1L, 5, "2024-10-01", "2024-10-05");

        Invoice expectedInvoice = Invoice.builder()
                .issueDate("2024-09-26 15:30")
                .nameHotel(hotel.getFullNameHotel())
                .city(hotel.getCity())
                .location(hotel.getLocation())
                .phoneHotel(hotel.getPhone())
                .startDayBooking(LocalDate.of(2024, 10, 1))
                .endDayBooking(LocalDate.of(2024, 10, 5))
                .idRoom(room.getIdroom())
                .nameCustomer(customer.getFullName())
                .identityCardCustomer(customer.getIdentification())
                .build();

        // Simular los métodos de los repositorios
        when(hotelRepository.findHotelById(rentRequest.idHotel())).thenReturn(hotel);
        when(customerRepository.findCustomerById(rentRequest.idClient())).thenReturn(customer);
        when(roomRepository.findRoomById(rentRequest.idRoom())).thenReturn(room);
        when(customerService.rentRoomAvailable(rentRequest)).thenReturn(InvoiceMapper.entityToDto(expectedInvoice));

    }


}

