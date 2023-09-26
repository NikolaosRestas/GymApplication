package com.example.demo4.service;

import com.example.demo4.model.County;
import com.example.demo4.repository.CountyRepository;
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
class CountyServiceTest {

    @InjectMocks
    CountyService countyService;

    @Mock
    CountyRepository countyRepository;

    @Test
    void getAllCounties() {
        County requestCounty = new County(4L, "ioannina");
        List<County> counties = new ArrayList<County>();

        when(countyRepository.findAll()).thenReturn(counties);

        counties.add(requestCounty);

        List<County> response = countyService.getAllCounties();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getName()).isEqualTo("ioannina");

    }
    @Test
    void findCountyById() {
        County responseCounty = new County(4L, "ioannina!");
        when(countyRepository.findById(4L)).thenReturn(Optional.of(responseCounty));

        County response = countyService.findCountyById(4L);
        assertThat(response.getId()).isEqualTo(4L);
        assertThat(response.getName()).isEqualTo("ioannina!");
    }

    @Test
    void insertCounty() {
        County requestCounty = new County(4L, "ioannina!");
        County responseCounty = new County(4L, "ioannina!");

        when(countyRepository.save(requestCounty)).thenReturn(responseCounty);

        County response = countyService.insertCounty(requestCounty);
        assertThat(response.getId()).isEqualTo(4L);
        assertThat(response.getName()).isEqualTo("ioannina!");
    }

    @Test
    void deleteCountyById() {
        Long requestCountyId=4L;

        when(countyRepository.deleteCountyById(requestCountyId)).thenReturn(Math.toIntExact(requestCountyId));

        Boolean response = countyService.deleteCountyById(requestCountyId);
        assertThat(response).isEqualTo(true);
    }

    @Test
    void updateCounty() {
        County requestCounty = new County(4L, "ioannina!");
        County responseCounty = new County(4L, "Ioannina!");

        when(countyRepository.save(requestCounty)).thenReturn(responseCounty);

        List<County>counties = new ArrayList<County>();
        counties.add(requestCounty);

        County response = countyService.insertCounty(requestCounty);


        assertThat(response.getId()).isNotNull();
        assertThat(response.getId()).isEqualTo(4L);
        assertThat(response.getName()).isEqualTo("Ioannina!");
    }
}