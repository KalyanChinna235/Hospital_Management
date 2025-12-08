package com.auth.service;

import com.auth.model.User;
import com.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
      // Optional<User> user = userRepository.findByEmail(email);
        return userRepository.findByEmail(email);

    }
}
