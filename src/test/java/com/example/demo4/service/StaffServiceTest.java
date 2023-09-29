package com.example.demo4.service;

import com.example.demo4.model.County;
import com.example.demo4.model.Gym;
import com.example.demo4.model.Staff;
import com.example.demo4.model.dto.GymRequestDto;
import com.example.demo4.model.dto.StaffRequestDto;
import com.example.demo4.repository.CountyRepository;
import com.example.demo4.repository.GymRepository;
import com.example.demo4.repository.StaffRepository;
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
class StaffServiceTest {

    @InjectMocks
    StaffService staffService;
    CountyService countyService;
    GymService gymService;
    @Mock
    StaffRepository staffRepository;
    @Mock
    GymRepository gymRepository;

    @BeforeEach
    void setUp() {
        gymService = new GymService(gymRepository,countyService);
        staffService = new StaffService(staffRepository,gymService);
    }

    @Test
    void getAllStaff() {
        List<Staff> staffs = new ArrayList<Staff>();

        County county  = new County(2L,"ioannina");
        Gym gym = new Gym(2L,"iraklis","meletiou",county);
        Staff requestStaff = new Staff(1L,"nikos","weights","965582148","male",gym);

        when(staffRepository.findAll()).thenReturn(staffs);

        staffs.add(requestStaff);

        List<Staff> response = staffService.getAllStaff();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("nikos");

    }

    @Test
    void findStaffById() {
        County county = new County(2L,"ioannina");
        Gym gym = new Gym(2L,"iraklis","meletiou",county);
        Staff responseStaff = new Staff(1L,"nikos","weights","965582148","male",gym);
        when(staffRepository.findById(2L)).thenReturn(Optional.of(responseStaff));

        Staff response = staffService.findStaffById(2L);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("nikos");
    }

    @Test
    void deleteCustomerById() {
        Long requestStaffId = 2L;

        when(staffRepository.deleteStaffById(requestStaffId)).thenReturn(Math.toIntExact(requestStaffId));

        Boolean response = staffService.deleteStaffById(requestStaffId);

        assertThat(response).isEqualTo(true);
    }

    @Test
    void insertStaff() {
        County county = new County(2L, "ioannina");
        Gym gym = new Gym(2L,"iraklis","meletiou",county);

        StaffRequestDto requestStaffDto = new StaffRequestDto("nikos","personal","695582145","male",gym.getId());

        Staff requestStaff = Staff.builder()
                .id(null)
                .name(requestStaffDto.getName())
                .specialty(requestStaffDto.getSpecialty())
                .phone(requestStaffDto.getPhone())
                .gender(requestStaffDto.getGender())
                .gym(gym).build();

        Staff responseStaff = new Staff(2L, requestStaff.getName(), requestStaff.getSpecialty(), requestStaff.getGender(),requestStaff.getGym());

        when(gymRepository.findById(requestStaffDto.getGymId())).thenReturn(Optional.of(gym));
        when(staffRepository.save(any())).thenReturn(responseStaff);


        Staff response = staffService.insertStaff(requestStaffDto);
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getGender()).isEqualTo("male");

    }

    @Test
    void updateStaff() {
        County county = new County(2L, "ioannina");
        Gym gym = new Gym(2L,"iraklis","meletiou",county);
        StaffRequestDto requestStaffDto = new StaffRequestDto("nikos","personal","695582145","male",gym.getId());

        Staff requestStaff = Staff.builder()
                .id(null)
                .name(requestStaffDto.getName())
                .specialty(requestStaffDto.getSpecialty())
                .phone(requestStaffDto.getPhone())
                .gender(requestStaffDto.getGender())
                .gym(gym).build();

        Staff responseStaff = new Staff(2L, requestStaff.getName(), requestStaff.getSpecialty(), requestStaff.getGender(),requestStaff.getGym());


        when(staffRepository.findById(2L)).thenReturn(Optional.of(responseStaff));
        when(gymRepository.findById(requestStaffDto.getGymId())).thenReturn(Optional.of(gym));
        when(staffRepository.save(any(Staff.class))).thenReturn(responseStaff);

        Staff response = staffService.updateStaff(requestStaffDto,2L);
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getName()).isEqualTo("nikos");

    }

}
