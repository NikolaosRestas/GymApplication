package com.example.demo4.service;

import com.example.demo4.model.*;
import com.example.demo4.model.dto.GymRequestDto;
import com.example.demo4.model.dto.ProgramRequestDto;
import com.example.demo4.repository.CustomerRepository;
import com.example.demo4.repository.ProgramRepository;
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
class GymProgramServiceTest {

    @InjectMocks
    GymProgramService programService;
    @Mock
    GymService gymService;
    CustomerService customerService;
    @Mock
    ProgramRepository programRepository;
    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository,gymService);
        programService = new GymProgramService(programRepository, customerService);
    }

    @Test
    void getAllPrograms() {
        List<Program> programs = new ArrayList<Program>();
        List<Customer>customers = new ArrayList<Customer>();

        Program requestProgram = new Program(1L,"weights","2hours","15$",customers);

        when(programRepository.findAll()).thenReturn(programs);

        programs.add(requestProgram);

        List<Program> response = programService.getAllPrograms();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1L);
        assertThat(response.get(0).getKind()).isEqualTo("weights");

    }

    @Test
    void findProgramById() {
       List<Customer>customers = new ArrayList<Customer>();

        Program responseProgram = new Program(1L,"weights","2hours","15$",customers);
        when(programRepository.findById(1L)).thenReturn(Optional.of(responseProgram));

        Program response = programService.findProgramById(1L);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getKind()).isEqualTo("weights");
    }

    @Test
    void deleteProgramById() {
        Long requestProgramId = 2L;

        when(programRepository.deleteProgramById(requestProgramId)).thenReturn(Math.toIntExact(requestProgramId));

        Boolean response = programService.deleteProgramById(requestProgramId);

        assertThat(response).isEqualTo(true);
    }

    @Test
    void insertProgram() {
        County county = new County(1L, "ioannina");
        Gym gym = new Gym(2L,"iraklis","meletiou",county);
        List<Long>customersIds = new ArrayList<Long>();
        List<Customer>customers = new ArrayList<Customer>();

        ProgramRequestDto requestProgramDto = new ProgramRequestDto("weights","2hours","20$",customersIds);

        Program requestProgram = Program.builder()
                .id(null)
                .kind(requestProgramDto.getKind())
                .duration(requestProgramDto.getDuration())
                .price(requestProgramDto.getPrice())
                .customers(customerService.findCustomersByIds(requestProgramDto.getCustomerIds()))
                .build();

        Program responseProgram = new Program(2L, requestProgram.getKind(),requestProgram.getDuration(),requestProgram.getPrice(),requestProgram.getCustomers());

        when(customerRepository.findAllById(requestProgramDto.getCustomerIds())).thenReturn(customers);
        when(programRepository.save(any())).thenReturn(responseProgram);


        Program response = programService.insertProgram(requestProgramDto);
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getKind()).isEqualTo("weights");
    }

    @Test
    void updateProgram() {
        County county = new County(1L, "ioannina");
        Gym gym = new Gym(2L,"iraklis","meletiou",county);

        List <Customer>customers = new ArrayList<Customer>();
        List<Long>customersIds = new ArrayList<Long>();

        ProgramRequestDto requestProgramDto = new ProgramRequestDto("weights","2hours","20$",customersIds);

        Program requestProgram = Program.builder()
                .id(null)
                .kind(requestProgramDto.getKind())
                .duration(requestProgramDto.getDuration())
                .price(requestProgramDto.getPrice())
                .customers(customerService.findCustomersByIds(requestProgramDto.getCustomerIds()))
                .build();

        Program responseProgram = new Program(2L, requestProgram.getKind(),requestProgram.getDuration(),requestProgram.getPrice(),requestProgram.getCustomers());


        when(programRepository.findById(2L)).thenReturn(Optional.of(responseProgram));
        when(customerRepository.findAllById(requestProgramDto.getCustomerIds())).thenReturn(customers);
        when(programRepository.save(any(Program.class))).thenReturn(responseProgram);

        Program response = programService.updateProgram(requestProgramDto,2L);
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getKind()).isEqualTo("weights");

    }

}
