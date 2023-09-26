package com.example.demo4.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

    private String name;
    private String address;
    private String email;
    private String phone;
    private Long gymId;
}
