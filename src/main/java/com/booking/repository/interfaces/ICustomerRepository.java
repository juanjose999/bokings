package com.booking.repository.interfaces;

import com.booking.entiti.Customer;
import com.booking.service.request.RegisterCustomerForm;

import java.util.List;
import java.util.Optional;

public interface ICustomerRepository {
    List<Customer> allCustomer ();
    Customer findCustomerById(long id);
    Customer saveCustomer(Customer customer);
    Customer updateCustomer(long idCustomerUpdate, Customer customer) throws NoSuchFieldException;
    boolean deleteCustomerById(long id);
}
