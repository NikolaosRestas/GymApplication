package com.example.demo4.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequestDto {

    private String name;
    private String specialty;
    private String phone;
    private String gender;
    private Long gymId;
}
