package com.example.yousavebackend.DTOs.Blood;

import lombok.Data;

@Data
public class BloodTypeResponseDTO {
    private Long id;
    private String type;
    private int donationCount;
}
