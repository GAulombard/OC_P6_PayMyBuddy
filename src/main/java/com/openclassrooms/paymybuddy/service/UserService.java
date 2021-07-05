package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getUsers() {
        LOGGER.info("Process to get all users");
        return userRepository.findAll();
    }

    public void saveUser(User user) throws UserAlreadyExistException {
        LOGGER.info("Processing to save a new user in database");
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User already exist in database");
        }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserRole("ROLE_USER");
            userRepository.save(user);
    }

    public Optional<User> findUserById(int id) {
        LOGGER.info("Processing to find a user by id");
        return userRepository.findById(id);
    }

    public void removeUserById(int id) {

        userRepository.deleteById(id);
    }
}
