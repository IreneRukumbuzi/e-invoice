package com.qt.e_invoice.service;

import com.qt.e_invoice.dto.ReqRes;
import com.qt.e_invoice.entity.Customer;
import com.qt.e_invoice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository usersRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ReqRes register(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();
        try {
            Customer ourUser = new Customer();
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setName(registrationRequest.getName());
            ourUser.setPhoneNumber(registrationRequest.getPhoneNumber());
            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            Customer ourUsersResult = usersRepo.save(ourUser);

            if (ourUsersResult.getId() > 0) {
                resp.setCustomer(ourUsersResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(201);
            } else {
                throw new RuntimeException("User registration failed");
            }
        } catch (Exception e) {
            throw new RuntimeException("User registration failed: " + e.getMessage());
        }
        return resp;
    }


    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);

            response.setStatusCode(200);
            response.setToken(jwt);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Bad credentials");
        }
        return response;
    }

    public String extractEmailFromToken(String token) {
        return jwtUtils.extractUsername(token.replace("Bearer ", ""));
    }
}
