package com.qt.e_invoice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.qt.e_invoice.entity.Customer;
import com.qt.e_invoice.entity.Invoice;
import com.qt.e_invoice.repository.InvoiceRepository;

@Service
public class InvoiceService {

  @Autowired
  private InvoiceRepository invoiceRepo;

  public List<Invoice> getAllInvoices() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Customer currentUser = (Customer) authentication.getPrincipal();

    long customerId = currentUser.getId();

    List<Invoice> invoices = new ArrayList<>();

    invoices = invoiceRepo.findAll();

    return invoices;
  }

  public Invoice saveInvoice(Invoice invoice) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Customer currentUser = (Customer) authentication.getPrincipal();

    long customerId = currentUser.getId();

    invoice.setCustomerId(customerId);

    return invoiceRepo.save(invoice);
  }
}
