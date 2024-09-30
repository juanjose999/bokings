package com.booking.repository.interfaces.jpa;

import com.booking.entiti.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepositoryJPA extends JpaRepository<Customer,Long> {
    Customer findByFullName(String name);
    boolean existsByEmail(String email);
}
