package com.compassuol.smart_parking_api.web.controllers;

import com.compassuol.smart_parking_api.entities.User;
import com.compassuol.smart_parking_api.services.UserService;
import com.compassuol.smart_parking_api.web.dto.UserCreateDto;
import com.compassuol.smart_parking_api.web.dto.UserPasswordDto;
import com.compassuol.smart_parking_api.web.dto.UserResponseDto;
import com.compassuol.smart_parking_api.web.dto.mapper.UserMapper;
import com.compassuol.smart_parking_api.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Contains all operations related to resources for creating, editing and reading a user.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create new user", description = "Resources for creating new user",
        responses = {
            @ApiResponse(responseCode = "201", description = "Resources created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "User e-mail already registered",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Appeal not processed due to invalid data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto createDto) {
        User newUser = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(newUser));
    }

    @Operation(summary = "Retrieve all users", description = "Resources for retrieve all users",
        responses = {
            @ApiResponse(responseCode = "200", description = "Users retrieve successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)))
        }
    )
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

    @Operation(summary = "Retrieve user by id", description = "Retrieve user by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resources retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resources not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return  ResponseEntity.ok(UserMapper.toDto(user));
    }

    @Operation(summary = "Update password", description = "Update password",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Password not check",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid camps",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto passwordDto) {
        User upUser = userService.editPassword(id, passwordDto.getCurrentPassword(), passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
        return  ResponseEntity.noContent().build();
    }
}
