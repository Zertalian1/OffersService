package com.example.controller;

import com.example.domain.dto.RegistrationDto;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Void> createNewUser(@RequestBody RegistrationDto registrationDto) {
        userService.registrationUser(registrationDto);
        return ResponseEntity.ok().build();
    }
}
