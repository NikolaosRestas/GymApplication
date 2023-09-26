package com.example.demo4.controller;

import com.example.demo4.model.Staff;
import com.example.demo4.model.dto.StaffRequestDto;
import com.example.demo4.service.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/staffs")
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public List<Staff> getStaffs() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaff(@PathVariable("id") Long id) {
        return new ResponseEntity<>(staffService.findStaffById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Staff> addStaff(@RequestBody StaffRequestDto staff) {
        final Staff createdStaff = staffService.insertStaff(staff);
        return new ResponseEntity<>(createdStaff, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStaff(@PathVariable("id") long id) {
        staffService.deleteStaffById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@RequestBody StaffRequestDto staff,@PathVariable("id") long id) {
        Staff updatedStaff = staffService.updateStaff(staff,id);
        return new ResponseEntity<>(updatedStaff, HttpStatus.OK);
    }

}