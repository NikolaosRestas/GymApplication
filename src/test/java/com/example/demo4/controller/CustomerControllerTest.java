package com.example.demo4.controller;

import com.example.demo4.model.County;
import com.example.demo4.model.Customer;
import com.example.demo4.model.Gym;
import com.example.demo4.model.dto.CustomerRequestDto;
import com.example.demo4.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {


    @InjectMocks
    CustomerController customerController;
    @Mock
    CustomerService customerService;

    @Test
    public void testGetCustomers() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(1L, "Iraklis", "Meletiou", county);

        List<Customer> customers = new ArrayList<Customer>();

        customers.add(new Customer(1L, "Nikos", "Meletiou", "nikos@gmail.com", "6955214785", gym));

        when(customerService.getAllCustomers()).thenReturn(customers);

        List<Customer> response = customerController.getCustomers();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("Nikos");
    }

    @Test
    public void testGetCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(1L, "Iraklis", "Meletiou", county);

        Long requestCustomerId=2L;

        Customer responseCustomer = new Customer(2L, "Nikos", "Meletiou", "nikos@gmail.com", "6955214785", gym);


        when(customerService.findCustomerById(requestCustomerId)).thenReturn(responseCustomer);

        ResponseEntity<Customer> response = customerController.getCustomer(requestCustomerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
    }

    @Test
    public void testAddCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/add");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(2L, "Iraklis", "Meletiou", county);
        CustomerRequestDto requestCustomer = new CustomerRequestDto("Nikos", "Meletiou", "nikos@gmail.com", "6955214785", 1L);
        Customer responseCustomer = new Customer(2L, "Nikos", "Meletiou", "nikos@gmail.com", "6955214785", gym);


        when(customerService.insertCustomer(requestCustomer)).thenReturn(responseCustomer);

        ResponseEntity<Customer> response = customerController.addCustomer(requestCustomer);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getGym().getId()).isEqualTo(2L);
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
    }


    @Test
    public void testDeleteCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest("DELETE", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(2L, "Iraklis", "Meletiou", county);

        Long requestCustomerId = 2L;
        Customer responseCustomer = new Customer(2L, "Nikos", "Meletiou", "nikos@gmail.com", "6955214785", gym);


        when(customerService.deleteCustomerById(requestCustomerId)).thenReturn(true);

        ResponseEntity<Customer> response = customerController.deleteCustomer(requestCustomerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdateCustomer() {
        MockHttpServletRequest request = new MockHttpServletRequest("PUT", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(2L, "Iraklis", "Meletiou", county);

        CustomerRequestDto requestCustomer = new CustomerRequestDto("Nikos", "Meletiou", "nikos@gmail.com", "6955214785", 2L);
        Customer responseCustomer = new Customer(3L, "Nikos", "Meletiou", "nikos@gmail.com", "6955214785", gym);
        Long requestCustomerId = 3L;


        when(customerService.updateCustomer(requestCustomer,requestCustomerId)).thenReturn(responseCustomer);

        ResponseEntity<Customer> response = customerController.updateCustomer(requestCustomer,requestCustomerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(3L);
        assertThat(response.getBody().getName()).isEqualTo("Nikos");
    }


}

