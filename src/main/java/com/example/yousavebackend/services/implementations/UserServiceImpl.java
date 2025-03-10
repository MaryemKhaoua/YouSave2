package com.example.yousavebackend.services.implementations;

import com.example.yousavebackend.DTOs.RegisterRequestDTO;
import com.example.yousavebackend.DTOs.User.UserRequestDTO;
import com.example.yousavebackend.DTOs.User.UserResponseDTO;
import com.example.yousavebackend.entities.*;
import com.example.yousavebackend.repositories.*;
import com.example.yousavebackend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) throws Exception {
        if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new Exception("User already exists with email " + registerRequestDTO.getEmail());
        }

        User user = new User();
        user.setFirstname(registerRequestDTO.getFirstname());
        user.setLastname(registerRequestDTO.getLastname());
        user.setGender(registerRequestDTO.getGender());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setPhone(registerRequestDTO.getPhone());
        user.setDateOfBirth(registerRequestDTO.getDateOfBirth());

        City city = cityRepository.findById(registerRequestDTO.getCityId())
                .orElseThrow(() -> new Exception("City not found with id " + registerRequestDTO.getCityId()));
        user.setCity(city);

        BloodType bloodType = bloodTypeRepository.findById(registerRequestDTO.getBloodTypeId())
                .orElseThrow(() -> new Exception("BloodType not found with id " + registerRequestDTO.getBloodTypeId()));

        bloodType.incrementUserCount();
        bloodTypeRepository.save(bloodType);

        user.setBloodType(bloodType);

        Role donorRole = roleRepository.findByName("DONOR")
                .orElseThrow(() -> new Exception("Role 'DONOR' not found in database"));
        user.setRoles(Set.of(donorRole));

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public Role getRoleByName(String role_name) {

        return roleRepository.findByName(role_name).orElse(null);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public long countAllUsers() {
        return userRepository.count();
    }

    @Override
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) throws Exception {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new Exception("User already exists with email " + userRequestDTO.getEmail());
        }

        User user = new User();
        user.setFirstname(userRequestDTO.getFirstname());
        user.setLastname(userRequestDTO.getLastname());
        user.setGender(userRequestDTO.getGender());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setPhone(userRequestDTO.getPhone());
        user.setDateOfBirth(userRequestDTO.getDateOfBirth());

        City city = cityRepository.findById(userRequestDTO.getCityId())
                .orElseThrow(() -> new Exception("City not found with id " + userRequestDTO.getCityId()));
        user.setCity(city);

        BloodType bloodType = bloodTypeRepository.findById(userRequestDTO.getBloodTypeId())
                .orElseThrow(() -> new Exception("BloodType not found with id " + userRequestDTO.getBloodTypeId()));
        user.setBloodType(bloodType);

        Set<Role> roles = new HashSet<>();
        for (String roleName : userRequestDTO.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new Exception("Role not found with name " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with id " + id));

        user.setFirstname(userRequestDTO.getFirstname());
        user.setLastname(userRequestDTO.getLastname());
        user.setGender(userRequestDTO.getGender());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setPhone(userRequestDTO.getPhone());
        user.setDateOfBirth(userRequestDTO.getDateOfBirth());

        City city = cityRepository.findById(userRequestDTO.getCityId())
                .orElseThrow(() -> new Exception("City not found with id " + userRequestDTO.getCityId()));
        user.setCity(city);

        BloodType bloodType = bloodTypeRepository.findById(userRequestDTO.getBloodTypeId())
                .orElseThrow(() -> new Exception("BloodType not found with id " + userRequestDTO.getBloodTypeId()));
        user.setBloodType(bloodType);

        Set<Role> roles = new HashSet<>();
        for (String roleName : userRequestDTO.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new Exception("Role not found with name " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private UserResponseDTO mapToDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstname(user.getFirstname());
        userResponseDTO.setLastname(user.getLastname());
        userResponseDTO.setGender(user.getGender());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setDateOfBirth(user.getDateOfBirth());
        userResponseDTO.setCityName(user.getCity().getName());
        userResponseDTO.setBloodTypeName(user.getBloodType().getType());
        userResponseDTO.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        return userResponseDTO;
    }
}