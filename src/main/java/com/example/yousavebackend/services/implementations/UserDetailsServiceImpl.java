package com.example.yousavebackend.services.implementations;

import com.example.yousavebackend.entities.User;
import com.example.yousavebackend.repositories.UserRepository;
import com.example.yousavebackend.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    public UserDetailsServiceImpl() {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        logger.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByEmail(email);
        if(user == null){
            logger.error("Username not found: " + email);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!" + user.toString());
        return new CustomUserDetails(user);
    }
}
