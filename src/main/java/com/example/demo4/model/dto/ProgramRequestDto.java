package com.example.demo4.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramRequestDto {

    private String kind;
    private String duration;
    private String price;
    private List<Long> customerIds;
}