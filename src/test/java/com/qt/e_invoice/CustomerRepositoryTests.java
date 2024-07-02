package com.qt.e_invoice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.qt.e_invoice.entity.Customer;
import com.qt.e_invoice.repository.CustomerRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser () {
        Customer user =  new Customer();

        user.setEmail("test@gmail.com");
        user.setName("John");
        user.setPhoneNumber("0785088582");
        user.setPassword("Pass1234");

        Customer savedUser = repo.save(user);

        Customer existingUser = entityManager.find(Customer.class, savedUser.getId());

        assertThat(existingUser.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    public void testFindUserByEmail() {
        String email = "test@gmail.com";

        Optional<Customer> foundUser = repo.findByEmail(email);

        assertThat(foundUser).isNotNull();
    }
}