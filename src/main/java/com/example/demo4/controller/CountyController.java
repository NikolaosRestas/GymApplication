package com.example.demo4.controller;

import com.example.demo4.model.County;
import com.example.demo4.service.CountyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/counties")
public class CountyController {
    private final CountyService countyService;

    public CountyController(CountyService countyService) {
        this.countyService = countyService;
    }

    @GetMapping
    public ResponseEntity<List<County>> getCounties() {
        return new ResponseEntity<>(countyService.getAllCounties(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<County> getCounty(@PathVariable("id") Long id) {
        return new ResponseEntity<>(countyService.findCountyById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<County> addCounty(@RequestBody County county) {
        final County createdCounty = countyService.insertCounty(county);
        return new ResponseEntity<>(createdCounty, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCounty(@PathVariable("id") long id) {
        countyService.deleteCountyById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<County> updateCounty(@RequestBody County county) {
        County updatedCounty = countyService.updateCounty(county);
        return new ResponseEntity<>(updatedCounty, HttpStatus.OK);

    }

}