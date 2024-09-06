package org.example.tablereservation.service;

import org.example.tablereservation.model.entity.UserEntity;
import org.example.tablereservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public  class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(id) // ID로 사용자 찾음
                .orElseThrow(() -> new UsernameNotFoundException("User not found!!!"));

        return User.builder()
                .username(userEntity.getId())
                .password(userEntity.getPassword())
                .roles("USER")
                .build();
    }
}
