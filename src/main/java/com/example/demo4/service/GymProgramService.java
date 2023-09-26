package com.example.demo4.service;

import com.example.demo4.model.Program;
import com.example.demo4.model.dto.ProgramRequestDto;
import com.example.demo4.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymProgramService {
    private final ProgramRepository programRepository;
    private final CustomerService customerService;

    public GymProgramService(ProgramRepository programRepository, CustomerService customerService) {
        this.programRepository = programRepository;
        this.customerService = customerService;
    }

    public List<Program> getAllPrograms() {
        return this.programRepository.findAll();
    }

    public Program findProgramById(Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find program with id: %s", id)));
    }

    public boolean deleteProgramById(Long id) {
        final int deletedCount = programRepository.deleteProgramById(id);
        if (deletedCount <= 0) {
            throw new RuntimeException(String.format("Cannot find program with id: %s", id));
        }
        return true;
    }

    public Program insertProgram(ProgramRequestDto programRequestDto) {
        final Program program = Program.builder()
                .id(null)
                .kind(programRequestDto.getKind())
                .duration(programRequestDto.getDuration())
                .price(programRequestDto.getPrice())
                .customers(customerService.findCustomersByIds(programRequestDto.getCustomerIds()))
                .build();
        return programRepository.save(program);
    }

    public Program updateProgram(ProgramRequestDto programRequestDto, long id) {
        Program savedProgram = findProgramById(id);
        savedProgram.setKind(programRequestDto.getKind());
        savedProgram.setDuration(programRequestDto.getDuration());
        savedProgram.setPrice(programRequestDto.getPrice());
        savedProgram.setCustomers(customerService.findCustomersByIds(programRequestDto.getCustomerIds()));
        return programRepository.save(savedProgram);
    }
}