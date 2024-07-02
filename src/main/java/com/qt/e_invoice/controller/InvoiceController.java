package com.qt.e_invoice.controller;

import com.qt.e_invoice.entity.Invoice;
import com.qt.e_invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/")
    public List<Invoice> getInvoices(){
        return invoiceService.getAllInvoices();
    }

    @PostMapping("/")
    public Invoice saveInvoice(@RequestBody Invoice invoice){
        return invoiceService.saveInvoice(invoice);
    }
}