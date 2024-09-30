package com.booking;

import com.booking.controller.CustomerController;
import com.booking.controller.RegisterController;
import com.booking.entiti.Customer;
import com.booking.service.impl.HotelService;
import com.booking.service.jwt.JwtService;
import com.booking.service.response.customer.CustomerResponseDto;
import com.booking.service.impl.CustomerService;
import com.booking.service.request.RegisterCustomerForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerTest {
    @MockBean
    private CustomerService customerService;
    @MockBean
    private JwtService jwtService;

    @MockBean
    private HotelService hotelService; // Añade el HotelService si se usa en el controlador


    @MockBean
    private CustomerController customerController;


    @MockBean
    private RegisterController registerController;


    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createCustomer() throws Exception {

        RegisterCustomerForm customerFormRegister = RegisterCustomerForm.builder()
                .fullName("Juan")
                .email("juan@gmail.com")
                .identification("123123")
                .password("xw232")
                .build();

        Customer customer = Customer.builder()
                .idCustomer(1L)
                .fullName("Juan")
                .email("juan@gmail.com")
                .identification("123123")
                .password("xw232")
                .build();

        CustomerResponseDto customerResponseDto = CustomerResponseDto.builder()
                .nombre("Juan")
                .email("juan@gmail.com")
                .build();

        // Aquí devolvemos directamente el CustomerResponseDto, no el ResponseEntity
        when(customerService.saveCustomer(any(RegisterCustomerForm.class)))
                .thenReturn(customerResponseDto);

        // Convertir a JSON usando ObjectMapper
        String customerJson = objectMapper.writeValueAsString(customer);
        String expectedResponseJson = objectMapper.writeValueAsString(customerResponseDto);

        // Imprimir JSONs para depuración
        System.out.println("Customer JSON: " + customerJson);
        System.out.println("Expected Response JSON: " + expectedResponseJson);

        mockMvc.perform(post("/auth/register/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson));

    }


    @Test
    public void testAllCustomerResponse() throws Exception {
        List<CustomerResponseDto> dtoList = List.of(
                CustomerResponseDto.builder().nombre("Juan").email("juan@gmail.com").build(),
                CustomerResponseDto.builder().nombre("Maria").email("maria@gmail.com").build()
        );

        when(customerService.allCustomers()).thenReturn(dtoList);
        mockMvc.perform(get("/customer")) // Asegúrate de que esta ruta es correcta
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtoList)));
    }

    @Test
    public void testFindCustomerById() throws Exception {
        Long idToFind = 2L;
        CustomerResponseDto customerResponseDto = CustomerResponseDto.builder()
                .nombre("Juan")
                .email("juan@gmail.com")
                .build();

        when(customerService.findCustomerById(idToFind)).thenReturn(customerResponseDto);

        mockMvc.perform(get("/customer/{id}", idToFind))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customerResponseDto)));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Long idToUpdate = 2L;

        RegisterCustomerForm customerForm = RegisterCustomerForm.builder()
                .fullName("Juan camilo sierra")
                .email("juan@gmail.com")
                .identification("123445567")
                .password("oldpassword123")
                .build();

        Customer customer = Customer.builder()
                .idCustomer(idToUpdate)
                .fullName("Juan danilo sierra")
                .email("juan@gmail.com")
                .identification("123456678")
                .password("newpassword")
                .build();

        CustomerResponseDto customerResponseDto = CustomerResponseDto.builder()
                .nombre("Juan Updated")
                .email("juan.updated@gmail.com")
                .build();

        when(customerService.updateCustomer(eq(idToUpdate), any(RegisterCustomerForm.class))).thenReturn(customerResponseDto);

        String customerJson = objectMapper.writeValueAsString(customerForm);

        mockMvc.perform(put("/customer/{id}",idToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customerResponseDto)));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        Long idToDelete = 4L;

        when(customerService.deleteCustomerById(idToDelete)).thenReturn(true);

        mockMvc.perform(delete("/customer/{id}",idToDelete))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer delete is sussefully"));

        verify(customerService, times(1)).deleteCustomerById(idToDelete);

    }


}
