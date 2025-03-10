package com.example.yousavebackend.services;

import com.example.yousavebackend.DTOs.RegisterRequestDTO;
import com.example.yousavebackend.DTOs.User.UserRequestDTO;
import com.example.yousavebackend.DTOs.User.UserResponseDTO;
import com.example.yousavebackend.entities.Role;
import com.example.yousavebackend.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    UserResponseDTO register(RegisterRequestDTO registerRequestDTO) throws Exception;
    Role getRoleByName(String role_name);
    List<UserResponseDTO> getAllUsers();
    Optional<UserResponseDTO> getUserById(Long id);
    UserResponseDTO saveUser(UserRequestDTO userRequestDTO) throws Exception;
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) throws Exception;
    void deleteUser(Long id);
    long countAllUsers();
    Optional<User> findByEmail(String email);
}