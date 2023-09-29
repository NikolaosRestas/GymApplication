package com.example.demo4.service;

import com.example.demo4.model.County;
import com.example.demo4.model.Customer;
import com.example.demo4.model.Gym;
import com.example.demo4.model.dto.CustomerRequestDto;
import com.example.demo4.model.dto.GymRequestDto;
import com.example.demo4.repository.CustomerRepository;
import com.example.demo4.repository.GymRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    CustomerService customerService;
    GymService gymService;
    CountyService countyService;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    GymRepository gymRepository;

    @BeforeEach
    void setUp() {
        gymService = new GymService(gymRepository,countyService);
        customerService = new CustomerService(customerRepository, gymService);
    }

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

    @Test
    void insertCustomer() {
        County county = new County(1L, "ioannina");
        Gym gym  = new Gym(1L,"iraklis","meletiou",county);
        CustomerRequestDto requestCustomerDto = new CustomerRequestDto("nikos","meletiou","nikos@gmail.com","695582145",gym.getId());

        Customer requestCustomer = Customer.builder()
                .id(null)
                .gym(gym)
                .name(requestCustomerDto.getName())
                .address(requestCustomerDto.getAddress())
                .email(requestCustomerDto.getEmail())
                .phone(requestCustomerDto.getPhone()).build();

        Customer responseCustomer = new Customer(2L,"nikos","meletiou","nikos@gmail.com","695582145",gym);

        when(gymRepository.findById(requestCustomerDto.getGymId())).thenReturn(Optional.of(gym));
        when(customerRepository.save(any())).thenReturn(responseCustomer);


        Customer response = customerService.insertCustomer(requestCustomerDto);
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getName()).isEqualTo("nikos");

    }
    @Test
    void updateCustomer() {
        County county = new County(2L, "ioannina");
        Gym gym = new Gym(2L,"iraklis","meletiou",county);
        CustomerRequestDto requestCustomerDto = new CustomerRequestDto("nikos","meletiou","nikos@gmail.com","695582145",gym.getId());

        Customer requestCustomer = Customer.builder()
                .id(null)
                .gym(gym)
                .name(requestCustomerDto.getName())
                .address(requestCustomerDto.getAddress())
                .email(requestCustomerDto.getEmail())
                .phone(requestCustomerDto.getPhone()).build();

        Customer responseCustomer = new Customer(2L,"nikos","meletiou","nikos@gmail.com","695582145",requestCustomer.getGym());


        when(customerRepository.findById(2L)).thenReturn(Optional.of(responseCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(responseCustomer);

        Customer response = customerService.updateCustomer(requestCustomerDto,2L);
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getName()).isEqualTo("nikos");

    }

}
