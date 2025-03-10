package com.example.yousavebackend.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class RegisterRequestDTO {
   private String firstname;
   private String lastname;
   private String gender;
   private String email;
   private String password;
   private String phone;
   private LocalDate dateOfBirth;
   private Long cityId;
   private Long bloodTypeId;
//   private Set<String> roles;
}