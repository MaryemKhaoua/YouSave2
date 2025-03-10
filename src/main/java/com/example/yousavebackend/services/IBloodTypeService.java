package com.example.yousavebackend.services;

import com.example.yousavebackend.DTOs.Blood.BloodTypeRequestDTO;
import com.example.yousavebackend.DTOs.Blood.BloodTypeResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface IBloodTypeService {
    List<BloodTypeResponseDTO> getAllBloodTypes();
    Optional<BloodTypeResponseDTO> getBloodTypeById(Long id);
    BloodTypeResponseDTO saveBloodType(BloodTypeRequestDTO bloodTypeRequestDTO);
    BloodTypeResponseDTO updateBloodType(Long id, BloodTypeRequestDTO bloodTypeRequestDTO);
    void deleteBloodType(Long id);
    Map<String, Integer> getUserCountsByBloodType();
}