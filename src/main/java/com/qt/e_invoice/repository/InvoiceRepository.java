package com.qt.e_invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qt.e_invoice.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
  List<Invoice> findByCustomerId(long customerId);
}
