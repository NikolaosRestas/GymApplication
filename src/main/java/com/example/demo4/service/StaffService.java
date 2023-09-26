package com.example.demo4.service;

import com.example.demo4.model.Gym;
import com.example.demo4.model.Staff;
import com.example.demo4.model.dto.StaffRequestDto;
import com.example.demo4.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final StaffRepository staffRepository;
    private final GymService gymService;

    public StaffService(StaffRepository staffRepository,GymService gymService) {
        this.staffRepository = staffRepository;
        this.gymService = gymService;
    }

    public List<Staff> getAllStaff() {
        return this.staffRepository.findAll();
    }

    public Staff findStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find staff with id: %s", id)));
    }

    public boolean deleteStaffById(Long id) {
        final int deletedCount = staffRepository.deleteStaffById(id);
        if (deletedCount <= 0) {
            throw new RuntimeException(String.format("Cannot find staff with id: %s", id));
        }
        return true;
    }

    public Staff insertStaff(StaffRequestDto staffRequestDto) {
        final Gym gym = gymService.findGymById(staffRequestDto.getGymId());
        final Staff staff = Staff.builder()
                .id(null)
                .name(staffRequestDto.getName())
                .specialty(staffRequestDto.getSpecialty())
                .phone(staffRequestDto.getPhone())
                .gender(staffRequestDto.getGender())
                .gym(gym).build();
        return staffRepository.save(staff);
    }

    public Staff updateStaff(StaffRequestDto staffRequestDto,long id) {
        final Staff savedStaff = findStaffById(id);
        savedStaff.setName(staffRequestDto.getName());
        savedStaff.setSpecialty(staffRequestDto.getSpecialty());
        savedStaff.setPhone(staffRequestDto.getPhone());
        savedStaff.setGender(staffRequestDto.getGender());
        final Gym gym = gymService.findGymById(staffRequestDto.getGymId());
        return staffRepository.save(savedStaff);
    }
}
