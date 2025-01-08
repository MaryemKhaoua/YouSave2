package com.example.yousavebackend.services.implementations;

import com.example.yousavebackend.DTOs.RegisterRequestDTO;
import com.example.yousavebackend.entities.Role;
import com.example.yousavebackend.entities.User;
import com.example.yousavebackend.repositories.RoleRepository;
import com.example.yousavebackend.repositories.UserRepository;
import com.example.yousavebackend.services.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User register (RegisterRequestDTO registerRequestDTO ) throws Exception {
        User user = new User (  );
        if (registerRequestDTO.getFirstname () != null && !registerRequestDTO.getFirstname ().isEmpty ()) {
            if (registerRequestDTO.getLastname () != null && !registerRequestDTO.getLastname ().isEmpty ()) {
                if (registerRequestDTO.getEmail () != null && !registerRequestDTO.getEmail ().isEmpty ()) {
                    // TODO Test for optional email if exists or not
                    if ( registerRequestDTO.getPassword () != null
                            && !registerRequestDTO.getPassword ().isEmpty ()) {
                        try {
                            user.setFirstname ( registerRequestDTO.getFirstname () );
                            user.setLastname ( registerRequestDTO.getLastname () );
                            user.setEmail ( registerRequestDTO.getEmail () );

                            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                            String encodedPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
                            user.setPassword ( encodedPassword );

                            user.setPhone ( registerRequestDTO.getPhone () );

                            Role role = getRoleByName ( "ROLE_USER" );
                            user.setRoles(role);
                            return userRepository.save ( user );
                        } catch (Exception e) {
                            throw new Exception(e.getMessage());
                        }
                    } else {
                        throw new Exception("Password null or empty");
                    }
                } else {
                    throw new Exception("Email null or empty");
                }
            } else {
                throw new Exception("Lastname null or empty");
            }
        } else {
            throw new Exception("Firstname null or empty");
        }
    }


    @Override
    public Role getRoleByName (String role_name ) {
        return this.roleRepository.getRoleByName ( role_name );
    }
}
