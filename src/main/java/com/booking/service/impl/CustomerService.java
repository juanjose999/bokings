package com.booking.service.impl;

import com.booking.entiti.Invoice;
import com.booking.entiti.Booking;
import com.booking.entiti.Customer;
import com.booking.entiti.Hotel;
import com.booking.entiti.Room;
import com.booking.exception.customer.CustomerNotFoundException;
import com.booking.exception.customer.InvalidDateException;
import com.booking.exception.room.RoomNotCreate;
import com.booking.repository.interfaces.IBookingRepository;
import com.booking.repository.interfaces.ICustomerRepository;
import com.booking.repository.interfaces.IHotelRepository;
import com.booking.repository.interfaces.IRoomRepository;
import com.booking.repository.interfaces.jpa.IInvoiceRepositoryJPA;
import com.booking.service.interfaces.ICustomerService;
import com.booking.service.request.RegisterBooking;
import com.booking.service.request.RegisterCustomerForm;
import com.booking.service.request.RegisterRentRoom;
import com.booking.service.response.booking.BookingMapper;
import com.booking.service.response.customer.CustomerMapper;
import com.booking.service.response.customer.CustomerResponseDto;
import com.booking.service.response.invoice.InvoiceMapper;
import com.booking.service.response.invoice.InvoiceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final IHotelRepository hotelRepository;
    private final IRoomRepository roomRepository;
    private final IBookingRepository bookingRepository;
    private final IInvoiceRepositoryJPA invoiceRepository;

    public List<CustomerResponseDto> allCustomers(){
        return CustomerMapper.entitysToDtos(
                customerRepository.allCustomer());
    }

    public CustomerResponseDto findCustomerById(Long id) throws NoSuchFieldException {
        return CustomerMapper.entityToDto(customerRepository.findCustomerById(id));
    }
    public CustomerResponseDto saveCustomer(RegisterCustomerForm customerForm) throws NoSuchFieldException, DatatypeConfigurationException {
        System.out.println("Validando correo: " + customerForm.email());
        if (!isValidEmail(customerForm.email())) {
            throw new DatatypeConfigurationException("Email invalid");
        }
        Customer customer = customerRepository.saveCustomer(CustomerMapper.registerToEntity(customerForm));
        return CustomerMapper.entityToDto(customer);
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    @Override
    public InvoiceResponseDto rentRoomAvailable(RegisterRentRoom registerRentRoom) throws NoSuchFieldException {
        Optional<Hotel> hotel = Optional.ofNullable(hotelRepository.findHotelById(registerRentRoom.idHotel()));
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findCustomerById(registerRentRoom.idClient()));
        Optional<Room> room = Optional.ofNullable(roomRepository.findRoomById(registerRentRoom.idRoom()));
        if(hotel.isPresent()&&customer.isPresent()&&room.isPresent()){

            Hotel hotelObj = hotel.get();
            Customer customerObj = customer.get();
            Room roomObj = room.get();

            LocalDate dateStartNewBooking= changeToLocalDate(registerRentRoom.dateStart());
            LocalDate dateEndNewBooking = changeToLocalDate(registerRentRoom.dateEnd());
            Long daysOfRentRoom = ChronoUnit.DAYS.between(dateStartNewBooking,dateEndNewBooking);

            if(dateStartNewBooking.isBefore(LocalDate.now())){
                throw new RuntimeException("Date start booking invalid");
            }

            for (Booking bookingCurrent : roomObj.getBookingroom()) {
                LocalDate existingStart = LocalDate.parse(bookingCurrent.getDatestart());
                LocalDate existingEnd = LocalDate.parse(bookingCurrent.getDateend());

                // Verificar si la habitación ya está ocupada en la fecha actual
                if (!(dateEndNewBooking.isBefore(existingStart) || dateStartNewBooking.isAfter(existingEnd))) {
                    roomObj.setAvailable(false);
                    throw new RoomNotCreate("Room is currently not available");
                }
            }


            roomObj.setAvailable(false);
            roomObj.setHotel(hotelObj);
            hotelObj.setRoomList(roomObj);
            hotelRepository.updateHotel(hotelObj.getIdHotel(),hotelObj);

            List<Customer> customerList = new ArrayList<>(List.of(customerObj));


            RegisterBooking registerBookingN = RegisterBooking.builder()
                    .nameHotel(hotelObj.getFullNameHotel())
                    .location(hotelObj.getLocation())
                    .description(roomObj.getDescription())
                    .dateStart(registerRentRoom.dateStart())
                    .dateEnd(registerRentRoom.dateEnd())
                    .price(roomObj.getPricefordayrent())
                    .build();


            Booking booking = BookingMapper.registerToEntity(registerBookingN);
            booking.setRoomtype(roomObj.getRoomtype());
            booking.setRoom(roomObj);
            booking.setCustomers(customerList);
            bookingRepository.saveBooking(booking);

            roomObj.setBookingroom(booking);
            roomRepository.updateRoom(roomObj.getIdroom(),roomObj);

            customerObj.setBookings(booking);
            customerRepository.updateCustomer(customerObj.getIdCustomer(),customerObj);

            Invoice invoice = Invoice.builder()
                    .issueDate(getIssueDate())
                    .nameHotel(hotelObj.getFullNameHotel())
                    .city(hotelObj.getCity())
                    .location(hotelObj.getLocation())
                    .phoneHotel(hotelObj.getPhone())
                    .startDayBooking(LocalDate.parse(booking.getDatestart()))
                    .endDayBooking(LocalDate.parse(booking.getDateend()))
                    .idRoom(roomObj.getIdroom())
                    .nameCustomer(customerObj.getFullName())
                    .identityCardCustomer(customerObj.getIdentification())
                    .emailCustomer(customerObj.getEmail())
                    .booking(booking)
                    .build();

            Invoice savedInvoice = invoiceRepository.save(invoice);
            booking.setInvoiceList(savedInvoice);
            bookingRepository.updateBooking(booking.getIdbooking(),booking);
            return InvoiceMapper.entityToDto(savedInvoice);
        }else{
            throw new RoomNotCreate("Error in saved rent room");
        }
    }
    private LocalDate convertToLocalDates(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate changeToLocalDate = LocalDate.parse(time,formatter);
        if(isValidateTime(changeToLocalDate)){
            return changeToLocalDate;
        }else{
            throw new RuntimeException("Date invalid");
        }
    }
    private boolean isValidateTime(LocalDate dateUser){
        return dateUser.isAfter(LocalDate.now()) || dateUser.equals(LocalDate.now());
    }

    private LocalDate changeToLocalDate (String dateNewRentRoom){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            return LocalDate.parse(dateNewRentRoom,dateTimeFormatter);
        }catch (DateTimeParseException e){
            throw new InvalidDateException("Date input invalid");
        }
    }
    private String getIssueDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(dateTimeFormatter);
    }
    public CustomerResponseDto updateCustomer(long id, RegisterCustomerForm customerForm) throws NoSuchFieldException {
        return CustomerMapper.entityToDto(customerRepository.updateCustomer(id, CustomerMapper.registerToEntity(customerForm)));
    }

    public boolean deleteCustomerById(Long id){
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findCustomerById(id));
        if (customer.isPresent()) {
            customerRepository.deleteCustomerById(id);
            return true;
        } else {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }
    }

}
