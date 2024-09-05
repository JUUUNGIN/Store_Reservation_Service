package org.example.tablereservation.controller;

import org.example.tablereservation.model.entity.UserEntity;
import org.example.tablereservation.model.dto.UserDto;
import org.example.tablereservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        try {
            UserEntity newUser = userService.create(userDto.getId(), userDto.getUserName(), userDto.getPassword(), userDto.getPhoneNumber());
            return ResponseEntity.ok("User registered successfully with Id : " + newUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
