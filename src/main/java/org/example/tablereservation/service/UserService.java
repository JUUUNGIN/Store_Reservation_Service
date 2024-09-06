package org.example.tablereservation.service;

import org.example.tablereservation.model.entity.UserEntity;
import org.example.tablereservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
// 회원가입
    public UserEntity create(String id, String userName, String password, String phoneNumber) {

//        중복 id 확인
        Optional<UserEntity> existingUserById = userRepository.findById(id);
        if (existingUserById.isPresent()) {
            throw new IllegalArgumentException("Id is already in use!!!!");
        }

//      새로운 유저 엔티티 생성 및 저장
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);

    }

}
