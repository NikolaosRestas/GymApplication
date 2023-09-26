package com.example.demo4.service;

import com.example.demo4.model.County;
import com.example.demo4.repository.CountyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountyService {
    private final CountyRepository countyRepository;

    public CountyService(CountyRepository countyRepository) {
        this.countyRepository = countyRepository;
    }

    public List<County> getAllCounties() {
        return this.countyRepository.findAll();
    }

    public County findCountyById(Long id) {
        return countyRepository.findById(id)
                .orElse(null);

//        return countyRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException(String.format("Cannot find county with id: %s", id)));
    }

    public boolean deleteCountyById(Long id) {
        final int deletedCount = countyRepository.deleteCountyById(id);
        if (deletedCount <= 0) {
            throw new RuntimeException(String.format("Cannot find county with id: %s", id));
        }
        return true;
    }

    public County insertCounty(County county) {
        county.setId(null);
        return countyRepository.save(county);
    }

    public County updateCounty(County county) {
        County savedCounty = findCountyById(county.getId());
        savedCounty.setName(county.getName());
        return countyRepository.save(savedCounty);
    }
}