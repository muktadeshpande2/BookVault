package com.example.Library_Management_System.service;

import com.example.Library_Management_System.Utils.AuthoritiesListProvider;
import com.example.Library_Management_System.Utils.Constants;
import com.example.Library_Management_System.model.User;
import com.example.Library_Management_System.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public User save(String userType, User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        String authorities = AuthoritiesListProvider.getAuthorities(userType);

        try{
            if(authorities.equals(Constants.INVALID_USER)) {
                return new User();
            }

            user.setPassword(encryptedPassword);
            user.setAuthorities(authorities);

            return userRepo.save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

    }
}
