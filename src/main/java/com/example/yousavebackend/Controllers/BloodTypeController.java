package com.example.yousavebackend.Controllers;

import com.example.yousavebackend.DTOs.Blood.*;
import com.example.yousavebackend.services.IBloodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blood-types")
public class BloodTypeController {

    @Autowired
    @Qualifier("bloodTypeServiceImpl")
    private IBloodTypeService bloodTypeService;

    @GetMapping
    public List<BloodTypeResponseDTO> getAllBloodTypes() {
        return bloodTypeService.getAllBloodTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodTypeResponseDTO> getBloodTypeById(@PathVariable Long id) {
        return bloodTypeService.getBloodTypeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BloodTypeResponseDTO> createBloodType(@RequestBody BloodTypeRequestDTO bloodTypeRequestDTO) {
        BloodTypeResponseDTO newBloodType = bloodTypeService.saveBloodType(bloodTypeRequestDTO);
        return ResponseEntity.ok(newBloodType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodTypeResponseDTO> updateBloodType(@PathVariable Long id, @RequestBody BloodTypeRequestDTO bloodTypeRequestDTO) {
        try {
            BloodTypeResponseDTO updatedBloodType = bloodTypeService.updateBloodType(id, bloodTypeRequestDTO);
            return ResponseEntity.ok(updatedBloodType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodType(@PathVariable Long id) {
        try {
            bloodTypeService.deleteBloodType(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user-counts")
    public Map<String, Integer> getUserCountsByBloodType() {
        return bloodTypeService.getUserCountsByBloodType();
    }
    
}