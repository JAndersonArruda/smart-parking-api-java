package com.compassuol.smart_parking_api.web.controllers;

import com.compassuol.smart_parking_api.entities.User;
import com.compassuol.smart_parking_api.services.UserService;
import com.compassuol.smart_parking_api.web.dto.UserCreateDto;
import com.compassuol.smart_parking_api.web.dto.UserPasswordDto;
import com.compassuol.smart_parking_api.web.dto.UserResponseDto;
import com.compassuol.smart_parking_api.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto createDto) {
        User newUser = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(newUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return  ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto passwordDto) {
        User upUser = userService.editPassword(id, passwordDto.getCurrentPassword(), passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
        return  ResponseEntity.noContent().build();
    }
}
