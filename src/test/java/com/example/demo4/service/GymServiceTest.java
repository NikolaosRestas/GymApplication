package com.example.demo4.service;

import com.example.demo4.model.County;
import com.example.demo4.model.Gym;
import com.example.demo4.model.dto.GymRequestDto;
import com.example.demo4.repository.CountyRepository;
import com.example.demo4.repository.GymRepository;
import org.hamcrest.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GymServiceTest {


    GymService gymService;
    CountyService countyService;
    @Mock
    CountyRepository countyRepository;
    @Mock
    GymRepository gymRepository;

    @BeforeEach
    void setUp() {
        countyService = new CountyService(countyRepository);
        gymService = new GymService(gymRepository, countyService);
    }

    @Test
    void getAllGyms() {
        List<Gym> gyms = new ArrayList<Gym>();

        County county = new County(4L, "ioannina");
        Gym requestGym = new Gym(1L, "Iraklis", "Meletiou", county);


        when(gymRepository.findAll()).thenReturn(gyms);

        gyms.add(requestGym);

        List<Gym> response = gymService.getAllGyms();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("Iraklis");

    }

    @Test
    void findGymById() {
        County county = new County(2L, "ioannina");
        Gym responseGym = new Gym(2L, "iraklis", "meletiou", county);
        when(gymRepository.findById(2L)).thenReturn(Optional.of(responseGym));

        Gym response = gymService.findGymById(2L);
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getName()).isEqualTo("iraklis");
    }

    @Test
    void deleteGymById() {
        Long requestGymId = 2L;

        when(gymRepository.deleteGymById(requestGymId)).thenReturn(Math.toIntExact(requestGymId));

        Boolean response = gymService.deleteGymById(requestGymId);

        assertThat(response).isEqualTo(true);
    }

    @Test
    void insertGym() {
        County county = new County(1L, "ioannina");
        GymRequestDto requestGymDto = new GymRequestDto("iraklis", "Meletiou", county.getId());

        Gym requestGym = Gym.builder()
                .id(null)
                .name(requestGymDto.getName())
                .address(requestGymDto.getAddress())
                .county(county).build();

        Gym responseGym = new Gym(2L, requestGym.getName(), requestGym.getAddress(), requestGym.getCounty());
        when(countyRepository.findById(requestGymDto.getCountyId())).thenReturn(Optional.of(county));
        when(gymRepository.save(requestGym)).thenReturn(responseGym);
//        when(gymRepository.save(any())).thenReturn(responseGym);
//        when(gymRepository.save(any(Gym.class))).thenReturn(responseGym);

        //Starts here
        Gym response = gymService.insertGym(requestGymDto);
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getName()).isEqualTo("iraklis");

    }

}