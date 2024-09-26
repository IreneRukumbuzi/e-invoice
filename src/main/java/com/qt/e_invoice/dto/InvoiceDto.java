package com.qt.e_invoice.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.qt.e_invoice.entity.Invoice;
import com.qt.e_invoice.entity.InvoiceStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDto {

    private HttpStatus statusCode;
    private String message;

    private String amount;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    private LocalDateTime invoiceDate;

    private Invoice invoice;
    private List<Invoice> invoices;
}