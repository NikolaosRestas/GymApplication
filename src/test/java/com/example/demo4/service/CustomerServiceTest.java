package com.example.demo4.service;

import com.example.demo4.model.County;
import com.example.demo4.model.Customer;
import com.example.demo4.model.Gym;
import com.example.demo4.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;
    @Mock
    CustomerRepository customerRepository;

    @Test
    void getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();

        County county  = new County(2L,"ioannina");
        Gym gym = new Gym(2L,"iraklis","meletiou",county);
        Customer requestCustomer = new Customer(1L,"nikos","kosma","nikos@gmail.com","452289653",gym);

        when(customerRepository.findAll()).thenReturn(customers);

        customers.add(requestCustomer);

        List<Customer> response = customerService.getAllCustomers();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("nikos");

    }

    @Test
    void findCustomerById() {
        County county = new County(2L,"ioannina");
        Gym gym = new Gym(2L,"iraklis","meletiou",county);
        Customer responseCustomer = new Customer(2L,"nikos","meletiou","nikos@gmail.com","695582147",gym);
        when(customerRepository.findById(2L)).thenReturn(Optional.of(responseCustomer));

        Customer response = customerService.findCustomerById(2L);
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getName()).isEqualTo("nikos");
    }

    @Test
    void deleteCustomerById() {
        Long requestCustomerId = 2L;

        when(customerRepository.deleteCustomerById(requestCustomerId)).thenReturn(Math.toIntExact(requestCustomerId));

        Boolean response = customerService.deleteCustomerById(requestCustomerId);

        assertThat(response).isEqualTo(true);
    }

}
