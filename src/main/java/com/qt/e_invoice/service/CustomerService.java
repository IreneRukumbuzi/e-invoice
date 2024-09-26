package com.qt.e_invoice.service;

import com.qt.e_invoice.dto.CustomerDto;
import com.qt.e_invoice.entity.Customer;
import com.qt.e_invoice.repository.CustomerRepository;
import com.qt.e_invoice.utils.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customersRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerDto register(CustomerDto registrationRequest) {
        CustomerDto resp = new CustomerDto();
        try {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(registrationRequest.getEmail());
            newCustomer.setName(registrationRequest.getName());
            newCustomer.setPhoneNumber(registrationRequest.getPhoneNumber());
            newCustomer.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            Customer savedCustomer = customersRepo.save(newCustomer);

            if (savedCustomer.getId() > 0) {
                resp.setCustomer(savedCustomer);
                resp.setMessage("Customer Saved Successfully");
                resp.setStatusCode(HttpStatus.CREATED);
            }
        } catch (Exception e) {
            resp.setStatusCode(HttpStatus.BAD_REQUEST);
            resp.setMessage("Error creating customer " + e.getLocalizedMessage());
        }
        return resp;
    }

    public CustomerDto login(CustomerDto customerDto) {
        CustomerDto response = new CustomerDto();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    customerDto.getEmail(), customerDto.getPassword()));
            var customer = customersRepo.findByEmail(customerDto.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(customer);

            response.setStatusCode(HttpStatus.OK);
            response.setToken(jwt);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");
        } catch (AuthenticationException e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.setMessage("Invalid Credentials");
        }
        return response;
    }

    public String extractEmailFromToken(String token) {
        return jwtUtils.extractUsername(token.replace("Bearer ", ""));
    }
}
