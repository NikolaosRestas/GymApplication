package com.example.demo4.controller;


import com.example.demo4.model.County;
import com.example.demo4.model.Gym;
import com.example.demo4.model.dto.GymRequestDto;
import com.example.demo4.service.GymService;
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
public class GymControllerTest {


    @InjectMocks
    GymController gymController;

    @Mock
    GymService gymService;

    @Test
    public void testGetGyms() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L,"Igoumenitsa");
        List<Gym> gyms = new ArrayList<Gym>();

        gyms.add(new Gym(1L, "Iraklis","Meletiou",county));

        when(gymService.getAllGyms()).thenReturn(gyms);

        List<Gym> response = gymController.getGyms();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getName()).isEqualTo("Iraklis");
    }

    @Test
    public void testGetGym() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestGymId = 1L;
        County county = new County(1L,"Igoumenitsa");
        Gym responseGym = new Gym(2L, "Iraklis","Meletiou",county);


        when(gymService.findGymById(requestGymId)).thenReturn(responseGym);

        ResponseEntity<Gym> response = gymController.getGym(requestGymId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getName()).isEqualTo("Iraklis");
    }

    @Test
    public void testAddGym() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/add");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L,"Ioannina");

        GymRequestDto requestGym = new GymRequestDto("Iraklis","Meletiou",1L);
        Gym responseGym = new Gym(2L, "Iraklis","Meletiou",county);


        when(gymService.insertGym(requestGym)).thenReturn(responseGym);

        ResponseEntity<Gym> response = gymController.addGym(requestGym);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCounty().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("Iraklis");
    }

    @Test
    public void testDeleteGym() {
        MockHttpServletRequest request = new MockHttpServletRequest("DELETE", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L,"Igoumenitsa");
        Gym requestGym = new Gym(1L,"Iraklis","Meletiou",county);

        when(gymService.deleteGymById(requestGym.getId())).thenReturn(true);

        ResponseEntity<Gym> response = gymController.deleteGym(requestGym.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdateGym() {
        MockHttpServletRequest request = new MockHttpServletRequest("PUT", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L,"Igoumenitsa");
        GymRequestDto requestGym = new GymRequestDto("Iraklis","Meletiou",2L);
        Gym responseGym = new Gym(2L,"Ioannina","Meletiou",county);
        Long gymId = 2L;


        when(gymService.updateGym(requestGym,gymId)).thenReturn(responseGym);

        ResponseEntity<Gym> response = gymController.updateGym(requestGym,gymId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getName()).isEqualTo("Ioannina");
    }

}