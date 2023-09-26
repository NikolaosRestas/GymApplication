package com.example.demo4.controller;

import com.example.demo4.model.*;
import com.example.demo4.model.dto.ProgramRequestDto;
import com.example.demo4.service.GymProgramService;
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
public class GymProgramControllerTest {


    @InjectMocks
    GymProgramController programController;
    @Mock
    GymProgramService programService;

    @Test
    public void testGetPrograms() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


        List<Customer> customers = new ArrayList<Customer>();
        List<Program>programs = new ArrayList<Program>();

        programs.add(new Program(1L, "Weights", "2Hours", "15$", customers));

        when(programService.getAllPrograms()).thenReturn(programs);

        List<Program> response = programController.getPrograms();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1L);
        assertThat(response.get(0).getKind()).isEqualTo("Weights");
    }

    @Test
    public void testGetProgram() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


        Long requestProgramId=2L;
        ArrayList<Customer>customers = new ArrayList<Customer>();
        Program responseProgram = new Program(2L,"Weights","2Hours","20$",customers);

        when(programService.findProgramById(requestProgramId)).thenReturn(responseProgram);

        ResponseEntity<Program> response = programController.getProgram(requestProgramId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getKind()).isEqualTo("Weights");
    }

    @Test
    public void testAddProgram() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/add");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


        ArrayList<Customer>customers = new ArrayList<Customer>();
        ArrayList<Long>customerIds = new ArrayList<Long>();
        ProgramRequestDto requestProgram = new ProgramRequestDto("Weights","2Hours","20$",customerIds);
        Program responseProgram = new Program(2L,"Weights","2Hours","15$",customers);

        when(programService.insertProgram(requestProgram)).thenReturn(responseProgram);

        ResponseEntity<Program> response = programController.addProgram(requestProgram);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getKind()).isEqualTo("Weights");
    }

    @Test
    public void testDeleteProgram() {
        MockHttpServletRequest request = new MockHttpServletRequest("DELETE", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long requestProgramId=2L;

        when(programService.deleteProgramById(requestProgramId)).thenReturn(true);

        ResponseEntity<Program> response = programController.deleteProgram(requestProgramId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdateProgram() {
        MockHttpServletRequest request = new MockHttpServletRequest("PUT", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


        ArrayList<Customer>customers = new ArrayList<Customer>();
        ArrayList<Long>customerIds = new ArrayList<Long>();
        ProgramRequestDto requestProgram = new ProgramRequestDto("Weights","2Hours","20$",customerIds);
        Program responseProgram = new Program(2L,"Weights","2Hours","15$",customers);
        Long programRequestId=2L;

        when(programService.updateProgram(requestProgram,programRequestId)).thenReturn(responseProgram);

        ResponseEntity<Program> response = programController.updateProgram(requestProgram,programRequestId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getKind()).isEqualTo("Weights");
    }


}