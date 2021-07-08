package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

    public User getUserById(int id) {
        return userRepository.getById(id);
    }

    public void removeUserById(int id) {

        userRepository.deleteById(id);
    }

    public AtomicReference<Double> getTotalAccountBalanceByUserId(int id){
        AtomicReference<Double> result= new AtomicReference<>((double) 0);
        List<BankAccount> bankAccounts = userRepository.getById(id).getAccountList();

        if(bankAccounts == null) return result;

        bankAccounts.iterator().forEachRemaining(bankAccount -> {
            result.updateAndGet(v -> new Double((double) (v + bankAccount.getBalance())));
        });

        return result;
    }

    public User findUserByEmail(String email) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            if(user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

}
