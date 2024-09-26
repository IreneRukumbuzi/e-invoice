package com.qt.e_invoice;

import com.qt.e_invoice.dto.InvoiceDto;
import com.qt.e_invoice.entity.Invoice;
import com.qt.e_invoice.entity.InvoiceStatus;
import com.qt.e_invoice.repository.InvoiceRepository;
import com.qt.e_invoice.service.InvoiceService;
import com.qt.e_invoice.service.RabbitMQProducer;
import com.qt.e_invoice.utils.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @InjectMocks
    private InvoiceService invoiceService;

    private InvoiceDto invoiceDto;

    @BeforeEach
    void setUp() {
        invoiceDto = new InvoiceDto();
        invoiceDto.setAmount("1000.0");
        invoiceDto.setStatus(InvoiceStatus.PAID);
    }

    @Test
    void testSaveInvoiceSuccess() {
        when(jwtUtils.getCustomerId()).thenReturn(1L);
        Invoice savedInvoice = new Invoice();
        savedInvoice.setId(1L);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(savedInvoice);

        InvoiceDto response = invoiceService.saveInvoice(invoiceDto);

        assertEquals("Invoice saved successfully", response.getMessage());
        assertEquals(200, response.getStatusCode().value());
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
        verify(rabbitMQProducer, times(1)).sendInvoiceNotification(anyString());
    }

    @Test
    void testGetInvoiceByIdSuccess() {
        when(jwtUtils.getCustomerId()).thenReturn(1L);
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        when(invoiceRepository.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(Optional.of(invoice));

        InvoiceDto response = invoiceService.getInvoiceById(1L);

        assertEquals("Invoice retrieved successfully", response.getMessage());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getInvoice());
    }

    @Test
    void testUpdateInvoiceSuccess() {
        when(jwtUtils.getCustomerId()).thenReturn(1L);
        Invoice existingInvoice = new Invoice();
        existingInvoice.setId(1L);
        when(invoiceRepository.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(Optional.of(existingInvoice));

        InvoiceDto response = invoiceService.updateInvoice(1L, invoiceDto);

        assertEquals("Invoice updated successfully", response.getMessage());
        assertEquals(200, response.getStatusCode().value());
    }
}
