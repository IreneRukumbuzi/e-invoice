package com.qt.e_invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.qt.e_invoice.dto.InvoiceDto;
import com.qt.e_invoice.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/")
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody InvoiceDto invoice) {
        InvoiceDto response = invoiceService.saveInvoice(invoice);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<InvoiceDto> getInvoices() {
        InvoiceDto response = invoiceService.getAllInvoices();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable long id) {
        InvoiceDto response = invoiceService.getInvoiceById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InvoiceDto> deleteInvoice(@PathVariable long id) {
        InvoiceDto response = invoiceService.deleteInvoice(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
