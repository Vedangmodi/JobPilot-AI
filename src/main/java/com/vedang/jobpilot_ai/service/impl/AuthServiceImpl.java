package com.vedang.jobpilot_ai.service.impl;

import com.vedang.jobpilot_ai.config.JwtUtil;
import com.vedang.jobpilot_ai.dto.request.LoginRequest;
import com.vedang.jobpilot_ai.dto.request.SignupRequest;
import com.vedang.jobpilot_ai.dto.response.AuthResponse;
import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.entity.enums.Role;
import com.vedang.jobpilot_ai.exception.DuplicateResourceException;
import com.vedang.jobpilot_ai.exception.ResourceNotFoundException;
import com.vedang.jobpilot_ai.exception.UnauthorizedException;
import com.vedang.jobpilot_ai.repository.UserRepository;
import com.vedang.jobpilot_ai.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }

    @Override
    public AuthResponse signup(SignupRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new DuplicateResourceException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

//      Generate JWT token using an email
        String token = jwtUtil.generateToken(savedUser.getEmail());

        return new AuthResponse(token, savedUser.getName(), savedUser.getEmail(), "Signup successful");

    }

    @Override
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("No account found with this email"));

//        Check if the raw password matches the hashed one in DB

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new UnauthorizedException("Incorrect password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getName(), user.getEmail(), "Login successful");
    }

}
