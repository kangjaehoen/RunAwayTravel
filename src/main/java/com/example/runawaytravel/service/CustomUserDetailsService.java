package com.example.runawaytravel.service;

import com.example.runawaytravel.dto.CustomUserDetails;
import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User data = userRepository.findByUsername(username);
        System.out.println(data+"userdetails 값");
        if (data != null) {
            return new CustomUserDetails(data); //userdetails 를 만들어서 userservice 에서 최종적으로 authentication manager에 넘겨줄거다
        }
        return null;
    }
}
