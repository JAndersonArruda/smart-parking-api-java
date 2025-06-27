package com.compassuol.smart_parking_api.services;

import com.compassuol.smart_parking_api.entities.User;
import com.compassuol.smart_parking_api.exceptions.EntityNotFoundException;
import com.compassuol.smart_parking_api.exceptions.UsernameUniqueViolationException;
import com.compassuol.smart_parking_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameUniqueViolationException(String.format("User '%s' already registered.", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User id=%s not found.", id))
        );
    }

    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("New password not check confirm password.");
        }

        User user = findById(id);
        if (!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Your password doesn't match.");
        }

        user.setPassword(newPassword);
        return user;
    }
}
