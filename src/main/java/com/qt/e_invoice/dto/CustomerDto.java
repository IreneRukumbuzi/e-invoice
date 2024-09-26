package com.qt.e_invoice.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.qt.e_invoice.entity.Customer;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {

    private HttpStatus statusCode;
    private String message;
    private String token;
    private String expirationTime;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    private Customer customer;
}
