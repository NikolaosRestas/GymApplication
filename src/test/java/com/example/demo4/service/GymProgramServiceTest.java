package com.example.demo4.service;

import com.example.demo4.model.*;
import com.example.demo4.repository.ProgramRepository;
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
class GymProgramServiceTest {

    @InjectMocks
    GymProgramService programService;
    @Mock
    ProgramRepository programRepository;

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

}
