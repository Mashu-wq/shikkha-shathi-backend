package com.runtimerebels.authservice.services;

import com.runtimerebels.authservice.dtos.UserRegistrationResponse;
import com.runtimerebels.authservice.exceptions.UserAlreadyExistsException;
import com.runtimerebels.authservice.mappers.UserMapper;
import com.runtimerebels.authservice.models.User;
import com.runtimerebels.authservice.repositories.UserRepository;
import com.runtimerebels.authservice.security.JwtTokenProvider;
import com.shikkhasathi.dtos.UserRegistrationRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Transactional
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByUsernameOrEmail(request.getUsername(), request.getEmail())) {
            throw new UserAlreadyExistsException("Username or email already taken");
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of("STUDENT"));

        User savedUser = userRepository.save(user);
        String token = jwtTokenProvider.generateToken(savedUser.getUsername());

        return userMapper.toUserRegistrationResponse(savedUser, token);
    }
}
