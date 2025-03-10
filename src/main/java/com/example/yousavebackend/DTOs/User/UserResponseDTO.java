package com.example.yousavebackend.DTOs.User;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class UserResponseDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String gender;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String cityName;
    private String bloodTypeName;
    private Set<String> roles;
}
