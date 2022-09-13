package com.vanguard.elasticcache.poc.service;

import com.vanguard.elasticcache.poc.UserRepository;
import com.vanguard.elasticcache.poc.domain.User;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "getUserById", key = "{#id}", cacheManager = "cacheManager10Seconds")
    public User getUserById(Long id) {
        User user = Hibernate.unproxy(userRepository.getById(id), User.class);
        return user;
    }

    @CacheEvict(value = "getUserById", key = "#id")
    public void resetGetUserByIdResult(long id) {
        // Intentionally blank
    }
}
