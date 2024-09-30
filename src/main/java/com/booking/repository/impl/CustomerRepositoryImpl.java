package com.booking.repository.impl;

import com.booking.entiti.Customer;
import com.booking.exception.customer.CustomerListIsEmpty;
import com.booking.exception.customer.CustomerNotCreateException;
import com.booking.exception.customer.CustomerNotFoundException;
import com.booking.repository.interfaces.ICustomerRepository;
import com.booking.repository.interfaces.jpa.ICustomerRepositoryJPA;
import com.booking.service.response.customer.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements ICustomerRepository {
    private final ICustomerRepositoryJPA customerRepositoryJPA;
    @Override
    public List<Customer> allCustomer() {
        if(customerRepositoryJPA.findAll().isEmpty()){
            throw new CustomerListIsEmpty("Customer list is empty");
        }
        return customerRepositoryJPA.findAll();
    }

    @Override
    public Customer findCustomerById(long id) {
        Optional<Customer> findCustomer = customerRepositoryJPA.findById(id);
        if(findCustomer.isPresent()){
            return findCustomer.get();
        }else{
            throw new CustomerNotFoundException("Customer with ID : " + id + " not found");
        }
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        try{
            return customerRepositoryJPA.save(customer);
        }catch (CustomerNotCreateException c){
            throw new CustomerNotCreateException("Fail create new customer");
        }
    }


    @Override
    public Customer updateCustomer(long idCustomerUpdate, Customer customer) throws NoSuchFieldException {
        Optional<Customer> optionalCustomer = customerRepositoryJPA.findById(idCustomerUpdate);
        if (optionalCustomer.isPresent()) {
            Customer customerObj = optionalCustomer.get();
            customerObj.setFullName(customer.getFullName());
            customerObj.setEmail(customer.getEmail());
            customerObj.setIdentification(customer.getIdentification());
            customerObj.setPassword(customer.getPassword());
            return customerRepositoryJPA.save(customerObj);
        }
        else {
            throw new CustomerNotFoundException("Customer not found with ID: "+ idCustomerUpdate);
        }
    }

    @Override
    public boolean deleteCustomerById(long id) {
        Optional<Customer> findCustomer = customerRepositoryJPA.findById(id);
        if(findCustomer.isPresent()){
            customerRepositoryJPA.deleteById(id);
            return true;
        }else{
            throw new CustomerNotFoundException("Customer not found with ID: "+ id);
        }
    }
}
