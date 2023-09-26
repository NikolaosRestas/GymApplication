package com.example.demo4.service;

import com.example.demo4.model.County;
import com.example.demo4.model.Gym;
import com.example.demo4.model.dto.GymRequestDto;
import com.example.demo4.repository.GymRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymService {
    private final GymRepository gymRepository;
    private final CountyService countyService;

    public GymService(GymRepository gymRepository, CountyService countyService) {
        this.gymRepository = gymRepository;
        this.countyService = countyService;
    }

    public List<Gym> getAllGyms() {
        return this.gymRepository.findAll();
    }

    public Gym findGymById(Long id) {
        return gymRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find gym with id: %s", id)));
    }

    public boolean deleteGymById(Long id) {
        final int deletedCount = gymRepository.deleteGymById(id);
        if (deletedCount <= 0) {
            throw new RuntimeException(String.format("Cannot find gym with id: %s", id));
        }
        return true;
    }

    public Gym insertGym(GymRequestDto gymRequestDto) {
        final County county = countyService.findCountyById(gymRequestDto.getCountyId());
        final Gym gym = Gym.builder()
                .id(null)
                .name(gymRequestDto.getName())
                .address(gymRequestDto.getAddress())
                .county(county).build();
        return gymRepository.save(gym);
    }

    public Gym updateGym(GymRequestDto gymRequestDto, long id) {
        final Gym savedGym = findGymById(id);
        savedGym.setName(gymRequestDto.getName());
        savedGym.setAddress(gymRequestDto.getAddress());
        final County county = countyService.findCountyById(gymRequestDto.getCountyId());
        savedGym.setCounty(county);
        return gymRepository.save(savedGym);
    }

    public List<Gym> search(Long countyId) {
        return gymRepository.findAllByCounty_Id(countyId);
    }
}