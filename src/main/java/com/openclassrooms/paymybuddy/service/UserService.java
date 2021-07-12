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

/**
 * The type User service.
 */
@Service
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    /**
     * Gets users.
     *
     * @return the users
     */
    public Iterable<User> getUsers() {
        LOGGER.info("Process to get all users");
        return userRepository.findAll();
    }

    /**
     * Save user.
     *
     * @param user the user
     * @throws UserAlreadyExistException the user already exist exception
     */
    public void saveUser(User user) throws UserAlreadyExistException {
        LOGGER.info("Processing to save a new user in database");
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User already exist in database");
        }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserRole("ROLE_USER");
            userRepository.save(user);
    }

    /**
     * Find user by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<User> findUserById(int id) {
        LOGGER.info("Processing to find a user by id");
        return userRepository.findById(id);
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    public User getUserById(int id) {

        LOGGER.info("Processing to find a user by id");
        return userRepository.getById(id);
    }

    /**
     * Remove user by id.
     *
     * @param id the id
     */
    public void removeUserById(int id) {
        LOGGER.info("Processing to remove user by id");
        userRepository.deleteById(id);
    }

    /**
     * Get total account balance by user id atomic reference.
     *
     * @param id the id
     * @return the atomic reference
     */
    public AtomicReference<Double> getTotalAccountBalanceByUserId(int id){
        AtomicReference<Double> result= new AtomicReference<>((double) 0);
        List<BankAccount> bankAccounts = userRepository.getById(id).getAccountList();

        if(bankAccounts == null) return result;

        bankAccounts.iterator().forEachRemaining(bankAccount -> {
            result.updateAndGet(v -> new Double((double) (v + bankAccount.getBalance())));
        });

        return result;
    }

    /**
     * Find user by email user.
     *
     * @param email the email
     * @return the user
     */
    public User findUserByEmail(String email) {
        LOGGER.info("Processing to find a user by email");
        List<User> users = userRepository.findAll();

        for (User user : users) {
            if(user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Update role by id.
     *
     * @param id   the id
     * @param role the role
     */
    public void updateRoleById(int id, String role) {
        userRepository.updateRole(id,role);
    }

}
