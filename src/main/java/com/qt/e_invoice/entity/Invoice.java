package com.qt.e_invoice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long customerId;

    @Column(nullable = false)
    private String amount;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime invoiceDate;
}