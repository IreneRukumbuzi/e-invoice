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

  private long getCustomerId() {
    long customerId = 0;

    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Customer currentUser = (Customer) authentication.getPrincipal();
  
      customerId = currentUser.getId();
    } catch (Exception e) {
      throw new RuntimeException("User not found");
    }
   
    return customerId;
  }

  public List<Invoice> getAllInvoices() {
    List<Invoice> invoices = new ArrayList<>();

    invoices = invoiceRepo.findByCustomerId(getCustomerId());

    return invoices;
  }

  public Invoice saveInvoice(Invoice invoice) {
    invoice.setCustomerId(getCustomerId());

    return invoiceRepo.save(invoice);
  }

  public Invoice updateInvoice(Invoice invoice, long id) {
    Invoice updatedInvoice = new Invoice();
    boolean isInvoiceAvailable = invoiceRepo.existsById(id);

    if (isInvoiceAvailable) {
      updatedInvoice.setAmount(invoice.getAmount());
      updatedInvoice.setStatus(invoice.getStatus());
      updatedInvoice.setInvoiceDate(invoice.getInvoiceDate());
      invoiceRepo.save(updatedInvoice);
    }

    return updatedInvoice; 
  }

  public void deleteInvoice(long id) {
    boolean isInvoiceAvailable = invoiceRepo.existsById(id);

    if (isInvoiceAvailable) {
      invoiceRepo.deleteById(id);
    }
  }
}
