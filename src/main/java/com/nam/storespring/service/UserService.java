package com.nam.storespring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nam.storespring.dto.request.UserCreationRequest;
import com.nam.storespring.dto.request.UserUpdateRequest;
import com.nam.storespring.dto.response.UserResponse;
import com.nam.storespring.entity.User;
import com.nam.storespring.enums.Role;
import com.nam.storespring.exception.AppException;
import com.nam.storespring.exception.ErrorCode;
import com.nam.storespring.mapper.UserMapper;
import com.nam.storespring.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private KafkaProducerService kafkaProducerService;

    public UserService(UserRepository userRepository, KafkaProducerService kafkaProducerService) {
        this.userRepository = userRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public UserResponse createUser(UserCreationRequest request) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if (userRepository.existsByUserName(request.getUserName()))
            throw new AppException(ErrorCode.USER_EXISTED);

        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER.name());

        User createdUser = userRepository.save(user);

        kafkaProducerService.sendLog("user-logs", "ADD", createdUser.getId(), createdUser);

        return userMapper.toUserResponse(createdUser);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!")));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if (userRepository.existsByUserName(request.getUserName()) && !user.getUserName().equals(request.getUserName()))
            throw new RuntimeException("Username already exists");

        userMapper.updateUser(user, request);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        kafkaProducerService.sendLog("user-logs", "UPDATE", id, updatedUser);

        return userMapper.toUserResponse(updatedUser);
    }

    public void deleteUser(String id) {
        User deletedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        userRepository.deleteById(id);
        kafkaProducerService.sendLog("user-logs", "DELETE", id, deletedUser);
    }
}
