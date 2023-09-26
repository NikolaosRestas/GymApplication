package com.example.demo4.controller;

import com.example.demo4.model.County;
import com.example.demo4.model.Gym;
import com.example.demo4.model.Staff;
import com.example.demo4.model.dto.StaffRequestDto;
import com.example.demo4.service.StaffService;
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
public class StaffControllerTest {


    @InjectMocks
    StaffController staffController;
    @Mock
    StaffService staffService;

    @Test
    public void testGetCustomers() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(1L, "Iraklis", "Meletiou", county);

        List<Staff> staffs = new ArrayList<Staff>();

        staffs.add(new Staff(1L, "NikosRaptis", "Personal trainer", "965582145", "Male", gym));

        when(staffService.getAllStaff()).thenReturn(staffs);

        List<Staff> response = staffController.getStaffs();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1L);
        assertThat(response.get(0).getName()).isEqualTo("NikosRaptis");
    }

    @Test
    public void testGetStaff() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(1L, "Iraklis", "Meletiou", county);

        Long requestStaffId=2L;
        Staff responseStaff = new Staff(2L, "NikosRaptis", "Personal trainer", "965582145", "Male", gym);

        when(staffService.findStaffById(requestStaffId)).thenReturn(responseStaff);

        ResponseEntity<Staff> response = staffController.getStaff(requestStaffId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getName()).isEqualTo("NikosRaptis");
    }

    @Test
    public void testAddStaff() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/add");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(1L, "Iraklis", "Meletiou", county);

        StaffRequestDto requestStaff = new StaffRequestDto("NikosRaptis","Personal Trainer","9655821475","Male",1L);
        Staff responseStaff = new Staff(2L, "NikosRaptis", "Personal trainer", "965582145", "Male", gym);

        when(staffService.insertStaff(requestStaff)).thenReturn(responseStaff);

        ResponseEntity<Staff> response = staffController.addStaff(requestStaff);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getName()).isEqualTo("NikosRaptis");
    }

    @Test
    public void testDeleteStaff() {
        MockHttpServletRequest request = new MockHttpServletRequest("DELETE", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(1L, "Iraklis", "Meletiou", county);

        Long requestStaffId = 2L;
        when(staffService.deleteStaffById(requestStaffId)).thenReturn(true);

        ResponseEntity<Staff> response = staffController.deleteStaff(requestStaffId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testUpdateStaff() {
        MockHttpServletRequest request = new MockHttpServletRequest("PUT", "/{id}");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        County county = new County(1L, "Igoumenitsa");
        Gym gym = new Gym(1L, "Iraklis", "Meletiou", county);

        Long requestStaffId = 2L;
        StaffRequestDto requestStaff = new StaffRequestDto("NikosRaptis","Personal Trainer","9655821475","Male",1L);
        Staff responseStaff = new Staff(2L,"NikosRaptis","Personal Trainer","9655821475","Male",gym);

        when(staffService.updateStaff(requestStaff,requestStaffId)).thenReturn(responseStaff);

        ResponseEntity<Staff> response = staffController.updateStaff(requestStaff,requestStaffId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(2L);
        assertThat(response.getBody().getName()).isEqualTo("NikosRaptis");
    }

}
