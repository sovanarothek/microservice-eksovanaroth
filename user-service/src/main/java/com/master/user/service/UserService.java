package com.master.user.service;

import com.master.user.dto.UserRequest;
import com.master.user.dto.UserResponse;
import com.master.user.kafka.UserEventProducer;
import com.master.user.model.User;
import com.master.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserEventProducer eventProducer;

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .build();
        User saved = userRepository.save(user);
        eventProducer.sendUserCreatedEvent(saved.getId());
        return toResponse(saved);
    }

    @Cacheable(value = "users", key = "#id")
    public UserResponse getUserById(Long id) {
        log.info("Fetching user {} from DB", id);
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedAt())
            .build();
    }
}
