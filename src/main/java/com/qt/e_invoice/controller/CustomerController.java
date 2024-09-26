package com.qt.e_invoice.controller;

import com.qt.e_invoice.dto.CustomerDto;
import com.qt.e_invoice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class CustomerController {
    @Autowired
    private CustomerService usersService;

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerDto reg) {
        CustomerDto response = usersService.register(reg);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerDto> login(@RequestBody CustomerDto req) {
        CustomerDto response = usersService.login(req);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
