package com.example.demo4.controller;

import com.example.demo4.model.Program;
import com.example.demo4.model.dto.ProgramRequestDto;
import com.example.demo4.service.GymProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/programs")
public class GymProgramController {
    private final GymProgramService programService;

    public GymProgramController(GymProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public List<Program> getPrograms() {
        return programService.getAllPrograms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable("id") Long id) {
        return new ResponseEntity<>(programService.findProgramById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Program> addProgram(@RequestBody ProgramRequestDto program) {
        final Program createdProgram = programService.insertProgram(program);
        return new ResponseEntity<>(createdProgram, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProgram(@PathVariable("id") long id) {
        programService.deleteProgramById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@RequestBody ProgramRequestDto program, @PathVariable("id") long id) {
        Program updatedProgram = programService.updateProgram(program, id);
        return new ResponseEntity<>(updatedProgram, HttpStatus.OK);

    }

}
