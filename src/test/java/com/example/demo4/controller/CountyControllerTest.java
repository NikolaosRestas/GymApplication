package com.example.demo4.controller;


import com.example.demo4.model.County;
import com.example.demo4.service.CountyService;
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
public class CountyControllerTest {


    @InjectMocks
    CountyController countyController;

    @Mock
    CountyService countyService;

    @Test
    public void testGetCounties() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<County> counties = new ArrayList<>();
        counties.add(new County(1L, "Giannena"));

        when(countyService.getAllCounties()).thenReturn(counties);

        ResponseEntity<List<County>> response = countyController.getCounties();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Giannena");
    }


    @Test
    public void testGetCounty() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestCountyId = 1L;
        County responseCounty = new County(1L,"Igoumenitsa");


        when(countyService.findCountyById(requestCountyId)).thenReturn(responseCounty);

        ResponseEntity<County> response = countyController.getCounty(requestCountyId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Igoumenitsa");
    }


    @Test
    public void testAddCounty() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/add");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        County requestCounty = new County(null, "ioannina");
        County responseCounty = new County(54L, "ioannina");


        when(countyService.insertCounty(requestCounty)).thenReturn(responseCounty);

        ResponseEntity<County> response = countyController.addCounty(requestCounty);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(54L);
        assertThat(response.getBody().getName()).isEqualTo("ioannina");
    }

    @Test
    public void testDeleteCounty() {
        MockHttpServletRequest request = new MockHttpServletRequest("DELETE", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County requestCounty = new County(1L,"Igoumenitsa");


        when(countyService.deleteCountyById(requestCounty.getId())).thenReturn(true);

        ResponseEntity<County> response = countyController.deleteCounty(requestCounty.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdateCounty() {
        MockHttpServletRequest request = new MockHttpServletRequest("PUT", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County requestCounty = new County(2L,"Kavala");
        County responseCounty = new County(2L,"Kavala");


        when(countyService.updateCounty(requestCounty)).thenReturn(responseCounty);

        ResponseEntity<County> response = countyController.updateCounty(requestCounty);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getName()).isEqualTo("Kavala");
    }

}