package com.qt.e_invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qt.e_invoice.dto.InvoiceDto;
import com.qt.e_invoice.entity.Invoice;
import com.qt.e_invoice.repository.InvoiceRepository;
import com.qt.e_invoice.utils.JWTUtils;

@Service
public class InvoiceService {

  @Autowired
  private InvoiceRepository invoiceRepo;

  @Autowired
  private RabbitMQProducer rabbitMQProducer;

  @Autowired
  private JWTUtils jwtUtils;

  public InvoiceDto saveInvoice(InvoiceDto invoice) {
    InvoiceDto response = new InvoiceDto();
    try {
      long customerId = jwtUtils.getCustomerId();

      Invoice newInvoice = new Invoice();

      newInvoice.setCustomerId(customerId);
      newInvoice.setAmount(invoice.getAmount());
      newInvoice.setInvoiceDate(invoice.getInvoiceDate());
      newInvoice.setStatus(invoice.getStatus());

      Invoice savedInvoice = invoiceRepo.save(newInvoice);

      response.setInvoice(savedInvoice);
      response.setStatusCode(HttpStatus.OK);
      response.setMessage("Invoice saved successfully");

      rabbitMQProducer.sendInvoiceNotification("Invoice saved: " + savedInvoice.getId());

    } catch (Exception e) {
      response.setStatusCode(HttpStatus.BAD_REQUEST);
      response.setMessage("Error occurred while saving invoice: " + e.getMessage());
    }
    return response;
  }

  public InvoiceDto getAllInvoices() {
    InvoiceDto response = new InvoiceDto();
    try {
      long customerId = jwtUtils.getCustomerId();
      List<Invoice> invoices = invoiceRepo.findByCustomerId(customerId);
      response.setInvoices(invoices);
      response.setStatusCode(HttpStatus.OK);
      response.setMessage("Invoices retrieved successfully");

      rabbitMQProducer.sendInvoiceNotification("Invoices retrieved");

    } catch (Exception e) {
      response.setStatusCode(HttpStatus.BAD_REQUEST);
      response.setMessage("Error occurred while retrieving invoices: " + e.getMessage());
    }
    return response;
  }

  public InvoiceDto getInvoiceById(long id) {
    InvoiceDto response = new InvoiceDto();
    try {
      long customerId = jwtUtils.getCustomerId();
      Optional<Invoice> invoice = invoiceRepo.findByIdAndCustomerId(id, customerId);
      if (invoice.isPresent()) {
        response.setInvoice(invoice.get());
        response.setStatusCode(HttpStatus.OK);
        response.setMessage("Invoice retrieved successfully");

        rabbitMQProducer.sendInvoiceNotification("Invoice retrieved: " + id);
      } else {
        response.setStatusCode(HttpStatus.NOT_FOUND);
        response.setMessage("Invoice not found");
      }
    } catch (Exception e) {
      response.setStatusCode(HttpStatus.BAD_REQUEST);
      response.setMessage("Error occurred while retrieving the invoice: " + e.getMessage());
    }
    return response;
  }

  public InvoiceDto deleteInvoice(long id) {
    InvoiceDto response = new InvoiceDto();
    try {
      long customerId = jwtUtils.getCustomerId();
      Optional<Invoice> invoice = invoiceRepo.findByIdAndCustomerId(id, customerId);
      if (invoice.isPresent()) {
        invoiceRepo.deleteById(id);
        response.setStatusCode(HttpStatus.OK);
        response.setMessage("Invoice deleted successfully");

        rabbitMQProducer.sendInvoiceNotification("Invoice deleted: " + id);
      } else {
        response.setStatusCode(HttpStatus.NOT_FOUND);
        response.setMessage("Invoice not found");
      }
    } catch (Exception e) {
      response.setStatusCode(HttpStatus.BAD_REQUEST);
      response.setMessage("Error occurred while deleting the invoice: " + e.getMessage());
    }
    return response;
  }
}
