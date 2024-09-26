package com.qt.e_invoice;

import com.qt.e_invoice.dto.CustomerDto;
import com.qt.e_invoice.entity.Customer;
import com.qt.e_invoice.repository.CustomerRepository;
import com.qt.e_invoice.service.CustomerService;
import com.qt.e_invoice.utils.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerDto = new CustomerDto();
        customerDto.setEmail("test@example.com");
        customerDto.setPassword("password");
        customerDto.setName("Test User");
        customerDto.setPhoneNumber("123456789");
    }

    @Test
    void testRegisterSuccess() {
        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        CustomerDto response = customerService.register(customerDto);

        assertEquals("Customer Saved Successfully", response.getMessage());
        assertEquals(201, response.getStatusCode().value());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testLoginSuccess() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));
        when(jwtUtils.generateToken(customer)).thenReturn("dummyToken");

        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        CustomerDto response = customerService.login(customerDto);

        assertEquals("Successfully Logged In", response.getMessage());
        assertEquals(200, response.getStatusCode().value());
        assertEquals("dummyToken", response.getToken());
    }

    @Test
    void testLoginInvalidCredentials() {
        doThrow(new RuntimeException()).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        CustomerDto response = customerService.login(customerDto);

        assertEquals("Invalid Credentials", response.getMessage());
        assertEquals(401, response.getStatusCode().value());
    }
}
