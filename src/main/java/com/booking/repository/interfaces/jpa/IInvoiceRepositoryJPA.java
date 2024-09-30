package com.booking.repository.interfaces.jpa;

import com.booking.entiti.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepositoryJPA extends JpaRepository<Invoice,Long> {
}
